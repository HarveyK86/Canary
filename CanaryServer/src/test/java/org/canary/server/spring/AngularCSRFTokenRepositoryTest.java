package org.canary.server.spring;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.security.web.csrf.CsrfTokenRepository;

@RunWith(MockitoJUnitRunner.class)
public final class AngularCSRFTokenRepositoryTest {

    @Mock
    private HttpServletRequest request;

    @Mock
    private CsrfToken token;

    @Mock
    private HttpServletResponse response;

    @Mock
    private CsrfTokenRepository parent;

    private AngularCSRFTokenRepository repository;

    @Before
    public void before() {

	this.repository = Mockito.mock(AngularCSRFTokenRepository.class);

	Mockito.when(this.repository //
		.getParent()) //
		.thenReturn(this.parent);

	this.repository.postConstruct();

	Mockito.doCallRealMethod() //
		.when(this.repository) //
		.generateToken(Matchers.any(HttpServletRequest.class));

	Mockito.doCallRealMethod() //
		.when(this.repository) //
		.saveToken(Matchers.any(CsrfToken.class), //
			Matchers.any(HttpServletRequest.class), //
			Matchers.any(HttpServletResponse.class));

	Mockito.doCallRealMethod() //
		.when(this.repository) //
		.loadToken(Matchers.any(HttpServletRequest.class));

	Mockito.when(this.parent //
		.generateToken(Matchers.any(HttpServletRequest.class))) //
		.thenReturn(this.token);

	Mockito.when(this.parent //
		.loadToken(Matchers.any(HttpServletRequest.class))) //
		.thenReturn(this.token);
    }

    @Test
    public void generateTokenShouldNotReturnNull() {

	final CsrfToken token = this.repository.generateToken(this.request);

	Assert.assertNotNull(token);
    }

    @Test
    public void saveTokenShouldExecute() {
	this.repository.saveToken(this.token, this.request, this.response);
    }

    @Test
    public void loadTokenShouldNotReturnNull() {

	final CsrfToken token = this.repository.loadToken(this.request);

	Assert.assertNotNull(token);
    }

}
