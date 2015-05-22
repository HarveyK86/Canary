package org.canary.server.service;

import java.util.Collection;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.canary.server.model.User;
import org.canary.server.repository.CrudRepository;
import org.canary.server.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService extends AbstractService<User> implements
	UserDetailsService {

    @Autowired
    private UserRepository repository;

    private static final Logger LOGGER = Logger.getLogger(UserService.class);

    private UserService() {
	super();
    }

    @Override
    @Transactional
    @PreAuthorize("hasAuthority('USER')")
    public User create(final Object... args) {

	final String username = (String) args[0];
	final String password = (String) args[1];

	LOGGER.debug("create[username=" + username + ", password=("
		+ StringUtils.isNotBlank(password) + ")]");

	if (StringUtils.isBlank(username) || StringUtils.isBlank(password)) {

	    throw new IllegalArgumentException(
		    "Illegal argument; username or password cannot be blank.");
	}

	return this.repository.create(username, password);
    }

    @Override
    @Transactional
    @SuppressWarnings("serial")
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

	return new UserDetails() {

	    @Override
	    public String getUsername() {
		return user.getUsername();
	    }

	    @Override
	    public final String getPassword() {
		return user.getPassword();
	    }

	    @Override
	    public final Collection<? extends GrantedAuthority> getAuthorities() {
		return user.getRoles();
	    }

	    @Override
	    public boolean isAccountNonExpired() {
		return true;
	    }

	    @Override
	    public boolean isAccountNonLocked() {
		return true;
	    }

	    @Override
	    public boolean isCredentialsNonExpired() {
		return true;
	    }

	    @Override
	    public boolean isEnabled() {
		return true;
	    }

	};
    }

    @Override
    public final CrudRepository<User> getRepository() {

	LOGGER.debug("getRepository");

	return this.repository;
    }

}
