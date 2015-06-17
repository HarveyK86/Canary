package org.canary.server.service;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.canary.server.model.Message;
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
    private MessageRepository repository;

    private MessageService service;

    private static final String WHITESPACE_STRING = " ";
    private static final String MESSAGE = "Message";

    private static final Logger LOGGER = Logger
	    .getLogger(MessageServiceTest.class);

    @Override
    public CrudService<Message> getService() {

	final int id = super.getValidId();
	final Message canary = this.getModel();

	this.service = Mockito.mock(MessageService.class);

	ReflectionTestUtils.setField(this.service, "repository",
		this.repository);

	Mockito.doCallRealMethod() //
		.when(this.service) //
		.create(Matchers.anyString());

	Mockito.doCallRealMethod() //
		.when(this.service) //
		.read(Matchers.anyInt());

	Mockito.doCallRealMethod() //
		.when(this.service) //
		.readAll();

	Mockito.doCallRealMethod() //
		.when(this.service) //
		.update(Matchers.anyInt(), //
			Matchers.any(Message.class));

	Mockito.doCallRealMethod() //
		.when(this.service) //
		.delete(Matchers.anyInt());

	Mockito.when(this.repository //
		.read(Matchers.eq(id))) //
		.thenReturn(canary);

	Mockito.when(this.repository //
		.create(Matchers.anyString())) //
		.thenReturn(canary);

	return this.service;
    }

    @Test
    public void createShouldThrowIllegalArgument() {

	for (final String message : new String[] { null, StringUtils.EMPTY,
		WHITESPACE_STRING }) {

	    try {

		this.service.create(message);
		Assert.fail();

	    } catch (final IllegalArgumentException e) {
		LOGGER.debug("Expected illegal argument caught while testing.");
	    }
	}
    }

    @Test
    public void createShouldNotReturnNull() {

	final Message canary = this.service.create(MESSAGE);

	Assert.assertNotNull(canary);
    }

    @Override
    public Message getModel() {

	final int id = super.getValidId();
	final Message canary = new Message();

	canary.setId(id);
	canary.setValue(MESSAGE);

	return canary;
    }

}
