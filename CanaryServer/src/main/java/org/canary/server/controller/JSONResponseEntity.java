package org.canary.server.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public final class JSONResponseEntity extends ResponseEntity<String> {

    private static final String JSON_ESCAPE_PREFIX = ")]}',\n";

    private static final ObjectMapper JSON_MAPPER = new ObjectMapper();

    public JSONResponseEntity(final Object model)
	    throws JsonProcessingException {

	super(JSON_ESCAPE_PREFIX + JSON_MAPPER.writeValueAsString(model),
		HttpStatus.OK);
    }

}
