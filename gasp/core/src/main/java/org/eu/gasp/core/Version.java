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

package org.eu.gasp.core;


import java.io.Serializable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;


/**
 * Version of a component. A version can contain up to four parts:
 * <ul>
 * <li>the major part (mandatory),</li>
 * <li>the minor part,</li>
 * <li>the revision part,</li>
 * <li>the suffix part.</li>
 * </ul>
 * <p>
 * A version is defined to be of the form: <tt>major</tt>.<tt>minor</tt>.<tt>revision</tt>-<tt>suffix</tt>.
 * </p>
 * <p>
 * Examples:
 * </p>
 * <ul>
 * <li>1.0</li>
 * <li>1.2.1</li>
 * <li>2.4-r2</li>
 * <li>2.5.1-r6</li>
 * </ul>
 */
public class Version implements Comparable<Version>, Serializable {
    private static final Pattern PATTERN = Pattern
            .compile("(\\d)+(\\.(\\d)+(\\.(\\d)+)?(\\-(.*))?)?");
    private final int major;
    private final int minor;
    private final int revision;
    private final String suffix;


    public Version(final int major, final int minor, final int revision,
            final String suffix) {
        checkVersionPart(major);
        checkVersionPart(minor);
        checkVersionPart(revision);

        this.major = major;
        this.minor = minor;
        this.revision = revision;
        this.suffix = StringUtils.trimToNull(suffix);
    }


    public Version(final String version) {
        if (StringUtils.isBlank(version)) {
            throw new IllegalArgumentException("version");
        }

        final Matcher matcher = PATTERN.matcher(version);
        if (!matcher.matches()) {
            throw new IllegalArgumentException("Incorrect version: " + version);
        }

        this.major = Integer.parseInt(matcher.group(1));
        this.minor = Integer.parseInt(StringUtils.defaultString(matcher
                .group(3), "0"));
        this.revision = Integer.parseInt(StringUtils.defaultString(matcher
                .group(5), "0"));
        this.suffix = StringUtils.trimToNull(matcher.group(7));
    }


    private void checkVersionPart(int version) {
        if (version < 0) {
            throw new IllegalArgumentException("Invalid version part: "
                    + version);
        }
    }


    public String getSuffix() {
        return suffix;
    }


    public int getMajor() {
        return major;
    }


    public int getMinor() {
        return minor;
    }


    public int getRevision() {
        return revision;
    }


    @Override
    public String toString() {
        final StringBuilder buf = new StringBuilder();
        buf.append(major).append(".").append(minor);
        if (revision > 0) {
            buf.append(".").append(revision);
        }
        if (!StringUtils.isBlank(suffix)) {
            buf.append("-").append(suffix);
        }

        return buf.toString();
    }


    @Override
    public boolean equals(Object obj) {
        return EqualsBuilder.reflectionEquals(this, obj);
    }


    @Override
    public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this);
    }


    public int compareTo(Version other) {
        if (major < other.major) {
            return -1;
        }
        if (major > other.major) {
            return 1;
        }
        if (minor < other.minor) {
            return -1;
        }
        if (minor > other.minor) {
            return 1;
        }
        if (revision < other.revision) {
            return -1;
        }
        if (revision > other.revision) {
            return 1;
        }
        if (suffix == null && other.suffix == null) {
            return 0;
        }
        if (suffix == null) {
            return -1;
        }
        if (other.suffix == null) {
            return 1;
        }

        return suffix.compareTo(other.suffix);
    }


    /**
     * Returns <tt>true</tt> if the two versions are compatible. Two versions
     * are compatible if the major parts are equal and if the minor parts are
     * equal.
     * 
     * @param other version to compare to
     * @return <tt>true</tt> if the two versions are compatible
     */
    public boolean isCompatible(Version other) {
        if (other == null) {
            throw new NullPointerException("other");
        }

        return major == other.major && minor == other.minor;
    }
}
