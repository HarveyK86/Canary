package org.canary.server.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.ResponseEntity;

public interface CrudController {

    ResponseEntity<String> create(final HttpServletRequest request);

    ResponseEntity<String> read(final String id);

    ResponseEntity<String> update(final String id,
	    final HttpServletRequest request);

    ResponseEntity<String> delete(final String id);

}
