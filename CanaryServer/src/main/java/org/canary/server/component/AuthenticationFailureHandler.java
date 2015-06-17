package org.canary.server.component;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

@Component("authenticationFailureHandler")
public final class AuthenticationFailureHandler extends
	SimpleUrlAuthenticationFailureHandler {

    private static final Logger LOGGER = Logger
	    .getLogger(AuthenticationFailureHandler.class);

    private AuthenticationFailureHandler() {
	super();
    }

    @Override
    public void onAuthenticationFailure(final HttpServletRequest request,
	    final HttpServletResponse response,
	    final AuthenticationException exception) throws IOException,
	    ServletException {

	LOGGER.debug("onAuthenticationFailure[request=" + request
		+ ", response=" + response + ", exception=" + exception + "]");

	if (response == null || exception == null) {
	    throw new IllegalArgumentException(
		    "Illegal argument; response and exception cannot be null.");
	}

	final String message = "Authentication Failed: "
		+ exception.getMessage();

	response.sendError(HttpServletResponse.SC_UNAUTHORIZED, message);
    }

}
