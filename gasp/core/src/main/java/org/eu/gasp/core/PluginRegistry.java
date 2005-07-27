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

package org.eu.gasp.core;


import java.net.URL;


/**
 * Plugin registry. This class handles the lifecycle of plugins: installing,
 * starting and stopping.
 */
public interface PluginRegistry {
    /**
     * Installs a plugin specified by an <tt>URL</tt>. Implementations must
     * implement at least the "file" protocol. Other protocols may be provided
     * (such as "http" or "ftp").
     * 
     * @param url address of a plugin to install
     * @throws GaspException error while installing
     */
    void install(URL url) throws GaspException;


    /**
     * Activates a plugin. If no version is specified (<tt>null</tt>), the
     * most recent plugin is activated.
     * 
     * @param id plugin id
     * @param version plugin version
     * @throws GaspException no plugin found, or error on activation
     */
    void activate(String id, Version version) throws GaspException;


    /**
     * Deactivates a plugin. If no version is specified (<tt>null</tt>), the
     * most recent plugin is deactivated.
     * 
     * @param id plugin id
     * @param version plugin version
     * @throws GaspException no plugin found, or error on deactivation
     */
    void deactivate(String id, Version version) throws GaspException;


    /**
     * Returns the plugin descriptor of the specified plugin. If no version is
     * specified, it returns the plugin descriptor of the most recent plugin.
     * 
     * @param plugin concerned plugin
     * @param version plugin version
     * @return the plugin descriptor
     * @throws GaspException no plugin found
     */
    PluginDescriptor getPluginDescriptor(Plugin plugin, Version version)
            throws GaspException;


    /**
     * Stops the plugin registry. All activated plugins are deactivated.
     */
    void shutdown();
}
