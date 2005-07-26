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

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.eu.gasp.core.GaspException;
import org.eu.gasp.core.Plugin;
import org.eu.gasp.core.PluginDescriptor;
import org.eu.gasp.core.PluginRegistry;
import org.eu.gasp.core.Version;


public abstract class AbstractPluginRegistry implements PluginRegistry {
    protected final Log log = LogFactory.getLog(getClass());


    public final void activate(String id, Version version) throws GaspException {
        if (StringUtils.isBlank(id)) {
            throw new IllegalArgumentException("id");
        }

        try {
            doActivate(id, version);
        } catch (Exception e) {
            throw new GaspException("Unable to activate plugin: " + id, e);
        }
    }


    public final void deactivate(String id, Version version)
            throws GaspException {
        if (StringUtils.isBlank(id)) {
            throw new IllegalArgumentException("id");
        }

        try {
            doDeactivate(id, version);
        } catch (Exception e) {
            throw new GaspException("Unable to deactivate plugin: " + id, e);
        }
    }


    public final void install(URL url) throws GaspException {
        if (url == null) {
            throw new NullPointerException("url");
        }

        try {
            doInstall(url);
        } catch (Exception e) {
            throw new GaspException("Unable to install plugin: "
                    + url.toExternalForm(), e);
        }
    }


    public final PluginDescriptor getPluginDescriptor(Plugin plugin,
            Version version) throws GaspException {
        if (plugin == null) {
            throw new NullPointerException("plugin");
        }

        try {
            return doGetPluginDescriptor(plugin, version);
        } catch (Exception e) {
            throw new GaspException(
                    "Unable to get plugin descriptor for plugin: " + plugin, e);
        }
    }


    public void shutdown() {
        try {
            doShutdown();
        } catch (Exception e) {
            log.error("Error during plugin registry shutdown", e);
        }
    }


    protected abstract void doShutdown() throws Exception;


    protected abstract void doActivate(String id, Version version)
            throws Exception;


    protected abstract void doDeactivate(String id, Version version)
            throws Exception;


    protected abstract void doInstall(URL url) throws Exception;


    protected abstract PluginDescriptor doGetPluginDescriptor(Plugin plugin,
            Version version) throws Exception;
}
