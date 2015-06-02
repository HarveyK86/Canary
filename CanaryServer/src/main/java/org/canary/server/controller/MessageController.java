package org.canary.server.controller;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.canary.server.model.Message;
import org.canary.server.service.CrudService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping(value = "message")
public class MessageController extends AbstractController<Message> {

    @Autowired
    private CrudService<Message> service;

    private static final Logger LOGGER = Logger
	    .getLogger(MessageController.class);

    private MessageController() {
	super();
    }

    @Override
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<String> create(final HttpServletRequest request) {

	LOGGER.debug("create[request=" + request + "]");

	if (request == null) {

	    throw new IllegalArgumentException(
		    "Illegal argument; request cannot be null.");
	}

	final String json;
	final String value;

	Message message;
	ResponseEntity<String> response;

	try {

	    json = super.getRequestBody(request);
	    message = super.getModel(json);
	    value = message.getValue();
	    message = this.service.create(value);
	    response = super.getResponse(message);

	} catch (final Exception e) {
	    response = super.getResponse(e);
	}

	return response;
    }

    @Override
    public final Class<Message> getModelClass() {

	LOGGER.debug("getModelClass");

	return Message.class;
    }

    @Override
    public final CrudService<Message> getService() {

	LOGGER.debug("getService");

	return this.service;
    }

}
