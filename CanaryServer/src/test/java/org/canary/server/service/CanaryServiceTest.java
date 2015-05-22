package org.canary.server.service;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.canary.server.model.Canary;
import org.canary.server.repository.CanaryRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.test.util.ReflectionTestUtils;

@RunWith(MockitoJUnitRunner.class)
public final class CanaryServiceTest extends AbstractServiceTest<Canary> {

    @Mock
    private CanaryRepository repository;

    private CanaryService service;

    private static final String WHITESPACE_STRING = " ";
    private static final String MESSAGE = "Message";

    private static final Logger LOGGER = Logger
	    .getLogger(CanaryServiceTest.class);

    @Override
    public CrudService<Canary> getService() {

	final int id = super.getValidId();
	final Canary canary = this.getModel();

	this.service = Mockito.mock(CanaryService.class);

	ReflectionTestUtils.setField(this.service, "repository",
		this.repository);

	this.service.postConstruct();

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
			Matchers.any(Canary.class));

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

	final Canary canary = this.service.create(MESSAGE);

	Assert.assertNotNull(canary);
    }

    @Override
    public Canary getModel() {

	final int id = super.getValidId();
	final Canary canary = new Canary();

	canary.setId(id);
	canary.setMessage(MESSAGE);

	return canary;
    }

}
