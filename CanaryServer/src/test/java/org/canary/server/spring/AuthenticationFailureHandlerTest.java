package org.canary.server.spring;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.security.core.AuthenticationException;

@RunWith(MockitoJUnitRunner.class)
public final class AuthenticationFailureHandlerTest {

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private AuthenticationException authenticationException;

    @InjectMocks
    public AuthenticationFailureHandler authenticationFailureHandler;

    @Test
    public void onAuthenticationFailureShouldExecute() throws ServletException,
	    IOException {

	this.authenticationFailureHandler.onAuthenticationFailure(this.request,
		this.response, this.authenticationException);
    }

}
