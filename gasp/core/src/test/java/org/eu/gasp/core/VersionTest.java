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


import junit.framework.TestCase;


public class VersionTest extends TestCase {
    public void testConstructorString() {
        final Version v1 = new Version("1");
        assertEquals(1, v1.getMajor());
        assertEquals(0, v1.getMinor());
        assertEquals(0, v1.getRevision());
        assertNull(v1.getSuffix());

        final Version v2 = new Version("1.2");
        assertEquals(1, v2.getMajor());
        assertEquals(2, v2.getMinor());
        assertEquals(0, v2.getRevision());
        assertNull(v2.getSuffix());

        final Version v3 = new Version("1.2.3");
        assertEquals(1, v3.getMajor());
        assertEquals(2, v3.getMinor());
        assertEquals(3, v3.getRevision());
        assertNull(v3.getSuffix());

        final Version v4 = new Version("1.2.3-r4");
        assertEquals(1, v4.getMajor());
        assertEquals(2, v4.getMinor());
        assertEquals(3, v4.getRevision());
        assertEquals("r4", v4.getSuffix());

        final Version v5 = new Version("1.2-r1");
        assertEquals(1, v5.getMajor());
        assertEquals(2, v5.getMinor());
        assertEquals(0, v5.getRevision());
        assertEquals("r1", v5.getSuffix());
    }


    public void testCompareTo() {
        final Version v1 = new Version("1.0");
        final Version v2 = new Version("1.0.1");
        final Version v3 = new Version("1.0.1-r4");
        final Version v4 = new Version("1.1");
        final Version v5 = new Version("2.1");
        final Version v6 = new Version("1.0.1-r5");
        final Version v7 = new Version("1.0-r5");

        assertEquals(0, v1.compareTo(v1));
        assertEquals(-1, v1.compareTo(v2));
        assertEquals(1, v2.compareTo(v1));
        assertEquals(1, v3.compareTo(v1));
        assertEquals(-1, v1.compareTo(v3));
        assertEquals(1, v4.compareTo(v1));
        assertEquals(-1, v1.compareTo(v4));
        assertEquals(1, v5.compareTo(v1));
        assertEquals(-1, v1.compareTo(v5));
        assertEquals(0, v3.compareTo(v3));
        assertEquals(-1, v3.compareTo(v6));
        assertEquals(1, v6.compareTo(v3));
        assertEquals(-1, v1.compareTo(v7));
        assertEquals(1, v7.compareTo(v1));
    }


    public void testIsCompatible() {
        final Version v1 = new Version("1.0.1");
        final Version v2 = new Version("1.0.2");
        final Version v3 = new Version("2.0.1");

        assertTrue(v1.isCompatible(v1));
        assertTrue(v1.isCompatible(v2));
        assertFalse(v1.isCompatible(v3));

        assertTrue(v2.isCompatible(v2));
        assertTrue(v2.isCompatible(v1));
        assertFalse(v2.isCompatible(v3));

        assertTrue(v3.isCompatible(v3));
        assertFalse(v3.isCompatible(v1));
        assertFalse(v3.isCompatible(v2));

        try {
            v1.isCompatible(null);
            fail("Expected exception not thrown");
        } catch (Exception e) {
            // expected
        }
    }


    public void testConstructorIntInvalidPart() {
        try {
            new Version(-1, 0, 0, null);
            fail("Expected exception not thrown");
        } catch (Exception e) {
            // expected
        }
    }
}
