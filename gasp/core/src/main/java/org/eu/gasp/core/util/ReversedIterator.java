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
import java.util.Collections;
import java.util.Iterator;
import java.util.List;


/**
 * Reverse the order of an <tt>Iterator</tt>.
 * 
 * @param <T> type of the object we iterate on
 */
public class ReversedIterator<T> implements Iterator<T> {
    private final Iterator<T> i;


    public ReversedIterator(final Iterator<T> iterator) {
        if (iterator == null) {
            throw new NullPointerException("iterator");
        }

        final List<T> elements = new ArrayList<T>();
        while (iterator.hasNext()) {
            elements.add(iterator.next());
        }
        Collections.reverse(elements);

        i = elements.iterator();
    }


    public boolean hasNext() {
        return i.hasNext();
    }


    public T next() {
        return i.next();
    }


    public void remove() {
        throw new UnsupportedOperationException();
    }
}
