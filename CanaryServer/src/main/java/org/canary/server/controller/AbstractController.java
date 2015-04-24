package org.canary.server.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.canary.server.service.CrudService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public abstract class AbstractController<Model> implements CrudController {

    private Class<Model> clazz;

    private CrudService<Model> service;

    private static final ObjectMapper JSON_MAPPER = new ObjectMapper();

    private static final Logger LOGGER = Logger
	    .getLogger(AbstractController.class);

    protected AbstractController() {
	super();
    }

    @PostConstruct
    public final void postConstruct() {

	LOGGER.debug("postConstruct");

	this.clazz = this.getModelClass();
	this.service = this.getService();
    }

    public abstract Class<Model> getModelClass();

    public abstract CrudService<Model> getService();

    @Override
    @RequestMapping(method = RequestMethod.GET, value = "/{id}")
    public ResponseEntity<String> read(@PathVariable("id") final String id) {

	LOGGER.debug("read[id=" + id + "]");

	if (StringUtils.isBlank(id)) {

	    throw new IllegalArgumentException(
		    "Illegal argument; id cannot be blank.");
	}

	final int identifier;
	final Model model;

	ResponseEntity<String> response;

	try {

	    identifier = Integer.valueOf(id);
	    model = this.service.read(identifier);
	    response = this.getResponse(model);

	} catch (final Exception e) {
	    response = this.getResponse(e);
	}

	return response;
    }

    @Override
    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<String> readAll() {

	LOGGER.debug("readAll");

	final List<Model> models;

	ResponseEntity<String> response;

	try {

	    models = this.service.readAll();
	    response = this.getResponse(models);

	} catch (final Exception e) {
	    response = this.getResponse(e);
	}

	return response;
    }

    @Override
    @RequestMapping(method = RequestMethod.POST, value = "/{id}")
    public ResponseEntity<String> update(@PathVariable("id") final String id,
	    final HttpServletRequest request) {

	LOGGER.debug("update[id=" + id + ", request=" + request + "]");

	if (StringUtils.isBlank(id) || request == null) {

	    throw new IllegalArgumentException(
		    "Illegal argument; id cannot be blank and request cannot be null.");
	}

	final int identifier;
	final String json;
	final Model model;

	ResponseEntity<String> response;

	try {

	    identifier = Integer.valueOf(id);
	    json = this.getRequestBody(request);
	    model = this.getModel(json);
	    this.service.update(identifier, model);
	    response = this.getResponse();

	} catch (final Exception e) {
	    response = this.getResponse(e);
	}

	return response;
    }

    @Override
    @RequestMapping(method = RequestMethod.DELETE, value = "/{id}")
    public ResponseEntity<String> delete(@PathVariable("id") final String id) {

	LOGGER.debug("delete[id=" + id + "]");

	if (StringUtils.isBlank(id)) {

	    throw new IllegalArgumentException(
		    "Illegal argument; id cannot be blank.");
	}

	final int identifier;

	ResponseEntity<String> response;

	try {

	    identifier = Integer.valueOf(id);
	    this.service.delete(identifier);
	    response = this.getResponse();

	} catch (final Exception e) {
	    response = this.getResponse(e);
	}

	return response;
    }

    protected String getRequestBody(final HttpServletRequest request)
	    throws IOException {

	LOGGER.debug("getRequestBody[request=" + request + "]");

	if (request == null) {

	    throw new IllegalArgumentException(
		    "Illegal argument; request cannot be null.");
	}

	final BufferedReader reader = request.getReader();
	final StringBuffer buffer = new StringBuffer();

	String line;

	do {

	    line = reader.readLine();
	    buffer.append(line == null ? StringUtils.EMPTY : line);

	} while (line != null);

	return buffer.toString();
    }

    protected final ResponseEntity<String> getResponse(final Model model)
	    throws JsonProcessingException {

	LOGGER.debug("getResponse[model=" + model + "]");

	if (model == null) {

	    throw new IllegalArgumentException(
		    "Illegal argument; model cannot be null.");
	}

	final String json = JSON_MAPPER.writeValueAsString(model);

	return new ResponseEntity<String>(json, HttpStatus.OK);
    }

    protected final ResponseEntity<String> getResponse(final List<Model> models)
	    throws JsonProcessingException {

	LOGGER.debug("getResponse[models=" + models + "]");

	if (models == null) {

	    throw new IllegalArgumentException(
		    "Illegal argument; model cannot be null.");
	}

	final String json = JSON_MAPPER.writeValueAsString(models);

	return new ResponseEntity<String>(json, HttpStatus.OK);
    }

    protected final ResponseEntity<String> getResponse(final Exception caught) {

	LOGGER.debug("getResponse[caught=" + caught + "]");

	if (caught == null) {

	    throw new IllegalArgumentException(
		    "Illegal argument; caught cannot be null.");
	}

	final StringWriter stringWriter = new StringWriter();
	final PrintWriter printWriter = new PrintWriter(stringWriter);
	final String error;

	caught.printStackTrace(printWriter);
	error = stringWriter.toString();

	return new ResponseEntity<String>(error,
		HttpStatus.INTERNAL_SERVER_ERROR);
    }

    protected final ResponseEntity<String> getResponse() {

	LOGGER.debug("getResponse");

	return new ResponseEntity<String>(HttpStatus.OK);
    }

    protected Model getModel(final String json) throws IOException {

	LOGGER.debug("getModel[json=" + json + "]");

	if (StringUtils.isBlank(json)) {

	    throw new IllegalArgumentException(
		    "Illegal argument; json cannot be blank.");
	}

	return JSON_MAPPER.readValue(json, this.clazz);
    }

}
