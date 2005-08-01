package org.eu.gasp.jndi;


import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.eu.gasp.core.GaspException;


public abstract class AbstractJndiService implements JndiService {
    private final Log log = LogFactory.getLog(getClass());


    public final void bind(String name, Object resource) {
        if (log.isInfoEnabled()) {
            log.info("Binding JNDI resource: " + name);
        }

        try {
            getInitialContext().bind(name, resource);
        } catch (Exception e) {
            throw new GaspException("Error while binding JNDI resource: "
                    + name, e);
        }
    }


    public final void unbind(String name) {
        if (log.isInfoEnabled()) {
            log.info("Unbinding JNDI resource: " + name);
        }

        try {
            getInitialContext().unbind(name);
        } catch (Exception e) {
            throw new GaspException("Error while unbinding JNDI resource: "
                    + name, e);
        }
    }


    public final Object lookup(String name) {
        try {
            return getInitialContext().lookup(name);
        } catch (Exception e) {
            throw new GaspException("Error while looking up JNDI resource: "
                    + name, e);
        }
    }


    protected final void initENC() throws NamingException {
        if (log.isInfoEnabled()) {
            log.info("Initializing JNDI context");
        }

        final InitialContext initCtx = getInitialContext();
        final Context compCtx = initCtx.createSubcontext("java:comp");
        compCtx.createSubcontext("env");
    }


    protected InitialContext getInitialContext() throws NamingException {
        return new InitialContext();
    }
}
