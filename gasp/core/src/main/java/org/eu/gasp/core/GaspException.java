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


/**
 * Base exception. A code property is provided in order to clearly identify
 * errors, and ease the resolution of a problem. If no error code is specified,
 * the code is set to the constant <tt>UNKNOWN_CODE</tt>.
 */
public class GaspException extends Exception {
    public static final int UNKNOWN_CODE = 0;
    private final int code;


    public GaspException(final String msg, final Throwable cause) {
        this(msg, cause, UNKNOWN_CODE);
    }


    public GaspException(final String msg, final Throwable cause, final int code) {
        super(msg, cause);
        this.code = code;
    }


    public GaspException(final String msg) {
        this(msg, null);
    }


    public int getCode() {
        return code;
    }
}
