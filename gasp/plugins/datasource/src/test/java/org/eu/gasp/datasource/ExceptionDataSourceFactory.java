package org.eu.gasp.datasource;


import javax.sql.DataSource;


public class ExceptionDataSourceFactory extends AbstractDataSourceFactory {
    @Override
    protected DataSource doCreate(DataSourceDescriptor desc) throws Exception {
        throw new IllegalStateException("Surprise!");
    }
}
