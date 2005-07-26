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


import java.util.List;


/**
 * Plugin descriptor.
 */
public interface PluginDescriptor {
    /**
     * Returns the plugin id.
     * 
     * @return plugin id
     */
    String getId();


    /**
     * Returns the plugin version.
     * 
     * @return plugin version
     */
    Version getVersion();


    /**
     * Returns the class name which implements the interface <tt>Plugin</tt>
     * for the current plugin. May be <tt>null</tt> if no class is provided.
     * In such a case, the plugin is said to be a "plugin library": it doesn't
     * provide any services.
     * 
     * @return the class name which implements <tt>Plugin</tt>, or
     *         <tt>null</tt>
     */
    String getPluginClassName();


    /**
     * Returns the list of provided service names by the current plugin. The
     * list may be empty but cannot be <tt>null</tt>.
     * 
     * @return list of provided service names
     */
    List<String> getServices();


    /**
     * Returns the list of plugin dependencies. The list may be empty but cannot
     * be <tt>null</tt>.
     * 
     * @return list of plugin dependencies
     */
    List<? extends PluginDependency> getPluginDependencies();
}
