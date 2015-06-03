package org.canary.server.controller;

import java.io.BufferedReader;
import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.canary.server.model.Message;
import org.canary.server.service.MessageService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.util.ReflectionTestUtils;

import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(MockitoJUnitRunner.class)
public final class MessageControllerTest extends
	AbstractControllerTest<Message> {

    @Mock
    private MessageService service;

    @Mock
    private HttpServletRequest request;

    @Mock
    private BufferedReader reader;

    private MessageController controller;

    private static final String VALUE = "Value";

    private static final ObjectMapper JSON_MAPPER = new ObjectMapper();

    @Override
    public CrudController getController() {

	final Message message = this.getModel();
	final String json;

	this.controller = Mockito.mock(MessageController.class);

	ReflectionTestUtils.setField(this.controller, "service", this.service);

	this.controller.postConstruct();

	Mockito.doCallRealMethod() //
		.when(this.controller) //
		.create(Matchers.any(HttpServletRequest.class));

	Mockito.doCallRealMethod() //
		.when(this.controller) //
		.read(Matchers.anyString());

	Mockito.doCallRealMethod() //
		.when(this.controller) //
		.readAll();

	Mockito.doCallRealMethod() //
		.when(this.controller) //
		.update(Matchers.anyString(), //
			Matchers.any(HttpServletRequest.class));

	Mockito.doCallRealMethod() //
		.when(this.controller) //
		.delete(Matchers.anyString());

	Mockito.doCallRealMethod() //
		.when(this.controller) //
		.getValidModel(Matchers.anyInt(), //
			Matchers.any(Message.class));

	Mockito.when(this.service //
		.read(Matchers.anyInt())) //
		.thenReturn(message);

	try {

	    Mockito.doCallRealMethod() //
		    .when(this.controller) //
		    .getRequestBody(Matchers.any(HttpServletRequest.class));

	    Mockito.doCallRealMethod() //
		    .when(this.controller) //
		    .getModel(Matchers.anyString());

	    Mockito.when(this.request //
		    .getReader()) //
		    .thenReturn(this.reader);

	    json = JSON_MAPPER.writeValueAsString(message);

	    Mockito.when(this.reader //
		    .readLine()) //
		    .thenReturn(json, (String) null);

	} catch (final IOException e) {
	    Assert.fail();
	}

	return this.controller;
    }

    @Override
    public HttpServletRequest getRequest() {
	return this.request;
    }

    @Override
    public Message getModel() {

	final Message message = new Message();
	final int id = super.getValidId();

	message.setId(id);
	message.setValue(VALUE);

	return message;
    }

    @Test(expected = IllegalArgumentException.class)
    public void createShouldThrowIllegalArgument() {
	this.controller.create(null);
    }

    @Test
    public void createShouldReturnOK() {

	final Message message = this.getModel();

	Mockito.when(this.service //
		.create(Matchers.anyString())) //
		.thenReturn(message);

	final ResponseEntity<String> response = this.controller
		.create(this.request);

	Assert.assertEquals(response.getBody(), HttpStatus.OK,
		response.getStatusCode());
    }

    @Test
    public void createShouldReturnINTERNAL_SERVER_ERROR() {

	Mockito.when(this.service //
		.create(Matchers.anyString())) //
		.thenReturn(null);

	final ResponseEntity<String> response = this.controller
		.create(this.request);

	Assert.assertEquals(HttpStatus.INTERNAL_SERVER_ERROR,
		response.getStatusCode());
    }

    @Override
    @SuppressWarnings("unchecked")
    public void readAllShouldReturnINTERNAL_SERVER_ERROR() {

	Mockito.when(this.service //
		.readAll()) //
		.thenThrow(Exception.class);

	final ResponseEntity<String> response = this.controller.readAll();

	Assert.assertEquals(HttpStatus.INTERNAL_SERVER_ERROR,
		response.getStatusCode());
    }

    @Test
    public void getValidModelNoDateShouldNotReturnNull() {

	final int id = super.getValidId();

	Message message = this.getModel();

	message.setValue(StringUtils.EMPTY);
	message = this.controller.getValidModel(id, message);

	Assert.assertNotNull(message);
    }

}
