package org.canary.server.spring;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

@Component("authenticationSuccessHandler")
public final class AuthenticationSuccessHandler extends
	SimpleUrlAuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(final HttpServletRequest request,
	    final HttpServletResponse response,
	    final Authentication authentication) throws IOException,
	    ServletException {

	response.setStatus(HttpServletResponse.SC_OK);
    }

}
