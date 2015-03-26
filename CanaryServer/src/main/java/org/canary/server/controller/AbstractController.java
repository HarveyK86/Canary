package org.canary.server.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.canary.server.service.CrudService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public abstract class AbstractController<Model> implements CrudController
{
	private Class<Model>				clazz;

	private CrudService<Model>			service;

	private static final ObjectMapper	JSON_MAPPER	= new ObjectMapper();

	protected AbstractController()
	{
		super();
	}

	@PostConstruct
	public final void postConstruct()
	{
		this.clazz = this.getModelClass();
		this.service = this.getService();
	}

	public abstract Class<Model> getModelClass();

	public abstract CrudService<Model> getService();

	@Override
	@RequestMapping(method = RequestMethod.GET, value = "/{id}")
	public final ResponseEntity<String> read(@PathVariable("id") final String id)
	{
		final int identifier;
		final Model model;

		ResponseEntity<String> response;

		try
		{
			identifier = Integer.valueOf(id);
			model = this.service.read(identifier);
			response = this.getResponse(model);
		}
		catch ( final Exception e )
		{
			response = this.getResponse(e);
		}

		return response;
	}

	@Override
	@RequestMapping(method = RequestMethod.POST, value = "/{id}")
	public final ResponseEntity<String> update(@PathVariable("id") final String id, final HttpServletRequest request)
	{
		final String json;
		final Model model;

		ResponseEntity<String> response;

		try
		{
			json = this.getRequestBody(request);
			model = this.getModel(json);
			this.service.update(model);
			response = this.getResponse();
		}
		catch ( final Exception e )
		{
			response = this.getResponse(e);
		}

		return response;
	}

	@Override
	@RequestMapping(method = RequestMethod.DELETE, value = "/{id}")
	public final ResponseEntity<String> delete(@PathVariable("id") final String id)
	{
		final int identifier;

		ResponseEntity<String> response;

		try
		{
			identifier = Integer.valueOf(id);
			this.service.delete(identifier);
			response = this.getResponse();
		}
		catch ( final Exception e )
		{
			response = this.getResponse(e);
		}

		return response;
	}

	protected final String getRequestBody(final HttpServletRequest request) throws IOException
	{
		final BufferedReader reader = request.getReader();
		final StringBuffer buffer = new StringBuffer();

		String line;

		do
		{
			line = reader.readLine();
			buffer.append(line == null ? StringUtils.EMPTY : line);
		}
		while ( line != null );

		return buffer.toString();
	}

	protected final ResponseEntity<String> getResponse(final Model model) throws JsonProcessingException
	{
		final String json = JSON_MAPPER.writeValueAsString(model);

		return new ResponseEntity<String>(json, HttpStatus.OK);
	}

	protected final ResponseEntity<String> getResponse(final Exception caught)
	{
		final StringWriter stringWriter = new StringWriter();
		final PrintWriter printWriter = new PrintWriter(stringWriter);
		final String error;

		caught.printStackTrace(printWriter);
		error = stringWriter.toString();

		return new ResponseEntity<String>(error, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	protected final ResponseEntity<String> getResponse()
	{
		return new ResponseEntity<String>(HttpStatus.OK);
	}

	protected final Model getModel(final String json) throws IOException
	{
		return JSON_MAPPER.readValue(json, this.clazz);
	}

}
