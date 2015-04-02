package org.canary.server.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.canary.server.service.CrudService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.util.ReflectionTestUtils;

@RunWith(MockitoJUnitRunner.class)
public final class MockitoAbstractControllerTest {

    @Mock
    private CrudService<String> service;

    @Mock
    private HttpServletRequest request;

    private CrudController controller;

    private static final String JSON = "Json";
    private static final String MODEL = "Model";
    private static final String WHITESPACE_STRING = " ";
    private static final String ID = "1";
    private static final String INVALID_ID = "Invalid ID";

    private static final Logger LOGGER = Logger
	    .getLogger(MockitoAbstractControllerTest.class);

    @Before
    @SuppressWarnings("unchecked")
    public void before() throws IOException {

	this.controller = Mockito.mock(AbstractController.class);

	ReflectionTestUtils.setField(this.controller, "service", this.service);

	Mockito.doCallRealMethod() //
		.when(this.controller) //
		.read(Matchers.anyString());

	Mockito.doCallRealMethod() //
		.when(this.controller) //
		.update(Matchers.anyString(), //
			Matchers.any(HttpServletRequest.class));

	Mockito.doCallRealMethod() //
		.when(this.controller) //
		.delete(Matchers.anyString());

	Mockito.when(((AbstractController<String>) this.controller) //
		.getRequestBody(Matchers.any(HttpServletRequest.class))) //
		.thenReturn(JSON);

	Mockito.when(((AbstractController<String>) this.controller) //
		.getModel(Matchers.anyString())) //
		.thenReturn(MODEL);

	Mockito.when(this.service //
		.read(Matchers.anyInt())) //
		.thenReturn(MODEL);
    }

    @Test
    public void readShouldThrowIllegalArgument() {

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
    public void readShouldReturnOK() {

	final ResponseEntity<String> response = this.controller.read(ID);

	Assert.assertEquals(response.getBody(), HttpStatus.OK,
		response.getStatusCode());
    }

    @Test
    public void readShouldReturnINTERNAL_SERVER_ERROR() {

	final ResponseEntity<String> response = this.controller
		.read(INVALID_ID);

	Assert.assertEquals(HttpStatus.INTERNAL_SERVER_ERROR,
		response.getStatusCode());
    }

    @Test
    public void updateIdShouldThrowIllegalArgument() {

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
    public void updateRequestShouldThrowIllegalArgument() {
	this.controller.update(ID, null);
    }

    @Test
    public void updateShouldReturnOK() {

	final ResponseEntity<String> response = this.controller.update(ID,
		this.request);

	Assert.assertEquals(response.getBody(), HttpStatus.OK,
		response.getStatusCode());
    }

    @Test
    public void updateShouldReturnINTERNAL_SERVER_ERROR() {

	final ResponseEntity<String> response = this.controller.update(
		INVALID_ID, this.request);

	Assert.assertEquals(HttpStatus.INTERNAL_SERVER_ERROR,
		response.getStatusCode());
    }

    @Test
    public void deleteShouldThrowIllegalArgument() {

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
    public void deleteShouldReturnOK() {

	final ResponseEntity<String> response = this.controller.delete(ID);

	Assert.assertEquals(response.getBody(), HttpStatus.OK,
		response.getStatusCode());
    }

    @Test
    public void deleteShouldReturnINTERNAL_SERVER_ERROR() {

	final ResponseEntity<String> response = this.controller
		.delete(INVALID_ID);

	Assert.assertEquals(HttpStatus.INTERNAL_SERVER_ERROR,
		response.getStatusCode());
    }

}
