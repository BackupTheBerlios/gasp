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


import java.io.File;
import java.io.FileFilter;
import java.util.Collection;
import java.util.Collections;


/**
 * Composite file filter where all file filters must return <tt>true</tt> to
 * accept a file.
 */
public class AndFileFilter implements FileFilter {
    private Collection<FileFilter> fileFilters;


    @SuppressWarnings("unchecked")
    public AndFileFilter() {
        this(Collections.EMPTY_LIST);
    }


    public AndFileFilter(final Collection<FileFilter> fileFilters) {
        if (fileFilters == null) {
            throw new NullPointerException("fileFilters");
        }
        this.fileFilters = fileFilters;
    }


    public final boolean accept(File file) {
        for (final FileFilter fileFilter : fileFilters) {
            if (!fileFilter.accept(file)) {
                return false;
            }
        }

        return true;
    }


    public Collection<FileFilter> getFileFilters() {
        return fileFilters;
    }


    public void setFileFilters(Collection<FileFilter> fileFilters) {
        this.fileFilters = fileFilters;
    }

}
