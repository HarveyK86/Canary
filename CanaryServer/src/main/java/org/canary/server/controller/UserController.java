package org.canary.server.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.canary.server.model.Permission;
import org.canary.server.model.User;
import org.canary.server.service.CrudService;
import org.canary.server.service.UserServiceInterface;
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
    private UserServiceInterface service;

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
	final List<Permission> permissions;

	User user;
	ResponseEntity<String> response;

	try {

	    json = super.getRequestBody(request);
	    user = super.getModel(json);
	    username = user.getUsername();
	    password = user.getPassword();
	    permissions = user.getPermissions();

	    if (StringUtils.isBlank(password)) {

		throw new IllegalStateException(
			"Illegal state; password cannot be null or empty.");
	    }

	    passwordHash = ENCODER.encode(password);
	    user = this.service.create(username, passwordHash, permissions);
	    response = super.getResponse(user);

	} catch (final Exception e) {
	    response = super.getResponse(e);
	}

	return response;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/current")
    public ResponseEntity<String> readCurrent() {

	LOGGER.debug("readCurrent");

	final User user;

	ResponseEntity<String> response;

	try {

	    user = this.service.readCurrent();
	    response = this.getResponse(user);

	} catch (final Exception e) {
	    response = this.getResponse(e);
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

	LOGGER.debug("getValidModel[id=" + id + ", candidate=" + candidate
		+ "]");

	if (candidate == null) {

	    throw new IllegalArgumentException(
		    "Illegal argument; candidate cannot be null.");
	}

	final String username = candidate.getUsername();
	final User user = this.service.read(id);
	final String password = candidate.getPassword();
	final String passwordHash;
	final List<Permission> permissions = candidate.getPermissions();

	if (StringUtils.isNotBlank(username)) {
	    user.setUsername(username);
	}

	if (StringUtils.isNotBlank(password)) {

	    passwordHash = ENCODER.encode(password);
	    user.setPassword(passwordHash);
	}

	user.setPermissions(permissions);

	return user;
    }

}
