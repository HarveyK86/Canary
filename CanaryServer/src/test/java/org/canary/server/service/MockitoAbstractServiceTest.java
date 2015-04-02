package org.canary.server.service;

import org.canary.server.repository.CrudRepository;
import org.canary.server.repository.Persistable;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.test.util.ReflectionTestUtils;

@RunWith(MockitoJUnitRunner.class)
public final class MockitoAbstractServiceTest {

    @Mock
    private CrudRepository<Persistable> repository;

    private CrudService<Persistable> service;

    private static final int ID = 1;
    private static final int INVALID_ID = -1;

    @Before
    @SuppressWarnings("unchecked")
    public void before() {

	final Persistable model = this.getModel();

	this.service = Mockito.mock(AbstractService.class);

	ReflectionTestUtils.setField(this.service, "repository",
		this.repository);

	Mockito.doCallRealMethod() //
		.when(this.service) //
		.read(Matchers.anyInt());

	Mockito.doCallRealMethod() //
		.when(this.service) //
		.update(Matchers.anyInt(), Matchers.any(Persistable.class));

	Mockito.doCallRealMethod() //
		.when(this.service) //
		.delete(Matchers.anyInt());

	Mockito.when(this.repository //
		.read(Matchers.eq(ID))) //
		.thenReturn(model);
    }

    @Test
    public void readShouldNotReturnNull() {

	final Persistable model = this.service.read(ID);

	Assert.assertNotNull(model);
    }

    @Test
    public void readShouldReturnNull() {

	final Persistable model = this.service.read(INVALID_ID);

	Assert.assertNull(model);
    }

    @Test(expected = IllegalArgumentException.class)
    public void updateShouldThrowIllegalArgument() {
	this.service.update(ID, null);
    }

    @Test
    public void updateShouldExecute() {

	final Persistable model = this.getModel();

	this.service.update(ID, model);
    }

    @Test
    public void deleteShouldExecute() {
	this.service.delete(ID);
    }

    private Persistable getModel() {

	return new Persistable() {

	    @Override
	    public final int getId() {
		return ID;
	    }

	};
    }

}
