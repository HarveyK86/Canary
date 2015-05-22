package org.canary.server.controller;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.canary.server.model.User;
import org.canary.server.service.CrudService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping(value = "user")
public class UserController extends AbstractController<User> {

    @Autowired
    private CrudService<User> service;

    private static final Logger LOGGER = Logger.getLogger(UserController.class);

    private UserController() {
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
	final String username;
	final String password;

	User user;
	ResponseEntity<String> response;

	try {

	    json = super.getRequestBody(request);
	    user = super.getModel(json);
	    username = user.getUsername();
	    password = user.getPassword();
	    user = this.service.create(username, password);
	    response = super.getResponse(user);

	} catch (final Exception e) {
	    response = super.getResponse(e);
	}

	return response;
    }

    @Override
    public final Class<User> getModelClass() {

	LOGGER.debug("getModelClass");

	return User.class;
    }

    @Override
    public final CrudService<User> getService() {

	LOGGER.debug("getService");

	return this.service;
    }

}
