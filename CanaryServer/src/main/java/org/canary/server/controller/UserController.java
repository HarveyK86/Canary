package org.canary.server.controller;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.canary.server.model.User;
import org.canary.server.service.CrudService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping(value = "user")
public class UserController extends AbstractController<User> {

    @Autowired
    private CrudService<User> service;

    private static final PasswordEncoder ENCODER = new BCryptPasswordEncoder();

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
	final String passwordHash;

	User user;
	ResponseEntity<String> response;

	try {

	    json = super.getRequestBody(request);
	    user = super.getModel(json);
	    username = user.getUsername();
	    password = user.getPassword();

	    if (StringUtils.isBlank(password)) {

		throw new IllegalStateException(
			"Illegal state; password cannot be null or empty.");
	    }

	    passwordHash = ENCODER.encode(password);
	    user = this.service.create(username, passwordHash);
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

    @Override
    public User getValidModel(final int id, final User candidate) {

	final String username = candidate.getUsername();
	final User user = this.service.read(id);
	final String password = candidate.getPassword();
	final String passwordHash;

	if (StringUtils.isNotBlank(username)) {
	    user.setUsername(username);
	}

	if (StringUtils.isNotBlank(password)) {

	    passwordHash = ENCODER.encode(password);
	    user.setPassword(passwordHash);
	}

	return user;
    }

}
