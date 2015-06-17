package org.canary.server.component;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.security.core.Authentication;

@RunWith(MockitoJUnitRunner.class)
public final class AuthenticationSuccessHandlerTest {

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private Authentication authentication;

    @InjectMocks
    private AuthenticationSuccessHandler authenticationSuccessHandler;

    @Test(expected = IllegalArgumentException.class)
    public void onAuthenticationSuccessShouldThrowIllegalArgument()
	    throws ServletException, IOException {

	this.authenticationSuccessHandler.onAuthenticationSuccess(this.request,
		null, this.authentication);
    }

    @Test
    public void onAuthenticationSuccessShouldExecute() throws ServletException,
	    IOException {

	this.authenticationSuccessHandler.onAuthenticationSuccess(this.request,
		this.response, this.authentication);
    }

}
