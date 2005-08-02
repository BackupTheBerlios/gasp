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

package org.eu.gasp.core.util;


import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import junit.framework.TestCase;


public class ReversedIteratorTest extends TestCase {
    public void testIterate() {
        final List<Integer> list = new ArrayList<Integer>();
        list.add(1);
        list.add(2);
        list.add(3);

        final Iterator<Integer> i = new ReversedIterator<Integer>(list
                .iterator());
        assertTrue(i.hasNext());
        assertEquals(Integer.valueOf(3), i.next());
        assertTrue(i.hasNext());
        assertEquals(Integer.valueOf(2), i.next());
        assertTrue(i.hasNext());
        assertEquals(Integer.valueOf(1), i.next());
        assertFalse(i.hasNext());
    }


    public void testConstructorNull() {
        try {
            new ReversedIterator<Object>(null);
            fail("Expected exception was not thrown");
        } catch (Exception e) {
            // expected
        }
    }


    public void testRemove() {
        final List<String> list = new ArrayList<String>();
        list.add("Test");

        final Iterator<String> i = new ReversedIterator<String>(list.iterator());
        assertEquals("Test", i.next());
        try {
            i.remove();
            fail("Expected exception was not thrown");
        } catch (Exception e) {
            // expected
        }
    }
}
