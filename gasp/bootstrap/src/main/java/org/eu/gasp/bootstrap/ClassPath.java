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

package org.eu.gasp.bootstrap;


import java.io.File;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


/**
 * <tt>ClassLoader</tt> factory based from a JAR file list. Inspired by Jetty
 * <tt>ClassPath</tt> class.
 */
public final class ClassPath {
    private final List<URL> components = new ArrayList<URL>(2);


    /**
     * Adds a JAR file to the classpath.
     * 
     * @param file JAR file
     */
    public void addComponent(File file) {
        if (file == null) {
            throw new NullPointerException("file");
        }
        final URL url;
        try {
            url = file.toURI().toURL();
        } catch (Exception e) {
            // unlikely to happen
            throw new IllegalStateException("Unable to get URL from file: "
                    + file.getName(), e);
        }
        addComponent(url);
    }


    /**
     * Adds an URL to the classpath pointing to a JAR file.
     * 
     * @param url JAR file
     */
    public void addComponent(URL url) {
        if (url == null) {
            throw new NullPointerException("url");
        }
        components.add(url);
    }


    /**
     * Returns an unmodifiable list of JAR file URLs.
     * 
     * @return unmodifiable list
     */
    public List<URL> getComponents() {
        return Collections.unmodifiableList(components);
    }


    /**
     * Creates a <tt>ClassLoader</tt> based on the registered components. The
     * parent <tt>ClassLoader</tt> is set to either:
     * <ul>
     * <li>the thread context <tt>ClassLoader</tt>,</li>
     * <li>the <tt>ClassLoader</tt> which loaded this class,</li>
     * <li>the system <tt>ClassLoader</tt>.
     * </ul>
     * 
     * @return a newly <tt>ClassLoader</tt>
     */
    public ClassLoader createClassLoader() {
        ClassLoader parent = Thread.currentThread().getContextClassLoader();
        if (parent == null) {
            parent = getClass().getClassLoader();
        }
        if (parent == null) {
            parent = ClassLoader.getSystemClassLoader();
        }

        assert parent != null : "Null parent classloader is not allowed";

        return new URLClassLoader(components
                .toArray(new URL[components.size()]), parent);
    }
}
