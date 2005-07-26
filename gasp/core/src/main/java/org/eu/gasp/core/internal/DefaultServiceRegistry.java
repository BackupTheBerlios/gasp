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


import java.util.HashMap;
import java.util.Map;


public class DefaultServiceRegistry extends AbstractServiceRegistry {
    private final Map<String, Object> services = new HashMap<String, Object>();


    @Override
    protected Object doLookup(String id) throws Exception {
        final Object service = services.get(id);
        if (service == null) {
            throw new IllegalStateException("Service is not registered: " + id);
        }

        return service;
    }


    @Override
    protected void doRegister(String id, Object service) throws Exception {
        services.put(id, service);
    }


    @Override
    protected void doUnregister(String id) throws Exception {
        services.remove(id);
    }


    @Override
    protected void doShutdown() throws Exception {
        services.clear();
    }
}
