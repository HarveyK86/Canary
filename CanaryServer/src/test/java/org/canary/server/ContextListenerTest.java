package org.canary.server;

import javax.servlet.ServletContextEvent;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public final class ContextListenerTest {

    @Mock
    private ServletContextEvent event;

    @Test
    public void contextInitialisedShouldExecute() {

	final ContextListener contextListener = new ContextListener();

	contextListener.contextInitialized(this.event);
    }

    @Test
    public void contextDestroyedShouldExecute() {

	final ContextListener contextListener = new ContextListener();

	contextListener.contextDestroyed(this.event);
    }

}
