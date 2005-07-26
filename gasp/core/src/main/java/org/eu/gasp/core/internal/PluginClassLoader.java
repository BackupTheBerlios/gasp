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


import java.net.URL;
import java.net.URLClassLoader;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.eu.gasp.core.PluginDependency;
import org.eu.gasp.core.PluginDescriptor;


public class PluginClassLoader extends URLClassLoader {
    private final Log log = LogFactory.getLog(getClass());
    private final DefaultPluginRegistry pluginRegistry;
    private final PluginDescriptor pluginDescriptor;


    public PluginClassLoader(final DefaultPluginRegistry pluginRegistry,
            PluginDescriptor pluginDescriptor, URL jarFile, ClassLoader parent) {
        super(new URL[] { jarFile }, parent);
        this.pluginRegistry = pluginRegistry;
        this.pluginDescriptor = pluginDescriptor;
    }


    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        try {
            final Class clazz = super.findClass(name);
            if (log.isDebugEnabled()) {
                log.debug("Class '" + name + "' loaded from super.findClass()");
            }
            return clazz;
        } catch (ClassNotFoundException ignore) {
        }

        for (final PluginDependency pluginDependency : pluginDescriptor
                .getPluginDependencies()) {
            final ClassLoader classLoader = pluginRegistry.getClassLoader(
                    pluginDependency.getPluginId(), pluginDependency
                            .getVersion());
            try {
                final Class clazz = classLoader.loadClass(name);
                if (log.isDebugEnabled()) {
                    log.debug("Class '" + name
                            + "' loaded from plugin class loader: "
                            + pluginDependency.getPluginId());
                }
                return clazz;
            } catch (ClassNotFoundException ignore) {
            }
        }

        try {
            final Class clazz = getParent().loadClass(name);
            if (log.isDebugEnabled()) {
                log.debug("Class '" + name
                        + "' loaded from parent class loader");
            }
            return clazz;
        } catch (ClassNotFoundException ignore) {
        }

        if (log.isDebugEnabled()) {
            log.debug("Unable to load class '" + name
                    + "' from any class loaders");
        }

        throw new ClassNotFoundException(name);
    }


    @Override
    public URL findResource(String name) {
        URL url = super.findResource(name);
        if (url != null) {
            if (log.isDebugEnabled()) {
                log.debug("Resource '" + name
                        + "' loaded from super.findResource()");
            }
            return url;
        }

        for (final PluginDependency pluginDependency : pluginDescriptor
                .getPluginDependencies()) {
            final ClassLoader classLoader = pluginRegistry.getClassLoader(
                    pluginDependency.getPluginId(), pluginDependency
                            .getVersion());
            url = classLoader.getResource(name);
            if (url != null) {
                if (log.isDebugEnabled()) {
                    log.debug("Resource '" + name
                            + "' loaded from plugin class loader: "
                            + pluginDependency.getPluginId());
                }
                return url;
            }
        }

        url = getParent().getResource(name);
        if (url != null) {
            if (log.isDebugEnabled()) {
                log.debug("Resource '" + name
                        + "' loaded from parent class loader");
            }
            return url;
        }

        if (log.isDebugEnabled()) {
            log.debug("Unable to load resource '" + name
                    + "' from any class loaders");
        }

        return null;
    }
}
