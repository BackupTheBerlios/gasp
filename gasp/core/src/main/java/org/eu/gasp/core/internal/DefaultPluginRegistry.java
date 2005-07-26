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

package org.eu.gasp.core.internal;


import java.io.File;
import java.net.URL;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.jar.Attributes;
import java.util.jar.JarFile;
import java.util.jar.Manifest;

import org.apache.commons.lang.StringUtils;
import org.eu.gasp.core.Plugin;
import org.eu.gasp.core.PluginContext;
import org.eu.gasp.core.PluginDescriptor;
import org.eu.gasp.core.Version;
import org.eu.gasp.core.util.ReversedIterator;


public class DefaultPluginRegistry extends AbstractPluginRegistry {
    private static final String JAR_PLUGIN_CLASS = "Plugin-Class";
    private static final String JAR_PLUGIN_VERSION = "Plugin-Version";
    private static final String JAR_PLUGIN = "Plugin";
    private static final String JAR_PLUGIN_REQUIRES = "Plugin-Requires";
    private static final String JAR_PLUGIN_PROVIDES = "Plugin-Provides";

    private final Map<PluginDescriptor, PluginData> plugins = new LinkedHashMap<PluginDescriptor, PluginData>();


    public ClassLoader getClassLoader(String pluginId, Version version) {
        if (StringUtils.isBlank(pluginId)) {
            throw new IllegalArgumentException("pluginId");
        }

        final PluginDescriptor pluginDescriptor = getPluginDescriptor(pluginId,
                version);

        return plugins.get(pluginDescriptor).classLoader;
    }


    @Override
    protected void doActivate(String id, Version version) throws Exception {
        final PluginDescriptor pd = getPluginDescriptor(id, version);

        log.info("Activating plugin: " + pd);

        // TODO handle dependencies
        getPlugin(pd).start(plugins.get(pd).pluginContext);
    }


    @Override
    protected void doDeactivate(String id, Version version) throws Exception {
        final PluginDescriptor pd = getPluginDescriptor(id, version);

        // TODO handle dependencies
        final PluginData pluginData = plugins.get(pd);
        if (pluginData.plugin == null) {
            return;
        }

        log.info("Deactivating plugin: " + pd);

        pluginData.plugin.stop(pluginData.pluginContext);
        pluginData.clear();
    }


    @Override
    protected void doInstall(URL url) throws Exception {
        // right now, we only handle the "file" protocol:
        // we may support more protocols in the future
        // (like remote plugin locations with automatic download)
        final String protocol = url.getProtocol();
        if ("file".equals(protocol)) {
            // we have to transform the URL encoded file:
            // %20 = whitespace... and so on
            final File file = new File(URLDecoder
                    .decode(url.getFile(), "UTF-8"));
            registerPlugin(file);
        } else {
            throw new IllegalStateException("Unhandled protocol: " + protocol);
        }
    }


    @Override
    protected void doShutdown() throws Exception {
        for (final Iterator<PluginDescriptor> i = new ReversedIterator<PluginDescriptor>(
                plugins.keySet().iterator()); i.hasNext();) {
            final PluginDescriptor pluginDescriptor = i.next();
            try {
                deactivate(pluginDescriptor.getId(), pluginDescriptor
                        .getVersion());
            } catch (Exception e) {
                log.error("Error while stopping plugin: " + pluginDescriptor);
            }
        }
    }


    private PluginDescriptor getPluginDescriptor(String pluginId,
            Version version) {
        if (StringUtils.isBlank(pluginId)) {
            throw new IllegalArgumentException("pluginId");
        }

        PluginDescriptor mostUpToDatePluginDescriptor = null;

        for (final Map.Entry<PluginDescriptor, PluginData> entry : plugins
                .entrySet()) {
            if (!pluginId.equals(entry.getKey().getId())) {
                continue;
            }
            if (version != null && entry.getKey().getVersion().equals(version)) {
                return entry.getKey();
            } else if (mostUpToDatePluginDescriptor == null
                    || entry.getKey().getVersion().compareTo(
                            mostUpToDatePluginDescriptor.getVersion()) > -1) {
                mostUpToDatePluginDescriptor = entry.getKey();
            }
        }

        if (version == null && mostUpToDatePluginDescriptor != null) {
            return mostUpToDatePluginDescriptor;
        }

        throw new IllegalStateException("Plugin not found: " + pluginId
                + (version != null ? "-" + version : ""));
    }


    private void registerPlugin(File file) throws Exception {
        if (isFileRegistered(file)) {
            return;
        }

        final JarFile jarFile = new JarFile(file);
        final Manifest manifest = jarFile.getManifest();

        String pluginId = null;
        String pluginClassName = null;
        Version version = null;
        final List<DefaultPluginDependency> pluginDependencies = new ArrayList<DefaultPluginDependency>();
        final List<String> services = new ArrayList<String>();

        final Attributes attrs = manifest.getMainAttributes();
        for (final Map.Entry entry : attrs.entrySet()) {
            final String key = entry.getKey().toString();
            final String value = StringUtils.trimToNull(entry.getValue()
                    .toString());

            if (value == null) {
                continue;
            }

            if (JAR_PLUGIN.equals(key)) {
                pluginId = value;
            } else if (JAR_PLUGIN_CLASS.equals(key)) {
                pluginClassName = value;
            } else if (JAR_PLUGIN_VERSION.equals(key)) {
                version = new Version(value);
            } else if (JAR_PLUGIN_PROVIDES.equals(key)) {
                for (final String service : value.split(",")) {
                    services.add(service);
                }
            } else if (JAR_PLUGIN_REQUIRES.equals(key)) {
                for (final String versionnedRequiredPluginId : value.split(",")) {
                    pluginDependencies.add(new DefaultPluginDependency(
                            versionnedRequiredPluginId, null));
                }
            }
        }

        if (StringUtils.isBlank(pluginId)) {
            throw new IllegalStateException("Missing " + JAR_PLUGIN
                    + " attribute in manifest");
        }
        if (version == null) {
            throw new IllegalStateException("Missing " + JAR_PLUGIN_VERSION
                    + " attribute in manifest");
        }

        final PluginDescriptor pluginDescriptor = new DefaultPluginDescriptor(
                pluginId, version, pluginClassName, pluginDependencies,
                services);
        if (!plugins.containsKey(pluginDescriptor)) {
            log.info("Registering plugin: " + pluginDescriptor);
            plugins.put(pluginDescriptor, new PluginData(file));
        }
    }


    @Override
    protected PluginDescriptor doGetPluginDescriptor(Plugin plugin,
            Version version) throws Exception {
        if (plugin == null) {
            throw new NullPointerException("plugin");
        }
        PluginDescriptor mostUpToDatePluginDescriptor = null;

        for (final Map.Entry<PluginDescriptor, PluginData> entry : plugins
                .entrySet()) {
            if (!plugin.equals(entry.getValue().plugin)) {
                continue;
            }
            if (version != null && entry.getKey().getVersion().equals(version)) {
                return entry.getKey();
            } else if (mostUpToDatePluginDescriptor == null
                    || entry.getKey().getVersion().compareTo(
                            mostUpToDatePluginDescriptor.getVersion()) > -1) {
                mostUpToDatePluginDescriptor = entry.getKey();
            }
        }

        if (version == null && mostUpToDatePluginDescriptor != null) {
            return mostUpToDatePluginDescriptor;
        }

        throw new IllegalStateException("Plugin not found: " + plugin
                + (version != null ? "-" + version : ""));
    }


    private boolean isFileRegistered(File file) {
        if (file == null) {
            throw new NullPointerException("file");
        }

        for (final PluginData pluginData : plugins.values()) {
            if (file.equals(pluginData.file)) {
                return true;
            }
        }

        return false;
    }


    private Plugin getPlugin(PluginDescriptor pd) {
        final PluginData pluginData = plugins.get(pd);
        Plugin plugin = pluginData.plugin;
        if (plugin == null) {
            try {
                final PluginClassLoader classLoader = new PluginClassLoader(
                        this, pd, pluginData.file.toURI().toURL(), getClass()
                                .getClassLoader());
                plugin = (Plugin) classLoader
                        .loadClass(pd.getPluginClassName()).newInstance();
                pluginData.plugin = plugin;
                pluginData.classLoader = classLoader;
                pluginData.pluginContext = new DefaultPluginContext(pd);
            } catch (Exception e) {
                throw new IllegalStateException(
                        "Unable to instanciate plugin: "
                                + pd.getPluginClassName(), e);
            }
        }

        return plugin;
    }


    private class PluginData {
        final File file;
        Plugin plugin;
        ClassLoader classLoader;
        PluginContext pluginContext;


        public PluginData(final File file) {
            this.file = file;
        }


        public void clear() {
            plugin = null;
            classLoader = null;
            pluginContext = null;
        }
    }
}
