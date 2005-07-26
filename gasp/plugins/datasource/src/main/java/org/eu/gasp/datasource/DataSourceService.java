package org.eu.gasp.datasource;


import java.util.HashMap;
import java.util.Map;
import java.util.WeakHashMap;

import javax.sql.DataSource;


public class DataSourceService {
    private final Map<String, DataSource> dataSourceCache = new WeakHashMap<String, DataSource>(
            1);
    private final Map<String, DataSourceDescriptor> dataSourceDescriptorCache = new HashMap<String, DataSourceDescriptor>(
            1);


    public DataSource lookup(String id) {
        if (id == null) {
            throw new NullPointerException("id");
        }

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
            // putting the newly DataSource in the cache for future use
            dataSourceCache.put(id, ds);
        }

        return ds;
    }


    public void register(DataSourceDescriptor desc) {
        if (desc == null) {
            throw new NullPointerException("desc");
        }
        dataSourceDescriptorCache.put(desc.getId(), desc);
    }


    public void unregister(String id) {
        dataSourceCache.remove(id);
        dataSourceDescriptorCache.remove(id);
    }


    protected DataSource createDataSource(DataSourceDescriptor desc) {
        // TODO do implementation
        return null;
    }
}
