package org.canary.server.service;

import org.canary.server.model.Canary;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/applicationContext.xml")
public final class CanaryServiceIntegrationTest
{
	@Autowired
	private CrudService<Canary>	service;

	private static final String	TEST_MESSAGE		= "Test Message";
	private static final String	TEST_MESSAGE_UPDATE	= "Test Message Update";

	@Test
	public void createReadUpdateAndDeleteShouldExecuteCorrectly()
	{
		final Canary create;
		final Canary read;
		final Canary update;
		final Canary delete;

		create = this.service.create(TEST_MESSAGE);

		Assert.assertNotNull(create);
		Assert.assertTrue(create.getId() >= 1);
		Assert.assertEquals(TEST_MESSAGE, create.getMessage());

		read = this.service.read(create.getId());

		Assert.assertNotNull(read);
		Assert.assertEquals(create.getId(), read.getId());
		Assert.assertEquals(create.getMessage(), read.getMessage());

		read.setMessage(TEST_MESSAGE_UPDATE);
		this.service.update(read);
		update = this.service.read(read.getId());

		Assert.assertNotNull(update);
		Assert.assertEquals(read.getId(), update.getId());
		Assert.assertEquals(TEST_MESSAGE_UPDATE, update.getMessage());

		this.service.delete(update.getId());
		delete = this.service.read(update.getId());

		Assert.assertNull(delete);
	}

}
