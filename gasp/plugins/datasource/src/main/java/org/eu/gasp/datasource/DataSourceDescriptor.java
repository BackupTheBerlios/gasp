package org.eu.gasp.datasource;


import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;


public final class DataSourceDescriptor {
    private final String id;
    private final String url;
    private final String driver;
    private final String user;
    private final String password;


    public DataSourceDescriptor(final String id, final String url,
            final String driver, final String user, final String password) {
        if (StringUtils.isBlank(id)) {
            throw new IllegalArgumentException("id");
        }
        if (StringUtils.isBlank(url)) {
            throw new IllegalArgumentException("url");
        }
        if (StringUtils.isBlank(driver)) {
            throw new IllegalArgumentException("driver");
        }
        this.id = id;
        this.url = url;
        this.driver = driver;
        this.user = user;
        this.password = password;
    }


    public String getDriver() {
        return driver;
    }


    public String getPassword() {
        return password;
    }


    public String getUrl() {
        return url;
    }


    public String getUser() {
        return user;
    }


    @Override
    public boolean equals(Object obj) {
        return EqualsBuilder.reflectionEquals(this, obj);
    }


    @Override
    public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this);
    }


    public String getId() {
        return id;
    }
}
