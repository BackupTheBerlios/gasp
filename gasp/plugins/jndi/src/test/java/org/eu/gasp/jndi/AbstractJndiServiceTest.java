package org.eu.gasp.jndi;


import java.util.Date;

import javax.naming.Context;

import org.eu.gasp.core.Version;

import junit.framework.TestCase;


public abstract class AbstractJndiServiceTest extends TestCase {
    protected abstract JndiService createJndiService();


    public void testLookupENC() {
        final JndiService js = createJndiService();
        final Context ctx = (Context) js.lookup("java:comp");
        assertNotNull(ctx);
    }


    public void testBindAndLookup() {
        final JndiService js = createJndiService();
        bindAndLookup(js, "now-bindAndLookup", new Date());
        bindAndLookup(js, "java:comp/Version", new Version("1.0"));
    }


    private void bindAndLookup(JndiService js, String name, Object obj) {
        js.bind(name, obj);
        assertEquals(obj, js.lookup(name));
    }
}
