package org.canary.server.spring;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.WebUtils;

@Component("angularCSRFFilter")
public class AngularCSRFFilter extends OncePerRequestFilter {

    private static final String CSRF_TOKEN_NAME = "XSRF-TOKEN";

    private AngularCSRFFilter() {
	super();
    }

    @Override
    protected void doFilterInternal(final HttpServletRequest request,
	    final HttpServletResponse response, final FilterChain filterChain)
	    throws ServletException, IOException {

	final String csrfTokenName = CsrfToken.class.getName();
	final CsrfToken csrfToken = (CsrfToken) request
		.getAttribute(csrfTokenName);
	final String csrfTokenValue;

	Cookie cookie;
	String currentCSRFTokenValue = null;

	if (csrfToken != null) {

	    csrfTokenValue = csrfToken.getToken();
	    cookie = this.getCookie(request, CSRF_TOKEN_NAME);

	    if (cookie != null) {
		currentCSRFTokenValue = cookie.getValue();
	    }

	    if (cookie == null || csrfTokenValue != null
		    && !csrfTokenValue.equals(currentCSRFTokenValue)) {

		cookie = new Cookie(CSRF_TOKEN_NAME, csrfTokenValue);
		cookie.setPath("/");
		response.addCookie(cookie);
	    }

	    filterChain.doFilter(request, response);
	}
    }

    protected Cookie getCookie(final HttpServletRequest request,
	    final String csrfTokenName) {

	return WebUtils.getCookie(request, csrfTokenName);
    }

}
