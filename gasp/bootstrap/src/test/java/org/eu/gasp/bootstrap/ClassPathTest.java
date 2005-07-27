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
import java.util.List;

import junit.framework.TestCase;


public class ClassPathTest extends TestCase {
    public void testAddComponentNullURL() {
        final ClassPath cp = new ClassPath();
        try {
            cp.addComponent((URL) null);
            fail("Expected exception not thrown");
        } catch (Exception e) {
            // expected
        }
    }


    public void testAddComponentNullFile() {
        final ClassPath cp = new ClassPath();
        try {
            cp.addComponent((File) null);
            fail("Expected exception not thrown");
        } catch (Exception e) {
            // expected
        }
    }


    public void testGetComponents() {
        final URL url = getClass().getResource("/commons-beanutils-1.7.jar");

        final ClassPath cp = new ClassPath();
        cp.addComponent(url);

        final List<URL> components = cp.getComponents();
        assertNotNull(components);

        boolean error = false;
        try {
            components.add(url);
        } catch (UnsupportedOperationException e) {
            error = true;
        }
        assertTrue("Expected exception not thrown", error);
    }


    public void testGetClassLoaderURL() {
        final ClassPath cp = new ClassPath();
        assertBeanUtilClassLoadable(cp.createClassLoader(), false);

        cp.addComponent(getClass().getResource("/commons-beanutils-1.7.jar"));
        assertBeanUtilClassLoadable(cp.createClassLoader(), true);
    }


    public void testGetClassLoaderFile() throws Exception {
        final ClassPath cp = new ClassPath();
        assertBeanUtilClassLoadable(cp.createClassLoader(), false);

        final File file = new File("target/test-classes",
                "commons-beanutils-1.7.jar");
        assertTrue("Cannot find file: " + file.getName(), file.exists());

        cp.addComponent(file);
        assertBeanUtilClassLoadable(cp.createClassLoader(), true);
    }


    private void assertBeanUtilClassLoadable(ClassLoader cl, boolean loadable) {
        boolean error = false;
        try {
            cl.loadClass("org.apache.commons.beanutils.BeanUtils");
        } catch (Exception e) {
            error = true;
        }

        if (loadable && error) {
            fail("Unable to load BeanUtils class");
        }
        if (!loadable && !error) {
            fail("BeanUtils class shouldn't have been loaded");
        }
    }
}
