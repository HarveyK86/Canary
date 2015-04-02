package org.canary.server.service;

import org.apache.commons.lang3.StringUtils;
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

    private static final Canary MODEL = new Canary();
    private static final String WHITESPACE_STRING = " ";
    private static final String MESSAGE = "Message";

    @Override
    public CrudService<Canary> getService() {

	final int id = super.getValidId();

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
		.update(Matchers.anyInt(), Matchers.any(Canary.class));

	Mockito.doCallRealMethod() //
		.when(this.service) //
		.delete(Matchers.anyInt());

	Mockito.when(this.repository //
		.read(Matchers.eq(id))) //
		.thenReturn(MODEL);

	Mockito.when(this.repository //
		.create(Matchers.anyString())) //
		.thenReturn(MODEL);

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
		// do nothing
	    }
	}
    }

    @Test
    public void createShouldNotReturnNull() {

	final Canary model = this.service.create(MESSAGE);

	Assert.assertNotNull(model);
    }

    @Override
    public Canary getModel() {

	final int id = super.getValidId();
	final Canary model = new Canary();

	model.setId(id);

	return model;
    }

}
