package org.canary.server.service;

import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.canary.server.model.Message;
import org.canary.server.model.Permission;
import org.canary.server.model.User;
import org.canary.server.repository.MessageRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.test.util.ReflectionTestUtils;

@RunWith(MockitoJUnitRunner.class)
public final class MessageServiceTest extends AbstractServiceTest<Message> {

    @Mock
    private UserService userService;

    @Mock
    private MessageRepository messageRepository;

    private MessageService messageService;

    private static final String WHITESPACE_STRING = " ";
    private static final String VALUE = "Value";
    private static final String USERNAME = "Username";
    private static final String PASSWORD = "Password";

    private static final Logger LOGGER = Logger
	    .getLogger(MessageServiceTest.class);

    @Override
    public CrudService<Message> getService() {

	final int id = super.getValidId();
	final Message canary = this.getModel();
	final User user = this.getUser();

	this.messageService = Mockito.mock(MessageService.class);

	ReflectionTestUtils.setField(this.messageService, "userService",
		this.userService);
	ReflectionTestUtils.setField(this.messageService, "messageRepository",
		this.messageRepository);

	Mockito.doCallRealMethod() //
		.when(this.messageService) //
		.create(Matchers.anyString());

	Mockito.doCallRealMethod() //
		.when(this.messageService) //
		.read(Matchers.anyInt());

	Mockito.doCallRealMethod() //
		.when(this.messageService) //
		.readAll();

	Mockito.doCallRealMethod() //
		.when(this.messageService) //
		.update(Matchers.anyInt(), //
			Matchers.any(Message.class));

	Mockito.doCallRealMethod() //
		.when(this.messageService) //
		.delete(Matchers.anyInt());

	Mockito.when(this.userService //
		.readCurrent()) //
		.thenReturn(user);

	Mockito.when(this.messageRepository //
		.read(Matchers.eq(id))) //
		.thenReturn(canary);

	Mockito.when(this.messageRepository //
		.create(Matchers.any(User.class), //
			Matchers.anyString())) //
		.thenReturn(canary);

	return this.messageService;
    }

    @Test
    public void createShouldThrowIllegalArgument() {

	for (final String message : new String[] { null, StringUtils.EMPTY,
		WHITESPACE_STRING }) {

	    try {

		this.messageService.create(message);
		Assert.fail();

	    } catch (final IllegalArgumentException e) {
		LOGGER.debug("Expected illegal argument caught while testing.");
	    }
	}
    }

    @Test
    public void createShouldNotReturnNull() {

	final Message canary = this.messageService.create(VALUE);

	Assert.assertNotNull(canary);
    }

    @Override
    public Message getModel() {

	final int id = super.getValidId();
	final Message canary = new Message();

	canary.setId(id);
	canary.setValue(VALUE);

	return canary;
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
