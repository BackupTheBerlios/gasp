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

package org.eu.gasp.datasource;


import junit.framework.TestCase;


public class DataSourceServiceTest extends TestCase {
    public void testDataSourceFactory() {
        final DataSourceService dss = new DataSourceService();
        assertNotNull(dss.getDataSourceFactory());

        final DataSourceFactory dsf = new MockDataSourceFactory();
        dss.setDataSourceFactory(dsf);
        assertEquals(dsf, dss.getDataSourceFactory());
    }


    public void testLookupUnregisteredDataSource() {
        final DataSourceService dss = new DataSourceService();
        try {
            dss.lookup("test");
            fail("Expected exception was not thrown");
        } catch (Exception e) {
            // expected
        }
    }
}
