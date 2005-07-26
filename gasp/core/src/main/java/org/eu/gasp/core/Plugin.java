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


/**
 * Interface defining a plugin.
 */
public interface Plugin {
    /**
     * Returns if the plugin has been started.
     * 
     * @return <tt>true</tt> if the plugin is started
     */
    boolean isStarted();


    /**
     * Method called when the plugin is started. The plugin may register here
     * several services by using the <tt>PluginContext</tt>.
     * 
     * @param pluginContext context of the plugin
     * @throws GaspException error on start
     */
    void start(PluginContext pluginContext) throws GaspException;


    /**
     * Method called when the plugin is stopped. The plugin must unregister the
     * services registered on start.
     * 
     * @param pluginContext context of the plugin
     * @throws GaspException error on stop
     */
    void stop(PluginContext pluginContext) throws GaspException;
}
