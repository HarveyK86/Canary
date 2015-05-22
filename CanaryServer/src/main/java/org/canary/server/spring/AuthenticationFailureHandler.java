package org.canary.server.spring;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

@Component("authenticationFailureHandler")
public final class AuthenticationFailureHandler extends
	SimpleUrlAuthenticationFailureHandler {

    @Override
    public void onAuthenticationFailure(final HttpServletRequest request,
	    final HttpServletResponse response,
	    final AuthenticationException exception) throws IOException,
	    ServletException {

	response.sendError(HttpServletResponse.SC_UNAUTHORIZED,
		"Authentication Failed: " + exception.getMessage());
    }

}
