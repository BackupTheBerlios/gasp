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


import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.eu.gasp.core.GaspException;
import org.eu.gasp.core.ServiceRegistry;


public abstract class AbstractServiceRegistry implements ServiceRegistry {
    protected final Log log = LogFactory.getLog(getClass());
    private boolean stopped = false;


    public final Object lookup(Class clazz) throws GaspException {
        return lookup(clazz.getName());
    }


    public final void register(Class clazz, Object service)
            throws GaspException {
        checkStopped();
        if (clazz == null) {
            throw new NullPointerException("clazz");
        }
        register(clazz.getName(), service);
    }


    public final void unregister(Class clazz) {
        checkStopped();
        if (clazz == null) {
            throw new NullPointerException("clazz");
        }
        unregister(clazz.getName());
    }


    public final Object lookup(String id) throws GaspException {
        checkStopped();
        if (StringUtils.isBlank(id)) {
            throw new IllegalArgumentException("id");
        }

        try {
            return doLookup(id);
        } catch (Exception e) {
            throw new GaspException("Unable to lookup service: " + id, e);
        }
    }


    public final void register(String id, Object service) throws GaspException {
        checkStopped();
        if (StringUtils.isBlank(id)) {
            throw new IllegalArgumentException("id");
        }
        if (service == null) {
            throw new NullPointerException("service");
        }

        try {
            doRegister(id, service);
        } catch (Exception e) {
            throw new GaspException("Unable to register service: " + id, e);
        }
    }


    public final void unregister(String id) {
        checkStopped();
        if (StringUtils.isBlank(id)) {
            throw new IllegalArgumentException("id");
        }

        try {
            doUnregister(id);
        } catch (Exception e) {
            log.error("Error while unregistering service: " + id, e);
        }
    }


    public void shutdown() {
        checkStopped();
        try {
            doShutdown();
        } catch (Exception e) {
            log.error("Error during service registry shutdown", e);
        }
    }


    private void checkStopped() {
        if (stopped) {
            throw new IllegalStateException(
                    "Service registry has been stopped: "
                            + "no more operations are available.");
        }
    }


    protected abstract void doShutdown() throws Exception;


    protected abstract Object doLookup(String id) throws Exception;


    protected abstract void doRegister(String id, Object service)
            throws Exception;


    protected abstract void doUnregister(String id) throws Exception;
}
