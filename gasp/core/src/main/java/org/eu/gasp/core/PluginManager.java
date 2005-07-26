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


import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.eu.gasp.core.internal.DefaultPluginManager;


/**
 * Main class for managing plugins. This class is designed to provide several
 * implementations of <tt>PluginRegistry</tt> and <tt>ServiceRegistry</tt>.
 * See the <tt>instance()</tt> method for more informations.
 */
public abstract class PluginManager {
    public static final String PLUGIN_MANAGER_PROPERTY = "org.eu.gasp.core.PluginManager";
    private static final PluginManager INSTANCE = newInstance();
    protected final Log log = LogFactory.getLog(getClass());
    protected PluginRegistry pluginRegistry;
    protected ServiceRegistry serviceRegistry;


    /**
     * Concrete implementations of <tt>PluginManager</tt> must set a value to
     * <tt>pluginRegistry</tt> and <tt>serviceRegistry</tt>.
     */
    protected PluginManager() {
    }


    /**
     * Returns the <tt>ServiceRegistry</tt>.
     * 
     * @return <tt>ServiceRegistry</tt> attached to this
     *         <tt>PluginManager</tt>
     */
    public final ServiceRegistry getServiceRegistry() {
        return serviceRegistry;
    }


    /**
     * Returns the <tt>PluginRegistry</tt>.
     * 
     * @return <tt>PluginRegistry</tt> attached to this <tt>PluginManager</tt>
     */
    public final PluginRegistry getPluginRegistry() {
        return pluginRegistry;
    }


    /**
     * Shutdowns the <tt>PluginManager</tt>. Calls the <tt>shutdown()</tt>
     * method of <tt>PluginRegistry</tt> and <tt>ServiceRegistry</tt>.
     */
    public final void shutdown() {
        if (pluginRegistry != null) {
            pluginRegistry.shutdown();
        }
        if (serviceRegistry != null) {
            serviceRegistry.shutdown();
        }
    }


    /**
     * Returns an implementation of <tt>PluginManager</tt>. If the system
     * property <tt>org.eu.gasp.core.PluginManager</tt> contains a class, this
     * method creates and returns an instance of this class. Otherwise, a
     * default implementation is returned.
     * 
     * @return the static <tt>PluginManager</tt> instance
     */
    public static final PluginManager instance() {
        return INSTANCE;
    }


    private static PluginManager newInstance() {
        String stubClass = System.getProperty(PLUGIN_MANAGER_PROPERTY);
        if (StringUtils.isBlank(stubClass)) {
            // using default implementation
            stubClass = DefaultPluginManager.class.getName();
        }

        try {
            return (PluginManager) Class.forName(stubClass).newInstance();
        } catch (Exception e) {
            throw new IllegalStateException(
                    "Unable to instanciate plugin manager: " + stubClass, e);
        }
    }
}
