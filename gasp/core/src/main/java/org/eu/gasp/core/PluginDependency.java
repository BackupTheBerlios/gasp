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
 * Plugin dependency, against an other plugin.
 */
public interface PluginDependency {
    /**
     * Returns the version of the plugin we depend on. May be <tt>null</tt> if
     * the version is not crucial. In order to satisfy the dependency, the
     * version of other plugins must be compatible with this version.
     * 
     * @return plugin version of the dependency, or <tt>null</tt>
     */
    Version getVersion();


    /**
     * Returns the plugin id we depend on.
     * 
     * @return plugin id of the dependency
     */
    String getPluginId();
}
