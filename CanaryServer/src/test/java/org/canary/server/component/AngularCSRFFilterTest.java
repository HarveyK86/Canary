package org.canary.server.component;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.security.web.csrf.CsrfToken;

@RunWith(MockitoJUnitRunner.class)
public final class AngularCSRFFilterTest {

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private FilterChain filterChain;

    @Mock
    private CsrfToken csrfToken;

    @Mock
    private Cookie cookie;

    private AngularCSRFFilter csrfFilter;

    private static final String CSRF_TOKEN_VALUE = "CSRF Token Value";
    private static final String CSRF_TOKEN_NAME = "CSRF Token Name";

    @Before
    public void before() throws IOException, ServletException {

	this.csrfFilter = Mockito.mock(AngularCSRFFilter.class);

	Mockito.doCallRealMethod() //
		.when(this.csrfFilter) //
		.doFilterInternal(Matchers.any(HttpServletRequest.class), //
			Matchers.any(HttpServletResponse.class), //
			Matchers.any(FilterChain.class));

	Mockito.when(this.request //
		.getAttribute(Matchers.anyString())) //
		.thenReturn(this.csrfToken);

	Mockito.when(this.csrfFilter //
		.getCookie(Matchers.any(HttpServletRequest.class), //
			Matchers.anyString())) //
		.thenReturn(this.cookie);

	Mockito.when(this.csrfToken //
		.getToken()) //
		.thenReturn(CSRF_TOKEN_VALUE);

	Mockito.when(this.cookie //
		.getValue()) //
		.thenReturn(CSRF_TOKEN_VALUE);
    }

    @Test
    public void doFilterInternalShouldExecute() throws IOException,
	    ServletException {

	this.csrfFilter.doFilterInternal(this.request, this.response,
		this.filterChain);
    }

    @Test
    public void doFilterInternalShouldHandleNullCsrfToken() throws IOException,
	    ServletException {

	Mockito.when(this.request //
		.getAttribute(Matchers.anyString())) //
		.thenReturn(null);

	this.csrfFilter.doFilterInternal(this.request, this.response,
		this.filterChain);
    }

    @Test
    public void doFilterInternalShouldHandleNullCookie() throws IOException,
	    ServletException {

	Mockito.when(this.csrfFilter //
		.getCookie(Matchers.any(HttpServletRequest.class), //
			Matchers.anyString())) //
		.thenReturn(null);

	this.csrfFilter.doFilterInternal(this.request, this.response,
		this.filterChain);
    }

    @Test
    public void doFilterInternalShouldHandleNullCSRFTokenValue()
	    throws IOException, ServletException {

	Mockito.when(this.csrfToken //
		.getToken()) //
		.thenReturn(null);

	this.csrfFilter.doFilterInternal(this.request, this.response,
		this.filterChain);
    }

    @Test
    public void doFilterInternalShoouldHandleNullCurrentCSRFTokenValue()
	    throws IOException, ServletException {

	Mockito.when(this.cookie //
		.getValue()) //
		.thenReturn(null);

	this.csrfFilter.doFilterInternal(this.request, this.response,
		this.filterChain);
    }

    @Test
    public void getCookieShouldExecute() {

	Mockito.doCallRealMethod() //
		.when(this.csrfFilter) //
		.getCookie(Matchers.any(HttpServletRequest.class), //
			Matchers.anyString());

	this.csrfFilter.getCookie(this.request, CSRF_TOKEN_NAME);
    }

}
