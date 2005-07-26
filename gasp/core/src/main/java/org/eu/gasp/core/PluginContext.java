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
 * Context of a plugin. Provides an access to the <tt>ServiceRegistry</tt> to
 * allow a plugin to register and unregister services. A plugin may also
 * retrieve informations about itself with the <tt>PluginDescriptor</tt>.
 */
public interface PluginContext {
    /**
     * Returns the <tt>ServiceRegistry</tt> for the plugin.
     * 
     * @return <tt>ServiceRegistry</tt> for the plugin
     */
    ServiceRegistry getServiceRegistry();


    /**
     * Returns the <tt>PluginDescriptor</tt> for the plugin.
     * 
     * @return <tt>PluginDescriptor</tt> for the plugin.
     */
    PluginDescriptor getPluginDescriptor();
}
