package org.canary.server.service;

import java.util.List;

import org.canary.server.model.Permission;
import org.canary.server.model.User;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserServiceInterface extends CrudService<User>,
	UserDetailsService {

    User create(final String username, final String password,
	    final List<Permission> permissions);

    User readCurrent();

}