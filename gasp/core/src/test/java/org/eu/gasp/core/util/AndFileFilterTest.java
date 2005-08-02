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


import java.io.FileFilter;
import java.util.ArrayList;
import java.util.Collection;

import junit.framework.TestCase;


public class AndFileFilterTest extends TestCase {
    public void testConstructorNull() {
        try {
            new AndFileFilter(null);
            fail("Expected exception was not thrown");
        } catch (Exception e) {
            // expected
        }
    }


    public void testConstructorDefault() {
        final AndFileFilter filter = new AndFileFilter();
        assertTrue(filter.getFileFilters().isEmpty());
    }


    public void testSetFileFilter() {
        final Collection<FileFilter> newFileFilters = new ArrayList<FileFilter>();
        newFileFilters.add(new ExtensionFileFilter(".java"));

        final AndFileFilter filter = new AndFileFilter();
        filter.setFileFilters(newFileFilters);
        assertEquals(newFileFilters, filter.getFileFilters());
        assertEquals(newFileFilters.size(), filter.getFileFilters().size());
    }
}
