package org.canary.server.spring;

import java.util.ArrayList;
import java.util.List;

import org.canary.server.model.Role;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

public class AuthenticationTestBase {

    private static final String USERNAME = "Username";
    private static final String PASSWORD = "Password";

    public final void login(final Role role, final Role... otherRoles) {

	final List<Role> roles = new ArrayList<Role>();
	final Authentication authentication;
	final SecurityContext securityContext = SecurityContextHolder
		.getContext();

	roles.add(role);
	roles.addAll(roles);

	authentication = new UsernamePasswordAuthenticationToken(USERNAME,
		PASSWORD, roles);
	securityContext.setAuthentication(authentication);
    }

    public final void logout() {

	final SecurityContext securityContext = SecurityContextHolder
		.getContext();

	securityContext.setAuthentication(null);
    }

}
