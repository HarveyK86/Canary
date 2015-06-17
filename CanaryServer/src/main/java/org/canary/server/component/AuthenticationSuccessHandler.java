package org.canary.server.component;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

@Component("authenticationSuccessHandler")
public final class AuthenticationSuccessHandler extends
	SimpleUrlAuthenticationSuccessHandler {

    private static final Logger LOGGER = Logger
	    .getLogger(AuthenticationSuccessHandler.class);

    private AuthenticationSuccessHandler() {
	super();
    }

    @Override
    public void onAuthenticationSuccess(final HttpServletRequest request,
	    final HttpServletResponse response,
	    final Authentication authentication) throws IOException,
	    ServletException {

	LOGGER.debug("onAuthenticationSuccess[request=" + request
		+ ", response=" + response + ", authentication="
		+ authentication + "]");

	if (response == null) {

	    throw new IllegalArgumentException(
		    "Illegal argument; response cannot be null");
	}

	response.setStatus(HttpServletResponse.SC_OK);
    }

}
