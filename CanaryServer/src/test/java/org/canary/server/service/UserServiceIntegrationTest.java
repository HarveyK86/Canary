package org.canary.server.service;

import java.util.Arrays;
import java.util.List;

import javax.annotation.Resource;

import org.canary.server.model.Permission;
import org.canary.server.model.User;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext.xml")
public final class UserServiceIntegrationTest extends AuthenticationTestBase {

    @Resource(name = "userService")
    private UserServiceInterface service;

    private static final String USERNAME = "Username";
    private static final String NEW_USERNAME = "New Username";
    private static final String PASSWORD = "Password";

    @Before
    public void before() {
	super.login(Permission.values());
    }

    @After
    public void after() {
	super.logout();
    }

    @Test
    public void createReadUpdateAndDeleteShouldExecuteCorrectly() {

	final List<Permission> permissions = this.getPermissions();
	final User user;
	final List<User> users;

	User other;

	user = this.service.create(USERNAME, PASSWORD, permissions);

	Assert.assertNotNull(user);
	Assert.assertTrue(user.getId() >= 1);
	Assert.assertEquals(USERNAME, user.getUsername());

	other = this.service.read(user.getId());

	Assert.assertNotNull(other);
	Assert.assertEquals(user.getId(), other.getId());
	Assert.assertEquals(user.getUsername(), other.getUsername());

	users = this.service.readAll();

	Assert.assertNotNull(users);
	Assert.assertFalse(users.isEmpty());

	user.setUsername(NEW_USERNAME);
	this.service.update(user.getId(), user);
	other = this.service.read(user.getId());

	Assert.assertNotNull(other);
	Assert.assertEquals(user.getId(), other.getId());
	Assert.assertEquals(NEW_USERNAME, other.getUsername());

	this.service.delete(user.getId());
	other = this.service.read(user.getId());

	Assert.assertNull(other);
    }

    public List<Permission> getPermissions() {

	final Permission[] permissions = Permission.values();

	return Arrays.asList(permissions);
    }

}
