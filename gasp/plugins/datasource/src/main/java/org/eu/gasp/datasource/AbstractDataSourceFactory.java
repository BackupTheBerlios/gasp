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


/**
 * Abstract implementation of <tt>DataSourceFactory</tt>.
 */
public abstract class AbstractDataSourceFactory implements DataSourceFactory {
    public final DataSource create(DataSourceDescriptor desc) {
        try {
            return doCreate(desc);
        } catch (Exception e) {
            throw new IllegalStateException("Unable to create DataSource: "
                    + desc, e);
        }
    }


    /**
     * Creates a new <tt>DataSource</tt>. This method is called from
     * <tt>create(DataSourceDescriptor desc)</tt>.
     * 
     * @param desc informations for creating the <tt>DataSource</tt>
     * @throws Exception error while creating <tt>DataSource</tt>
     * @return a newly created <tt>DataSource</tt>
     */
    protected abstract DataSource doCreate(DataSourceDescriptor desc)
            throws Exception;
}
