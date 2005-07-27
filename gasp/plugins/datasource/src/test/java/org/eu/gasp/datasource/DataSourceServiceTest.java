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


import javax.sql.DataSource;

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


    public void testLookupNullId() {
        final DataSourceService dss = new DataSourceService();
        try {
            dss.lookup(null);
            fail("Expected exception was not thrown");
        } catch (Exception e) {
            // expected
        }
    }


    public void testDefaultLifecycle() {
        final DataSourceService dss = new DataSourceService();
        dss.setDataSourceFactory(new MockDataSourceFactory());

        final String id = "test";
        dss.register(new DataSourceDescriptor(id, "jdbc:test://test",
                "test.Test", "testuser", "testpasswd"));
        final DataSource ds = dss.lookup(id);
        assertNotNull(ds);

        dss.unregister(id);
        try {
            dss.lookup(id);
            fail("Expected exception was not thrown");
        } catch (Exception e) {
            // expected
        }
    }


    public void testLookupWithNullDataSourceFactory() {
        final DataSourceService dss = new DataSourceService();
        dss.setDataSourceFactory(new NullDataSourceFactory());

        final String id = "test";
        dss.register(new DataSourceDescriptor(id, "jdbc:test://test",
                "test.Test", "testuser", "testpasswd"));

        try {
            dss.lookup(id);
            fail("Expected exception was not thrown");
        } catch (Exception e) {
            // expected
        }
    }


    public void testRegisterNullDataSourceDescriptor() {
        final DataSourceService dss = new DataSourceService();
        try {
            dss.register(null);
            fail("Expected exception was not thrown");
        } catch (Exception e) {
            // expected
        }
    }


    public void testSetNullDataSourceFactory() {
        final DataSourceService dss = new DataSourceService();
        try {
            dss.setDataSourceFactory(null);
            fail("Expected exception was not thrown");
        } catch (Exception e) {
            // expected
        }
    }


    public void testLookupWithExceptionDataSourceFactory() {
        final DataSourceService dss = new DataSourceService();
        dss.setDataSourceFactory(new ExceptionDataSourceFactory());

        final String id = "test";
        dss.register(new DataSourceDescriptor(id, "jdbc:test://test",
                "test.Test", "testuser", "testpasswd"));

        try {
            dss.lookup(id);
            fail("Expected exception was not thrown");
        } catch (Exception e) {
            // expected
        }
    }

}
