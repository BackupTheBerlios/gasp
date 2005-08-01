package org.eu.gasp.jndi;


import java.util.Date;


public class JettyPlusJndiServiceTest extends AbstractJndiServiceTest {
    @Override
    protected JndiService createJndiService() {
        return new JettyPlusJndiService();
    }


    public void testUnbind() {
        final String id = "now-unbind";
        final Date now = new Date();

        final JndiService js = createJndiService();
        js.bind(id, now);

        try {
            js.unbind(id);
            fail("Expected exception was not thrown");
        } catch (Exception e) {
            // expected
        }
    }
}
