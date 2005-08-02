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
import org.easymock.MockControl;

import junit.framework.TestCase;


public class PluginManagerTest extends TestCase {
    private MockPluginManager createPluginManager() {
        return new MockPluginManager();
    }


    public void testShutdown() {
        final MockPluginManager pm = createPluginManager();
        final MockControl control = pm.getControl();
        pm.getPluginRegistry().shutdown();
        control.replay();
        pm.shutdown();
        control.verify();
    }


    public void testInstanceWithMock() {
        System.setProperty(PluginManager.PLUGIN_MANAGER_PROPERTY,
                MockPluginManager.class.getName());
        assertFalse(StringUtils.isBlank(System
                .getProperty(PluginManager.PLUGIN_MANAGER_PROPERTY)));

        final PluginManager pm = PluginManager.instance();
        assertNotNull(pm);
        assertTrue("PluginManager should have been "
                + "an instance of MockPluginManager: "
                + pm.getClass().getName(), pm instanceof MockPluginManager);

        assertSame("The same PluginManager should have been returned", pm,
                PluginManager.instance());
    }
}
