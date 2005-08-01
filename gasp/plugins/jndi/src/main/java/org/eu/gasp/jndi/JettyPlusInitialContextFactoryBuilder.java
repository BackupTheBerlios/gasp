package org.eu.gasp.jndi;


import java.util.Hashtable;

import javax.naming.NamingException;
import javax.naming.spi.InitialContextFactory;
import javax.naming.spi.InitialContextFactoryBuilder;


public class JettyPlusInitialContextFactoryBuilder implements
        InitialContextFactoryBuilder {
    public InitialContextFactory createInitialContextFactory(
            Hashtable<?, ?> environment) throws NamingException {
        return new org.mortbay.jndi.InitialContextFactory();
    }
}
