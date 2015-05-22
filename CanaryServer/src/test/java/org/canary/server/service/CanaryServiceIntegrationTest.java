package org.canary.server.service;

import java.util.List;

import javax.annotation.Resource;

import org.canary.server.model.Canary;
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
public final class CanaryServiceIntegrationTest extends AuthenticationTestBase {

    @Resource(name = "canaryService")
    private CrudService<Canary> service;

    private static final String MESSAGE = "Message";
    private static final String NEW_MESSAGE = "New Message";

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

	final Canary canary;
	final List<Canary> canaries;

	Canary other;

	canary = this.service.create(MESSAGE);

	Assert.assertNotNull(canary);
	Assert.assertTrue(canary.getId() >= 1);
	Assert.assertEquals(MESSAGE, canary.getMessage());

	other = this.service.read(canary.getId());

	Assert.assertNotNull(other);
	Assert.assertEquals(canary.getId(), other.getId());
	Assert.assertEquals(canary.getMessage(), other.getMessage());

	canaries = this.service.readAll();

	Assert.assertNotNull(canaries);
	Assert.assertFalse(canaries.isEmpty());

	canary.setMessage(NEW_MESSAGE);
	this.service.update(canary.getId(), canary);
	other = this.service.read(canary.getId());

	Assert.assertNotNull(other);
	Assert.assertEquals(canary.getId(), other.getId());
	Assert.assertEquals(NEW_MESSAGE, other.getMessage());

	this.service.delete(canary.getId());
	other = this.service.read(canary.getId());

	Assert.assertNull(other);
    }

}
