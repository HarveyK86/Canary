package org.canary.server.controller;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.canary.server.model.Canary;
import org.canary.server.service.CrudService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping(value = "canary")
public class CanaryController extends AbstractController<Canary> {

    @Autowired
    private CrudService<Canary> service;

    private static final Logger LOGGER = Logger
	    .getLogger(CanaryController.class);

    private CanaryController() {
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

	final String message;
	final Canary canary;

	ResponseEntity<String> response;

	try {

	    message = super.getRequestBody(request);
	    canary = this.service.create(message);
	    response = super.getResponse(canary);

	} catch (final Exception e) {
	    response = this.getResponse(e);
	}

	return response;
    }

    @Override
    public final Class<Canary> getModelClass() {

	LOGGER.debug("getModelClass");

	return Canary.class;
    }

    @Override
    public final CrudService<Canary> getService() {

	LOGGER.debug("getService");

	return this.service;
    }

}
