/**
 * $Id$
 *
 * Gasp: Generic Application Service Platform
 * http://gasp.berlios.de
 * Copyright (c) 2005 Gasp team

 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.

 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.

 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301  USA
 */

package org.eu.gasp.core.bootstrap;


import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.List;
import java.util.Properties;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.log4j.PropertyConfigurator;
import org.eu.gasp.core.GaspException;
import org.eu.gasp.core.PluginManager;
import org.eu.gasp.core.PluginRegistry;
import org.eu.gasp.core.Version;
import org.eu.gasp.core.util.JarFileFilter;


/**
 * Class for bootstraping the platform.
 */
public final class Bootstrap {
    private static final String PLUGINS_DIR = "plugins";

    private Log log;
    private final String propertyFilePath;
    private String homeDir;
    private final String log4jFilePath;
    private boolean initDone = false;
    private List<String> pluginsToStart = new ArrayList<String>();


    public Bootstrap(final String homeDir, final String policyFilePath,
            final String log4jFilePath, final String propertyFilePath) {
        if (StringUtils.isBlank(homeDir)) {
            throw new IllegalArgumentException("homeDir");
        }
        try {
            this.homeDir = new File(homeDir).getCanonicalPath();
        } catch (Exception e) {
            throw new IllegalStateException("Unexpected exception", e);
        }

        // the home dir is declared in the System properties
        System.setProperty("gasp.home", homeDir);

        try {
            this.propertyFilePath = new File(homeDir,
                    propertyFilePath == null ? "conf/gasp.properties"
                            : propertyFilePath).getCanonicalPath();
        } catch (Exception e) {
            throw new IllegalStateException("Unexpected exception", e);
        }
        this.log4jFilePath = log4jFilePath;

        if (!StringUtils.isBlank(policyFilePath)) {
            // installing security manager
            try {
                System.setProperty("java.security.policy", new File(homeDir,
                        policyFilePath).getAbsoluteFile().toURI().toURL()
                        .toExternalForm());
                System.setSecurityManager(new SecurityManager());
            } catch (Exception e) {
                throw new IllegalStateException(
                        "Error while setting the Java security policy", e);
            }
        }

        // adding a shutdown hook to properly close the application
        Runtime.getRuntime().addShutdownHook(new ShutdownHook(this));
    }


    public Bootstrap() {
        this(System.getProperty("gasp.home", System.getProperty("user.dir")),
                null, null, null);
    }


    public void init() throws GaspException {
        configureLogging();

        loadProperties();
        log.info("Using home directory: " + homeDir);

        final PluginRegistry pluginRegistry = PluginManager.instance()
                .getPluginRegistry();

        final File pluginsDir = new File(homeDir, PLUGINS_DIR);
        if (!pluginsDir.exists() || !pluginsDir.isDirectory()) {
            throw new IllegalStateException(
                    "Plugins directory is not available: "
                            + pluginsDir.getAbsolutePath());
        }

        // installing local plugins
        final File[] plugins = pluginsDir.listFiles(new JarFileFilter());
        if (plugins != null) {
            for (int i = 0; i < plugins.length; ++i) {
                final File pluginFile = plugins[i].getAbsoluteFile();

                log.info("Installing plugin: " + pluginFile.getPath());
                try {
                    pluginRegistry.install(pluginFile.toURI().toURL());
                } catch (Exception e) {
                    throw new GaspException("Error while installing plugin: "
                            + pluginFile.getPath(), e);
                }
            }
        }
        initDone = true;
    }


    public void start() throws GaspException {
        checkInitDone();

        log.debug("Starting application");
        final Package pkg = getClass().getPackage();
        final String title = pkg.getSpecificationTitle();
        final String version = pkg.getSpecificationVersion();
        if (!StringUtils.isBlank(title) && !StringUtils.isBlank(version)) {
            // print version in the log
            log.info("Using: " + title + " " + version);
        }

        final PluginRegistry pluginRegistry = PluginManager.instance()
                .getPluginRegistry();

        for (final String pluginDesc : pluginsToStart) {
            try {
                final String pluginId = getPluginId(pluginDesc);
                final Version pluginVersion = getPluginVersion(pluginDesc);
                pluginRegistry.activate(pluginId, pluginVersion);
            } catch (Exception e) {
                throw new GaspException("Error while starting plugin: "
                        + pluginDesc, e);
            }
        }

        log.info("Platform is up and running");
    }


    public void stop() throws GaspException {
        log.debug("Stopping application");
        PluginManager.instance().shutdown();
    }


    private Version getPluginVersion(String pluginDesc) {
        if (StringUtils.isBlank(pluginDesc)) {
            throw new IllegalArgumentException("pluginDesc");
        }
        final int i = pluginDesc.indexOf('-');
        if (i == -1 || i == pluginDesc.length() - 1) {
            return null;
        }

        return new Version(pluginDesc.substring(i + 1));
    }


    private String getPluginId(String pluginDesc) {
        if (StringUtils.isBlank(pluginDesc)) {
            throw new IllegalArgumentException("pluginDesc");
        }
        final int i = pluginDesc.indexOf('-');
        if (i == -1 || i == pluginDesc.length() - 1) {
            return pluginDesc;
        }

        return pluginDesc.substring(0, i);
    }


    @SuppressWarnings("unchecked")
    private void loadProperties() {
        InputStream input = null;
        try {
            final File propFile = new File(propertyFilePath);
            if (!propFile.exists()) {
                log.debug("Properties file doesn't exist: "
                        + propFile.getPath());
                return;
            }
            input = new BufferedInputStream(new FileInputStream(propFile));
            final Properties properties = new Properties();
            properties.load(input);

            final String pluginsToStartProp = properties
                    .getProperty("gasp.plugins.start");
            if (!StringUtils.isBlank(pluginsToStartProp)) {
                pluginsToStart.addAll(Arrays.asList(pluginsToStartProp
                        .split(" ")));
            }

            for (final Enumeration<String> elements = (Enumeration<String>) properties
                    .propertyNames(); elements.hasMoreElements();) {
                final String key = elements.nextElement();
                System.setProperty(key, properties.getProperty(key));
            }

            log.info("Properties loaded from file: " + propFile.getPath());
        } catch (Exception e) {
            log.warn("Error while loading properties", e);
        } finally {
            if (input != null) {
                try {
                    input.close();
                } catch (Exception ignore) {
                }
            }
        }

    }


    private void checkInitDone() throws GaspException {
        if (initDone) {
            return;
        }
        throw new GaspException(
                "Application hasn't been initialized (call the init() method)");
    }


    private void configureLogging() throws GaspException {
        // locating log4j properties
        final URL log4jUrl;
        if (StringUtils.isBlank(log4jFilePath)) {
            log4jUrl = getClass().getResource("/log4j.properties");
            if (log4jUrl == null) {
                // this is not supposed to happen
                throw new GaspException(
                        "Unable to find default log4j properties");
            }
        } else {
            final File log4jFile = new File(log4jFilePath);
            if (!log4jFile.exists() || !log4jFile.isFile()
                    || !log4jFile.canRead()) {
                throw new GaspException("Log4j file is not readable: "
                        + log4jFile.getAbsolutePath());
            }
            try {
                log4jUrl = log4jFile.toURI().toURL();
            } catch (Exception e) {
                throw new GaspException("Error while locating log4j file", e);
            }
        }

        final Properties props = new Properties();
        InputStream input = null;
        try {
            // loading log4j properties
            input = new BufferedInputStream(log4jUrl.openStream());
            props.load(input);
        } catch (Exception e) {
            if (input != null) {
                try {
                    input.close();
                } catch (Exception ignore) {
                }
            }
            throw new GaspException(
                    "Unable to load log4j properties from resource: "
                            + log4jUrl.toExternalForm(), e);
        }

        // under some OS, the logs are located in ${homeDir}/logs directory:
        // let's try to create it (we may not have sufficient permissions)
        final File logsDir = new File(homeDir, "logs");
        if (!logsDir.exists()) {
            logsDir.mkdirs();
        }

        // we can now initialize the logging system
        log = LogFactory.getLog(getClass());

        // configuring log4j properties
        PropertyConfigurator.configure(props);
        log.debug("Logging system initialized");
    }


    private class ShutdownHook extends Thread {
        private final Bootstrap bootstrap;


        public ShutdownHook(final Bootstrap bootstrap) {
            super("Gasp bootstrap shutdown thread");
            this.bootstrap = bootstrap;
        }


        public void run() {
            try {
                bootstrap.stop();
            } catch (Exception e) {
                if (log != null) {
                    log.fatal("Error while stopping", e);
                }
                System.exit(1);
            }
        }
    }
}
