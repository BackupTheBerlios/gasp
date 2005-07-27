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


import java.util.HashMap;
import java.util.Map;
import java.util.WeakHashMap;

import javax.sql.DataSource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


/**
 * Service for getting <tt>DataSource</tt> objects.
 */
public class DataSourceService {
    private final Log log = LogFactory.getLog(getClass());
    private final Map<String, DataSource> dataSourceCache = new WeakHashMap<String, DataSource>(
            1);
    private final Map<String, DataSourceDescriptor> dataSourceDescriptorCache = new HashMap<String, DataSourceDescriptor>(
            1);
    private DataSourceFactory dataSourceFactory;


    public DataSourceService() {
        setDataSourceFactory(new C3P0DataSourceFactory());
    }


    /**
     * Returns a registered <tt>DataSource</tt>. The <tt>DataSource</tt> is
     * created the first time it is accessed. A <tt>RuntimeException</tt> may
     * be thrown if the <tt>DataSource</tt> couldn't be created.
     * 
     * @param id <tt>DataSource</tt> id to lookup
     * @return a <tt>DataSource</tt> registered under the key <tt>id</tt>
     */
    public DataSource lookup(String id) {
        if (id == null) {
            throw new NullPointerException("id");
        }

        // getting a DataSource form the cache
        DataSource ds = dataSourceCache.get(id);
        if (ds == null) {
            // no DataSource available: creating new one
            final DataSourceDescriptor desc = dataSourceDescriptorCache.get(id);
            if (desc == null) {
                // something is wrong
                throw new IllegalArgumentException(
                        "DataSource id not registered: " + id);
            }
            ds = createDataSource(desc);
            assert ds != null : "DataSource is null";
            // putting the newly DataSource in the cache for future use
            dataSourceCache.put(id, ds);
        }

        return ds;
    }


    /**
     * Registers a <tt>DataSource</tt>. The <tt>DataSource</tt> will then
     * be available through the <tt>lookup(String id)</tt> method.
     * 
     * @param desc information for creating the <tt>DataSource</tt>
     */
    public void register(DataSourceDescriptor desc) {
        if (desc == null) {
            throw new NullPointerException("desc");
        }
        dataSourceDescriptorCache.put(desc.getId(), desc);

        if (log.isInfoEnabled()) {
            log.info("DataSource registered: " + desc.getId());
        }
    }


    /**
     * Unregisters the <tt>DataSource</tt> specified by <tt>id</tt>. Then,
     * the <tt>DataSource</tt> will not be available through the
     * <tt>lookup(String id)</tt> method.
     * 
     * @param id <tt>DataSource</tt> id
     */
    public void unregister(String id) {
        dataSourceCache.remove(id);
        dataSourceDescriptorCache.remove(id);

        if (log.isInfoEnabled()) {
            log.info("DataSource unregistered: " + id);
        }
    }


    private DataSource createDataSource(DataSourceDescriptor desc) {
        if (log.isInfoEnabled()) {
            log.info("Creating new DataSource: " + desc);
        }

        final DataSource ds = dataSourceFactory.create(desc);
        if (ds == null) {
            throw new NullPointerException(
                    "DataSourceFactory returned a null DataSource");
        }

        return ds;
    }


    public DataSourceFactory getDataSourceFactory() {
        return dataSourceFactory;
    }


    public void setDataSourceFactory(DataSourceFactory dataSourceFactory) {
        if (dataSourceFactory == null) {
            throw new NullPointerException("dataSourceFactory");
        }
        this.dataSourceFactory = dataSourceFactory;

        if (log.isInfoEnabled()) {
            log.info("Using DataSourceFactory: "
                    + dataSourceFactory.getClass().getName());
        }
    }
}
