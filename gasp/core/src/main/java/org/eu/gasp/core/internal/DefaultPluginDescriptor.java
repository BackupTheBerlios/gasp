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

package org.eu.gasp.core.internal;


import java.util.Collections;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.eu.gasp.core.PluginDependency;
import org.eu.gasp.core.PluginDescriptor;
import org.eu.gasp.core.Version;


public class DefaultPluginDescriptor implements PluginDescriptor {
    private final String id;
    private final Version version;
    private final String pluginClassName;
    private final List<? extends PluginDependency> pluginDependencies;
    private final List<String> services;


    @SuppressWarnings("unchecked")
    public DefaultPluginDescriptor(final String id, final Version version,
            final String pluginClassName,
            final List<? extends PluginDependency> pluginDependencies,
            final List<String> services) {
        if (StringUtils.isBlank(id)) {
            throw new IllegalArgumentException("id");
        }
        if (version == null) {
            throw new NullPointerException("version");
        }
        this.id = id;
        this.version = version;
        this.pluginClassName = pluginClassName;
        this.pluginDependencies = pluginDependencies == null ? Collections.EMPTY_LIST
                : pluginDependencies;
        this.services = services == null ? Collections.EMPTY_LIST : services;
    }


    public String getId() {
        return id;
    }


    public Version getVersion() {
        return version;
    }


    public String getPluginClassName() {
        return pluginClassName;
    }


    public List<? extends PluginDependency> getPluginDependencies() {
        return pluginDependencies;
    }


    public List<String> getServices() {
        return services;
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
        return getId() + "-" + getVersion();
    }
}
