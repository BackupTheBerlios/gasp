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


import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;


/**
 * Descriptor for a <tt>DataSource</tt>.
 */
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


    @Override
    public String toString() {
        // we don't want the password property to appear here, so we protect it
        return new ToStringBuilder(this).append("id", id).append("url", url)
                .append("driver", driver).append("user", user).append(
                        "password", "***protected***").toString();
    }


    public String getId() {
        return id;
    }
}
