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

import com.mchange.v2.c3p0.ComboPooledDataSource;


/**
 * <tt>DataSourceFactory</tt> implementation which uses the C3P0 library.
 */
public class C3P0DataSourceFactory extends AbstractDataSourceFactory {
    @Override
    protected DataSource doCreate(DataSourceDescriptor desc) throws Exception {
        final ComboPooledDataSource ds = new ComboPooledDataSource();
        ds.setJdbcUrl(desc.getUrl());
        ds.setDriverClass(desc.getDriver());
        ds.setUser(desc.getUser());
        ds.setPassword(desc.getPassword());

        return ds;
    }
}
