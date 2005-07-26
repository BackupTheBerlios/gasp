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
import java.lang.reflect.Method;


/**
 * Main class for bootstrapping the platform.
 */
public class Main {
    /**
     * Entry point.
     * 
     * @param args arguments from the command line
     */
    public static void main(String... args) {
        try {
            // creating the platform class path
            final ClassPath classPath = new ClassPath();
            // we assume the system property "gasp.home" is set to the home of
            // the platform; if not, we use the current directory.
            // from there, we can load libraries from "./lib" and plugins
            // from "./plugins"
            final File[] jarFiles = new File(System.getProperty("gasp.home",
                    System.getProperty("user.dir")), "lib").listFiles();
            if (jarFiles != null) {
                // adding all libraries from the "lib" directory to the
                // classpath
                for (final File file : jarFiles) {
                    if (file.isDirectory()
                            || !file.getName().toLowerCase().endsWith(".jar")) {
                        continue;
                    }
                    classPath.addComponent(file.getCanonicalFile());
                }
            }

            final ClassLoader classLoader = classPath.createClassLoader();

            // loading Runner class through our new class loader
            final Class runnerClass = classLoader
                    .loadClass("org.eu.gasp.core.bootstrap.Runner");

            // creating new Runner, and invoke the run() method
            final Object runner = runnerClass.newInstance();
            final Method runMethod = runnerClass.getDeclaredMethod("run",
                    new Class[] { String[].class });
            runMethod.invoke(runner, new Object[] { args });
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(2);
        }
    }
}
