package org.canary.server.service;

import java.util.Arrays;
import java.util.List;

import org.canary.server.model.Permission;
import org.canary.server.model.User;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

public class AuthenticationTestBase {

    private static final int ID = 1;
    private static final String USERNAME = "Username";
    private static final String PASSWORD = "Password";

    public final void login(final Permission... permissions) {

	final User user = new User();
	final List<Permission> permissionsList = Arrays.asList(permissions);
	final Authentication authentication;
	final SecurityContext securityContext = SecurityContextHolder
		.getContext();

	user.setId(ID);
	user.setUsername(USERNAME);
	user.setPassword(PASSWORD);
	user.setPermissions(permissionsList);
	authentication = new UsernamePasswordAuthenticationToken(user,
		PASSWORD, permissionsList);
	securityContext.setAuthentication(authentication);
    }

    public final void logout() {

	final SecurityContext securityContext = SecurityContextHolder
		.getContext();

	securityContext.setAuthentication(null);
    }

}
