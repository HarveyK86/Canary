package org.canary.server.service;

import java.util.List;

import javax.annotation.Resource;

import org.canary.server.model.Message;
import org.canary.server.model.Role;
import org.canary.server.spring.AuthenticationTestBase;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext.xml")
public final class MessageServiceIntegrationTest extends AuthenticationTestBase {

    @Resource(name = "messageService")
    private CrudService<Message> service;

    private static final String VALUE = "Value";
    private static final String NEW_VALUE = "New Value";

    @Before
    public void before() {
	super.login(Role.USER);
    }

    @After
    public void after() {
	super.logout();
    }

    @Test
    public void createReadUpdateAndDeleteShouldExecuteCorrectly() {

	final Message message;
	final List<Message> messages;

	Message other;

	message = this.service.create(VALUE);

	Assert.assertNotNull(message);
	Assert.assertTrue(message.getId() >= 1);
	Assert.assertEquals(VALUE, message.getValue());

	other = this.service.read(message.getId());

	Assert.assertNotNull(other);
	Assert.assertEquals(message.getId(), other.getId());
	Assert.assertEquals(message.getValue(), other.getValue());

	messages = this.service.readAll();

	Assert.assertNotNull(messages);
	Assert.assertFalse(messages.isEmpty());

	message.setValue(NEW_VALUE);
	this.service.update(message.getId(), message);
	other = this.service.read(message.getId());

	Assert.assertNotNull(other);
	Assert.assertEquals(message.getId(), other.getId());
	Assert.assertEquals(NEW_VALUE, other.getValue());

	this.service.delete(message.getId());
	other = this.service.read(message.getId());

	Assert.assertNull(other);
    }

}
