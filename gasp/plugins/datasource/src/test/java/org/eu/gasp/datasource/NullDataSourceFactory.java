package org.eu.gasp.datasource;


import javax.sql.DataSource;


public class NullDataSourceFactory extends AbstractDataSourceFactory {
    @Override
    protected DataSource doCreate(DataSourceDescriptor desc) throws Exception {
        return null;
    }
}
