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


public class GaspExceptionTest extends TestCase {
    public void testConstructor1() {
        final GaspException ge = new GaspException("error");
        assertEquals("error", ge.getMessage());
        assertEquals(GaspException.UNKNOWN_CODE, ge.getCode());
        assertNull(ge.getCause());
    }


    public void testConstructor2() {
        final Exception cause = new IllegalStateException("boom");
        final GaspException ge = new GaspException("error", cause);
        assertEquals("error", ge.getMessage());
        assertEquals(GaspException.UNKNOWN_CODE, ge.getCode());
        assertEquals(cause, ge.getCause());
    }


    public void testConstructor3() {
        final int code = 100;
        final Exception cause = new IllegalStateException("boom");
        final GaspException ge = new GaspException("error", cause, code);
        assertEquals("error", ge.getMessage());
        assertEquals(code, ge.getCode());
        assertEquals(cause, ge.getCause());
    }
}
