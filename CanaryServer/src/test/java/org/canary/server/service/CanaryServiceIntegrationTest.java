package org.canary.server.service;

import org.canary.server.model.Canary;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext.xml")
public final class CanaryServiceIntegrationTest {

    @Autowired
    private CrudService<Canary> service;

    private static final String MESSAGE = "Message";
    private static final String NEW_MESSAGE = "New Message";

    @Test
    public void createReadUpdateAndDeleteShouldExecuteCorrectly() {

	final Canary model;

	Canary other;

	model = this.service.create(MESSAGE);

	Assert.assertNotNull(model);
	Assert.assertTrue(model.getId() >= 1);
	Assert.assertEquals(MESSAGE, model.getMessage());

	other = this.service.read(model.getId());

	Assert.assertNotNull(other);
	Assert.assertEquals(model.getId(), other.getId());
	Assert.assertEquals(model.getMessage(), other.getMessage());

	model.setMessage(NEW_MESSAGE);
	this.service.update(model.getId(), model);
	other = this.service.read(model.getId());

	Assert.assertNotNull(other);
	Assert.assertEquals(model.getId(), other.getId());
	Assert.assertEquals(NEW_MESSAGE, other.getMessage());

	this.service.delete(model.getId());
	other = this.service.read(model.getId());

	Assert.assertNull(other);
    }

}
