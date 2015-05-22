package org.canary.server.repository;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.canary.server.model.User;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.test.util.ReflectionTestUtils;

@RunWith(MockitoJUnitRunner.class)
public final class UserRepositoryTest extends AbstractRepositoryTest<User> {

    @Mock
    private SessionFactory sessionFactory;

    @Mock
    private Session session;

    @Mock
    private Criteria criteria;

    private UserRepository repository;

    private static final String WHITESPACE_STRING = " ";
    private static final String USERNAME = "Username";
    private static final String PASSWORD = "Password";

    private static final Logger LOGGER = Logger.getLogger(UserRepository.class);

    @Override
    public CrudRepository<User> getRepository() {

	final User user = this.getModel();
	final int id = super.getValidId();
	final List<User> users = new ArrayList<User>();

	this.repository = Mockito.mock(UserRepository.class);

	ReflectionTestUtils.setField(this.repository, "sessionFactory",
		this.sessionFactory);

	this.repository.postConstruct();

	Mockito.doCallRealMethod() //
		.when(this.repository) //
		.create(Matchers.anyString(), //
			Matchers.anyString());

	Mockito.doCallRealMethod() //
		.when(this.repository) //
		.read(Matchers.anyInt());

	Mockito.doCallRealMethod() //
		.when(this.repository) //
		.read(Matchers.anyString());

	Mockito.doCallRealMethod() //
		.when(this.repository) //
		.readAll();

	Mockito.doCallRealMethod() //
		.when(this.repository) //
		.update(Matchers.anyInt(), //
			Matchers.any(User.class));

	Mockito.doCallRealMethod() //
		.when(this.repository) //
		.delete(Matchers.anyInt());

	Mockito.doCallRealMethod() //
		.when((AbstractRepository<User>) this.repository) //
		.getSession();

	Mockito.when(this.sessionFactory //
		.getCurrentSession()) //
		.thenReturn(this.session);

	Mockito.when(this.session //
		.get(Matchers.any(Class.class), //
			Matchers.eq(id))) //
		.thenReturn(user);

	Mockito.when(this.session //
		.createCriteria(Matchers.any(Class.class))) //
		.thenReturn(this.criteria);

	Mockito.when(this.session //
		.save(Matchers.any(User.class))) //
		.thenReturn(id);

	Mockito.when(this.criteria //
		.uniqueResult()) //
		.thenReturn(user);

	Mockito.when(this.criteria //
		.list()) //
		.thenReturn(users);

	return this.repository;
    }

    @Test
    public void createUsernameShouldThrowIllegalArgument() {

	for (final String username : new String[] { null, StringUtils.EMPTY,
		WHITESPACE_STRING }) {

	    try {

		this.repository.create(username, PASSWORD);
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

		this.repository.create(USERNAME, password);
		Assert.fail();

	    } catch (final IllegalArgumentException e) {
		LOGGER.debug("Expected illegal argument caught while testing.");
	    }
	}
    }

    @Test
    public void createShouldNotReturnNull() {

	final User user = this.repository.create(USERNAME, PASSWORD);

	Assert.assertNotNull(user);
    }

    @Test
    public void readUsernameShouldThrowIllegalArgument() {

	for (final String username : new String[] { null, StringUtils.EMPTY,
		WHITESPACE_STRING }) {

	    try {

		this.repository.read(username);
		Assert.fail();

	    } catch (final IllegalArgumentException e) {
		LOGGER.debug("Expected illegal argument caught while testing.");
	    }
	}
    }

    @Test
    public void readUsernameShouldNotReturnNull() {

	final User user = this.repository.read(USERNAME);

	Assert.assertNotNull(user);
    }

    @Override
    public User getModel() {

	final int id = super.getValidId();
	final User user = new User();

	user.setId(id);
	user.setUsername(USERNAME);
	user.setPassword(PASSWORD);

	return user;
    }

}
