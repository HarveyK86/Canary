package org.canary.server.component;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.security.web.csrf.CsrfTokenRepository;
import org.springframework.security.web.csrf.HttpSessionCsrfTokenRepository;
import org.springframework.stereotype.Component;

@Component("angularCSRFTokenRepository")
public class AngularCSRFTokenRepository implements CsrfTokenRepository {

    private CsrfTokenRepository parent;

    private static final String CSRF_TOKEN_NAME = "X-XSRF-TOKEN";

    private static final Logger LOGGER = Logger
	    .getLogger(AngularCSRFTokenRepository.class);

    private AngularCSRFTokenRepository() {
	super();
    }

    @PostConstruct
    public final void postConstruct() {

	LOGGER.debug("postContstruct");

	this.parent = this.getParent();
    }

    @Override
    public CsrfToken generateToken(final HttpServletRequest request) {

	LOGGER.debug("generateToken[request=" + request + "]");

	return this.parent.generateToken(request);
    }

    @Override
    public void saveToken(final CsrfToken token,
	    final HttpServletRequest request, //
	    final HttpServletResponse response) {

	LOGGER.debug("saveToken[token=" + token + ", request=" + request
		+ ", response=" + response + "]");

	this.parent.saveToken(token, request, response);
    }

    @Override
    public CsrfToken loadToken(final HttpServletRequest request) {

	LOGGER.debug("loadToken[request=" + request + "]");

	return this.parent.loadToken(request);
    }

    protected CsrfTokenRepository getParent() {

	LOGGER.debug("getParent");

	final HttpSessionCsrfTokenRepository parent = //
	new HttpSessionCsrfTokenRepository();

	parent.setHeaderName(CSRF_TOKEN_NAME);

	return parent;
    }

}
