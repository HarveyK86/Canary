package org.canary.server.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.canary.server.model.Permission;
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
    @SuppressWarnings("unchecked")
    public CrudService<User> getService() {

	final int id = super.getValidId();
	final User user = this.getModel();

	this.service = Mockito.mock(UserService.class);

	ReflectionTestUtils.setField(this.service, "repository",
		this.repository);

	Mockito.doCallRealMethod() //
		.when(this.service) //
		.create(Matchers.anyString(), //
			Matchers.anyString(), //
			Matchers.anyList());

	Mockito.doCallRealMethod() //
		.when(this.service) //
		.read(Matchers.anyInt());

	Mockito.doCallRealMethod() //
		.when(this.service) //
		.readCurrent();

	Mockito.doCallRealMethod() //
		.when(this.service) //
		.readAll();

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
			Matchers.anyString(), //
			Matchers.anyList())) //
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
    public void createInvalidUsernameShouldThrowIllegalArgument() {

	for (final String username : new String[] { null, StringUtils.EMPTY,
		WHITESPACE_STRING }) {

	    final List<Permission> permissions = this.getPermissions();

	    try {

		this.service.create(username, PASSWORD, permissions);
		Assert.fail();

	    } catch (final IllegalArgumentException e) {
		LOGGER.debug("Expected illegal argument caught while testing.");
	    }
	}
    }

    @Test
    public void createInvalidPasswordShouldThrowIllegalArgument() {

	for (final String password : new String[] { null, StringUtils.EMPTY,
		WHITESPACE_STRING }) {

	    final List<Permission> permissions = this.getPermissions();

	    try {

		this.service.create(USERNAME, password, permissions);
		Assert.fail();

	    } catch (final IllegalArgumentException e) {
		LOGGER.debug("Expected illegal argument caught while testing.");
	    }
	}
    }

    @Test(expected = IllegalArgumentException.class)
    public void createNullPermissionsShouldThrowIllegalArgument() {
	this.service.create(USERNAME, PASSWORD, null);
    }

    @Test
    public void createShouldNotReturnNull() {

	final List<Permission> permissions = this.getPermissions();
	final User user;

	super.login(Permission.values());
	user = this.service.create(USERNAME, PASSWORD, permissions);
	super.logout();

	Assert.assertNotNull(user);
    }

    @Test
    public void createMixedPermissionsShouldNotReturnNull() {

	final List<Permission> permissions = new ArrayList<Permission>();
	final User user;

	permissions.add(Permission.CREATE_MESSAGE);
	permissions.add(Permission.CREATE_USER);

	super.login(Permission.CREATE_USER, Permission.DELETE_MESSAGE);
	user = this.service.create(USERNAME, PASSWORD, permissions);
	super.logout();

	Assert.assertNotNull(user);
    }

    @Test
    public void readCurrentNotReturnNull() {

	final User user;

	super.login(Permission.values());
	user = this.service.readCurrent();
	super.logout();

	Assert.assertNotNull(user);
    }

    @Test
    public void updateMixedPermissionsShouldExecute() {

	final List<Permission> permissions = new ArrayList<Permission>();
	final User user = this.getModel();
	final int id = super.getValidId();

	permissions.add(Permission.CREATE_MESSAGE);
	permissions.add(Permission.CREATE_USER);
	user.setPermissions(permissions);

	super.login(Permission.CREATE_USER, Permission.DELETE_MESSAGE);
	this.service.update(id, user);
	super.logout();
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
	final List<Permission> permissions = this.getPermissions();

	user.setId(id);
	user.setUsername(USERNAME);
	user.setPassword(PASSWORD);
	user.setPermissions(permissions);

	return user;
    }

    public List<Permission> getPermissions() {

	final Permission[] permissions = Permission.values();

	return Arrays.asList(permissions);
    }

}
