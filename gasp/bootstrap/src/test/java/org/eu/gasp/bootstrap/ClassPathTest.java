package org.eu.gasp.bootstrap;


import java.io.File;

import junit.framework.TestCase;


public class ClassPathTest extends TestCase {
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
