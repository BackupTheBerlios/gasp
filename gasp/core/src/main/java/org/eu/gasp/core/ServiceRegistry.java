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
 * Service registry. This class is responsible for looking up, registering and
 * unregistering services provided by plugins. If several plugins register the
 * same service, only the last one would be effective.
 */
public interface ServiceRegistry {
    /**
     * Looks up a registered service against its id.
     * 
     * @param id service id
     * @return a registered service
     * @throws GaspException service not found
     */
    Object lookup(String id) throws GaspException;


    /**
     * Looks up a registered service against its class. This method delegates to
     * <tt>lookup(String)</tt> by using <tt>clazz.getName()</tt> as the
     * service id.
     * 
     * @param clazz service class
     * @return a registered service
     * @throws GaspException service not found
     */
    Object lookup(Class clazz) throws GaspException;


    /**
     * Registers a new service. If the same service was earlier registered, this
     * one replaces it.
     * 
     * @param id service id
     * @param service service implementation
     * @throws GaspException error while registering the service
     */
    void register(String id, Object service) throws GaspException;


    /**
     * Registers a new service. If the same service was earlier registered, this
     * one replaces it. This method delegates to <tt>register(String)</tt> by
     * using <tt>clazz.getName()</tt> as the service id.
     * 
     * @param clazz service class
     * @param service service implementation
     * @throws GaspException error while registering the service
     */
    void register(Class clazz, Object service) throws GaspException;


    /**
     * Unregisters a service. After this call, the service will not be available
     * through <tt>lookup</tt> methods.
     * 
     * @param id service id
     */
    void unregister(String id);


    /**
     * Unregisters a service. After this call, the service will not be available
     * through <tt>lookup</tt> methods. This method delegates to
     * <tt>unregister(String)</tt> by using <tt>clazz.getName()</tt> as the
     * service id.
     * 
     * @param clazz service class
     */
    void unregister(Class clazz);


    /**
     * Stops the service registry. All methods are made inactive.
     */
    void shutdown();
}
