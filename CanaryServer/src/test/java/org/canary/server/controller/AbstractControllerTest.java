package org.canary.server.controller;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public abstract class AbstractControllerTest<Model> {

    private HttpServletRequest request;

    private CrudController controller;

    private static final String WHITESPACE_STRING = " ";
    private static final String ID = "1";
    private static final String INVALID_ID = "Invalid ID";

    private static final Logger LOGGER = Logger
	    .getLogger(AbstractControllerTest.class);

    @Before
    public final void before() {

	this.controller = this.getController();
	this.request = this.getRequest();
    }

    public abstract CrudController getController();

    public abstract HttpServletRequest getRequest();

    @Test
    public final void readShouldThrowIllegalArgument() {

	for (final String id : new String[] { null, StringUtils.EMPTY,
		WHITESPACE_STRING }) {

	    try {

		this.controller.read(id);
		Assert.fail();

	    } catch (final IllegalArgumentException e) {
		LOGGER.debug("Expected illegal argument caught while testing.");
	    }
	}
    }

    @Test
    public final void readShouldReturnOK() {

	final ResponseEntity<String> response = this.controller.read(ID);

	Assert.assertEquals(response.getBody(), HttpStatus.OK,
		response.getStatusCode());
    }

    @Test
    public final void readShouldReturnINTERNAL_SERVER_ERROR() {

	final ResponseEntity<String> response = this.controller
		.read(INVALID_ID);

	Assert.assertEquals(HttpStatus.INTERNAL_SERVER_ERROR,
		response.getStatusCode());
    }

    @Test
    public final void readAllShouldReturnOK() {

	final ResponseEntity<String> response = this.controller.readAll();

	Assert.assertEquals(response.getBody(), HttpStatus.OK,
		response.getStatusCode());
    }

    @Test
    public abstract void readAllShouldReturnINTERNAL_SERVER_ERROR();

    @Test
    public final void updateIdShouldThrowIllegalArgument() {

	for (final String id : new String[] { null, StringUtils.EMPTY,
		WHITESPACE_STRING }) {

	    try {

		this.controller.update(id, this.request);
		Assert.fail();

	    } catch (final IllegalArgumentException e) {
		LOGGER.debug("Expected illegal argument caught while testing.");
	    }
	}
    }

    @Test(expected = IllegalArgumentException.class)
    public final void updateRequestShouldThrowIllegalArgument() {
	this.controller.update(ID, null);
    }

    @Test
    public final void updateShouldReturnOK() {

	final ResponseEntity<String> response = this.controller.update(ID,
		this.request);

	Assert.assertEquals(response.getBody(), HttpStatus.OK,
		response.getStatusCode());
    }

    @Test
    public final void updateShouldReturnINTERNAL_SERVER_ERROR() {

	final ResponseEntity<String> response = this.controller.update(
		INVALID_ID, this.request);

	Assert.assertEquals(HttpStatus.INTERNAL_SERVER_ERROR,
		response.getStatusCode());
    }

    @Test
    public final void deleteShouldThrowIllegalArgument() {

	for (final String id : new String[] { null, StringUtils.EMPTY,
		WHITESPACE_STRING }) {

	    try {

		this.controller.delete(id);
		Assert.fail();

	    } catch (final IllegalArgumentException e) {
		LOGGER.debug("Expected illegal argument caught while testing.");
	    }
	}
    }

    @Test
    public final void deleteShouldReturnOK() {

	final ResponseEntity<String> response = this.controller.delete(ID);

	Assert.assertEquals(response.getBody(), HttpStatus.OK,
		response.getStatusCode());
    }

    @Test
    public final void deleteShouldThrowINTERNAL_SERVER_ERROR() {

	final ResponseEntity<String> response = this.controller
		.delete(INVALID_ID);

	Assert.assertEquals(HttpStatus.INTERNAL_SERVER_ERROR,
		response.getStatusCode());
    }

    public final int getValidId() {
	return Integer.valueOf(ID);
    }

}
