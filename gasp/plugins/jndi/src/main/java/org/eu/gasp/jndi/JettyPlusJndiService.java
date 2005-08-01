package org.eu.gasp.jndi;


import javax.naming.NamingException;
import javax.naming.spi.NamingManager;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.eu.gasp.core.GaspException;


public class JettyPlusJndiService extends AbstractJndiService {
    private static boolean initDone = false;
    private final Log log = LogFactory.getLog(getClass());


    public JettyPlusJndiService() {
        super();

        try {
            init();
        } catch (Exception e) {
            throw new GaspException("Unable to initialize JNDI context", e);
        }

        if (log.isDebugEnabled()) {
            log.debug("Once started, the Jetty JNDI registry "
                    + "cannot be stopped.");
            log.debug("Moreover, any binded entries cannot be unbinded.");
        }
    }


    private void init() throws NamingException {
        if (initDone) {
            return;
        }

        NamingManager
                .setInitialContextFactoryBuilder(new JettyPlusInitialContextFactoryBuilder());
        initENC();

        initDone = true;
    }
}
