package org.canary.server.service;

import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.canary.server.model.Role;
import org.canary.server.model.User;
import org.canary.server.repository.UserRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.test.util.ReflectionTestUtils;

@RunWith(MockitoJUnitRunner.class)
public final class UserServiceTest extends AbstractServiceTest<User> {

    @Mock
    private UserRepository repository;

    private UserService service;

    private static final String WHITESPACE_STRING = " ";
    private static final String USERNAME = "Username";
    private static final String INVALID_USERNAME = "Invalid Username";
    private static final String PASSWORD = "Password";

    private static final Logger LOGGER = Logger
	    .getLogger(UserServiceTest.class);

    @Override
    public CrudService<User> getService() {

	final int id = super.getValidId();
	final User user = this.getModel();

	this.service = Mockito.mock(UserService.class);

	ReflectionTestUtils.setField(this.service, "repository",
		this.repository);

	this.service.postConstruct();

	Mockito.doCallRealMethod() //
		.when(this.service) //
		.create(Matchers.anyString(), //
			Matchers.anyString());

	Mockito.doCallRealMethod() //
		.when(this.service) //
		.read(Matchers.anyInt());

	Mockito.doCallRealMethod() //
		.when(this.service).readAll();

	Mockito.doCallRealMethod() //
		.when(this.service) //
		.update(Matchers.anyInt(), //
			Matchers.any(User.class));

	Mockito.doCallRealMethod() //
		.when(this.service) //
		.delete(Matchers.anyInt());

	Mockito.doCallRealMethod() //
		.when(this.service) //
		.loadUserByUsername(Matchers.anyString());

	Mockito.when(this.repository //
		.create(Matchers.anyString(), //
			Matchers.anyString())) //
		.thenReturn(user);

	Mockito.when(this.repository //
		.read(Matchers.eq(id))) //
		.thenReturn(user);

	Mockito.when(this.repository //
		.read(Matchers.eq(USERNAME))) //
		.thenReturn(user);

	return this.service;
    }

    @Test
    public void createUsernameShouldThrowIllegalArgument() {

	for (final String username : new String[] { null, StringUtils.EMPTY,
		WHITESPACE_STRING }) {

	    try {

		this.service.create(username, PASSWORD);
		Assert.fail();

	    } catch (final IllegalArgumentException e) {
		LOGGER.debug("Expected illegal argument caught while testing.");
	    }
	}
    }

    @Test
    public void createPasswordShouldThrowIllegalArgument() {

	for (final String password : new String[] { null, StringUtils.EMPTY,
		WHITESPACE_STRING }) {

	    try {

		this.service.create(USERNAME, password);
		Assert.fail();

	    } catch (final IllegalArgumentException e) {
		LOGGER.debug("Expected illegal argument caught while testing.");
	    }
	}
    }

    @Test
    public void createShouldNotReturnNull() {

	final User user = this.service.create(USERNAME, PASSWORD);

	Assert.assertNotNull(user);
    }

    @Test
    public void loadUserByUsernameShouldThrowIllegalArgument() {

	for (final String username : new String[] { null, StringUtils.EMPTY,
		WHITESPACE_STRING }) {

	    try {

		this.service.loadUserByUsername(username);
		Assert.fail();

	    } catch (final IllegalArgumentException e) {
		LOGGER.debug("Expected illegal argument caught while testing.");
	    }
	}
    }

    @Test(expected = UsernameNotFoundException.class)
    public void loadUserByUsernameShouldThrowUsernameNotFound() {
	this.service.loadUserByUsername(INVALID_USERNAME);
    }

    @Test
    public void loadUserByUsernameShouldReturnValidUserDetails() {

	final UserDetails userDetails = this.service
		.loadUserByUsername(USERNAME);

	Assert.assertNotNull(userDetails);
	Assert.assertEquals(USERNAME, userDetails.getUsername());
	Assert.assertEquals(PASSWORD, userDetails.getPassword());
	Assert.assertFalse(userDetails.getAuthorities().isEmpty());
	Assert.assertTrue(userDetails.isAccountNonExpired());
	Assert.assertTrue(userDetails.isAccountNonLocked());
	Assert.assertTrue(userDetails.isCredentialsNonExpired());
	Assert.assertTrue(userDetails.isEnabled());
    }

    @Override
    public User getModel() {

	final User user = new User();
	final int id = super.getValidId();
	final Role[] rolesArray = Role.values();
	final List<Role> roles = Arrays.asList(rolesArray);

	user.setId(id);
	user.setUsername(USERNAME);
	user.setPassword(PASSWORD);
	user.setRoles(roles);

	return user;
    }

}
