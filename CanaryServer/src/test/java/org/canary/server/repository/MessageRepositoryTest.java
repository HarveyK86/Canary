package org.canary.server.repository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.canary.server.model.Message;
import org.canary.server.model.Permission;
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
public final class MessageRepositoryTest extends
	AbstractRepositoryTest<Message> {

    @Mock
    private SessionFactory sessionFactory;

    @Mock
    private Session session;

    @Mock
    private Criteria criteria;

    private MessageRepository repository;

    private static final String WHITESPACE_STRING = " ";
    private static final String VALUE = "Value";
    private static final String USERNAME = "Username";
    private static final String PASSWORD = "Password";

    private static final Logger LOGGER = Logger
	    .getLogger(MessageRepositoryTest.class);

    @Override
    public CrudRepository<Message> getRepository() {

	final Message message = this.getModel();
	final int id = super.getValidId();
	final List<Message> messages = new ArrayList<Message>();

	this.repository = Mockito.mock(MessageRepository.class);

	ReflectionTestUtils.setField(this.repository, "sessionFactory",
		this.sessionFactory);

	this.repository.postConstruct();

	Mockito.doCallRealMethod() //
		.when(this.repository) //
		.create(Matchers.any(User.class), //
			Matchers.anyString());

	Mockito.doCallRealMethod() //
		.when(this.repository) //
		.read(Matchers.anyInt());

	Mockito.doCallRealMethod() //
		.when(this.repository) //
		.readAll();

	Mockito.doCallRealMethod() //
		.when(this.repository) //
		.update(Matchers.anyInt(), //
			Matchers.any(Message.class));

	Mockito.doCallRealMethod() //
		.when(this.repository) //
		.delete(Matchers.anyInt());

	Mockito.doCallRealMethod() //
		.when((AbstractRepository<Message>) this.repository) //
		.getSession();

	Mockito.when(this.sessionFactory //
		.getCurrentSession()) //
		.thenReturn(this.session);

	Mockito.when(this.session //
		.get(Matchers.any(Class.class), //
			Matchers.eq(id))) //
		.thenReturn(message);

	Mockito.when(this.session //
		.createCriteria(Matchers.any(Class.class))) //
		.thenReturn(this.criteria);

	Mockito.when(this.session //
		.save(Matchers.any(Message.class))) //
		.thenReturn(id);

	Mockito.when(this.criteria //
		.list()) //
		.thenReturn(messages);

	return this.repository;
    }

    @Test(expected = IllegalArgumentException.class)
    public void createNullAuthorShouldThrowIllegalArgument() {
	this.repository.create(null, VALUE);
    }

    @Test
    public void createInvalidValueShouldThrowIllegalArgument() {

	for (final String value : new String[] { null, StringUtils.EMPTY,
		WHITESPACE_STRING }) {

	    final User user = this.getUser();

	    try {

		this.repository.create(user, value);
		Assert.fail();

	    } catch (final IllegalArgumentException e) {
		LOGGER.debug("Expected illegal argument caught while testing.");
	    }
	}
    }

    @Test
    public void createShouldNotReturnNull() {

	final User user = this.getUser();
	final Message message = this.repository.create(user, VALUE);

	Assert.assertNotNull(message);
    }

    @Override
    public Message getModel() {

	final int id = super.getValidId();
	final Message message = new Message();

	message.setId(id);
	message.setValue(VALUE);

	return message;
    }

    private User getUser() {

	final int id = super.getValidId();
	final User user = new User();
	final Permission[] permissionsArray = Permission.values();
	final List<Permission> permissions = Arrays.asList(permissionsArray);

	user.setId(id);
	user.setUsername(USERNAME);
	user.setPassword(PASSWORD);
	user.setPermissions(permissions);

	return user;
    }

}
