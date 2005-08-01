package org.eu.gasp.jndi;


import org.eu.gasp.core.AbstractPlugin;
import org.eu.gasp.core.PluginContext;


public class Plugin extends AbstractPlugin {
    @Override
    protected void onStart(PluginContext context) throws Exception {
        context.getServiceRegistry().register(JndiService.class,
                new JettyPlusJndiService());
    }


    @Override
    protected void onStop(PluginContext context) throws Exception {
        context.getServiceRegistry().unregister(JndiService.class);
    }
}
