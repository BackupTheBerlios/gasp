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


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


/**
 * Abstract implementation for plugin. Plugins should extend this class.
 */
public abstract class AbstractPlugin implements Plugin {
    protected final Log log = LogFactory.getLog(getClass());
    private boolean started = false;


    public final void start(PluginContext pluginContext) throws GaspException {
        if (started) {
            throw new GaspException("Plugin already started: "
                    + getClass().getName());
        }

        try {
            onStart(pluginContext);
        } catch (Exception e) {
            throw new GaspException("Unable to start plugin: "
                    + getClass().getName(), e);
        }
        started = true;
    }


    public final void stop(PluginContext pluginContext) throws GaspException {
        if (!started) {
            throw new GaspException("Plugin not started: "
                    + getClass().getName());
        }

        try {
            onStop(pluginContext);
        } catch (Exception e) {
            throw new GaspException("Unable to stop plugin: "
                    + getClass().getName(), e);
        }
        started = false;
    }


    public final boolean isStarted() {
        return started;
    }


    /**
     * This method is supposed to be overloaded.
     * 
     * @param pluginContext context of the plugin
     * @throws Exception error on start
     */
    protected void onStart(PluginContext pluginContext) throws Exception {
    }


    /**
     * This method is supposed to be overloaded.
     * 
     * @param pluginContext context of the plugin
     * @throws Exception error on stop
     */
    protected void onStop(PluginContext pluginContext) throws Exception {
    }
}
