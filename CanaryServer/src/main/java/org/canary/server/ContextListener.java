package org.canary.server;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.log4j.Logger;

public final class ContextListener implements ServletContextListener {

    private static final String PATH = "canary.properties";

    private static final Logger LOGGER = Logger
	    .getLogger(ContextListener.class);

    @Override
    public void contextInitialized(final ServletContextEvent event) {

	final Thread thread = Thread.currentThread();
	final ClassLoader loader = thread.getContextClassLoader();
	final InputStream input = loader.getResourceAsStream(PATH);
	final Properties properties = new Properties();

	String propertyValue;

	try {

	    properties.load(input);

	    for (final String propertyName : properties.stringPropertyNames()) {

		propertyValue = properties.getProperty(propertyName);
		System.setProperty(propertyName, propertyValue);
	    }

	} catch (final IOException e) {

	    LOGGER.error("IOException caught while attempting to read "
		    + "properties.");
	}
    }

    @Override
    public void contextDestroyed(final ServletContextEvent event) {
	// do nothing
    }

}
