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


import java.io.ByteArrayInputStream;
import java.io.InputStream;

import junit.framework.TestCase;


public class IOUtilsTest extends TestCase {
    public void testCloseNull() {
        IOUtils.close(null);
    }


    public void testClose() {
        final InputStream input = new ByteArrayInputStream(new byte[0]);
        IOUtils.close(input);
    }


    public void testCloseException() {
        final InputStream input = new CloseWithExceptionInputStream();
        IOUtils.close(input);
    }
}
