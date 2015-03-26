package org.canary.server.controller;

import javax.servlet.http.HttpServletRequest;

import org.canary.server.model.Canary;
import org.canary.server.service.CrudService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping(value = "canary")
public final class CanaryController extends AbstractController<Canary> {

    @Autowired
    private CrudService<Canary> service;

    private CanaryController() {
	super();
    }

    @Override
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<String> create(final HttpServletRequest request) {

	final String message;
	final Canary canary;

	ResponseEntity<String> response;

	try {

	    message = this.getRequestBody(request);
	    canary = this.service.create(message);
	    response = this.getResponse(canary);

	} catch (final Exception e) {
	    response = this.getResponse(e);
	}

	return response;
    }

    @Override
    public final Class<Canary> getModelClass() {
	return Canary.class;
    }

    @Override
    public final CrudService<Canary> getService() {
	return this.service;
    }

}
