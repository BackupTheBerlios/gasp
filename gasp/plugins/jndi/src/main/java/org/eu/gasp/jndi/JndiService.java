package org.eu.gasp.jndi;


public interface JndiService {
    void bind(String name, Object resource);


    void unbind(String name);


    Object lookup(String name);
}
