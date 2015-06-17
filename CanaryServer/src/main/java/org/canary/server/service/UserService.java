package org.canary.server.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.canary.server.model.Permission;
import org.canary.server.model.User;
import org.canary.server.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService implements UserServiceInterface {

    @Autowired
    private UserRepository repository;

    private static final Logger LOGGER = Logger.getLogger(UserService.class);

    private UserService() {
	super();
    }

    @Override
    @Transactional
    @PreAuthorize("hasAuthority('CREATE_USER')")
    public User create(final String username, final String password,
	    final List<Permission> permissions) {

	LOGGER.debug("create[username=" + username + ", password=("
		+ StringUtils.isNotBlank(password) + "), permissions="
		+ permissions + "]");

	if (StringUtils.isBlank(username) || StringUtils.isBlank(password)
		|| permissions == null) {

	    throw new IllegalArgumentException(
		    "Illegal argument; username or password cannot be blank "
			    + "and permissions cannot be null.");
	}

	final User currentUser = this.readCurrent();
	final List<Permission> grantedPermissions = new ArrayList<Permission>();

	for (final Permission permission : Permission.values()) {

	    if (permissions.contains(permission)
		    && currentUser.hasPermission(permission)) {

		grantedPermissions.add(permission);
	    }
	}

	return this.repository.create(username, password, grantedPermissions);
    }

    @Override
    @Transactional(readOnly = true)
    @PreAuthorize("hasAuthority('READ_USER')")
    public User read(final int id) {

	LOGGER.debug("read[id=" + id + "]");

	return this.repository.read(id);
    }

    @Override
    @Transactional(readOnly = true)
    @PreAuthorize("hasAuthority('READ_USER')")
    public List<User> readAll() {

	LOGGER.debug("readAll");

	return this.repository.readAll();
    }

    @Override
    @Transactional(readOnly = true)
    @PreAuthorize("isAuthenticated()")
    public User readCurrent() {

	LOGGER.debug("readCurrent");

	final SecurityContext securityContext = SecurityContextHolder
		.getContext();
	final Authentication authentication = securityContext
		.getAuthentication();

	return (User) authentication.getPrincipal();
    }

    @Override
    @Transactional
    @PreAuthorize("hasAuthority('UPDATE_USER')")
    public void update(final int id, final User user) {

	LOGGER.debug("update[id=" + id + ", user=" + user + "]");

	if (user == null) {

	    throw new IllegalArgumentException(
		    "Illegal argument; user cannot be null.");
	}

	final List<Permission> permissions = user.getPermissions();
	final User currentUser = this.readCurrent();
	final List<Permission> grantedPermissions = new ArrayList<Permission>();

	for (final Permission permission : Permission.values()) {

	    if (permissions.contains(permission)
		    && currentUser.hasPermission(permission)) {

		grantedPermissions.add(permission);

	    } else if (!currentUser.hasPermission(permission)
		    && user.hasPermission(permission)) {

		grantedPermissions.add(permission);
	    }
	}

	user.setPermissions(grantedPermissions);

	this.repository.update(id, user);
    }

    @Override
    @Transactional
    @PreAuthorize("hasAuthority('DELETE_USER')")
    public void delete(final int id) {

	LOGGER.debug("delete[id=" + id + "]");

	this.repository.delete(id);
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(final String username)
	    throws UsernameNotFoundException {

	LOGGER.debug("loadUserByUsername[username=" + username + "]");

	if (StringUtils.isBlank(username)) {

	    throw new IllegalArgumentException(
		    "Illegal argument; username cannot be blank.");
	}

	final User user = this.repository.read(username);

	if (user == null) {

	    throw new UsernameNotFoundException(
		    "Username not found; username (" + username
			    + ") cannot be found");
	}

	return user;
    }

}
