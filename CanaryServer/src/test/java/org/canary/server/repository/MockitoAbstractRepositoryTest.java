package org.canary.server.repository;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public final class MockitoAbstractRepositoryTest {

    @Mock
    private Session session;

    @Mock
    private Criteria criteria;

    private CrudRepository<Persistable> repository;

    private static final int ID = 1;
    private static final int INVALID_ID = -1;

    @Before
    @SuppressWarnings("unchecked")
    public void before() {

	final Persistable model = this.getModel();
	final List<Persistable> models = new ArrayList<Persistable>();

	this.repository = Mockito.mock(AbstractRepository.class);

	Mockito.doCallRealMethod() //
		.when(this.repository) //
		.read(Matchers.anyInt());

	Mockito.doCallRealMethod() //
		.when(this.repository) //
		.readAll();

	Mockito.doCallRealMethod() //
		.when(this.repository) //
		.update(Matchers.anyInt(), Matchers.any(Persistable.class));

	Mockito.doCallRealMethod() //
		.when(this.repository) //
		.delete(Matchers.anyInt());

	Mockito.when(((AbstractRepository<Persistable>) this.repository) //
		.getSession()) //
		.thenReturn(this.session);

	Mockito.when(this.session //
		.get(Matchers.any(Class.class), Matchers.eq(ID))) //
		.thenReturn(model);

	Mockito.when(this.session //
		.createCriteria(Matchers.any(Class.class))) //
		.thenReturn(this.criteria);

	Mockito.when(this.criteria //
		.list()) //
		.thenReturn(models);
    }

    @Test
    public void readShouldNotReturnNull() {

	final Persistable model = this.repository.read(ID);

	Assert.assertNotNull(model);
    }

    @Test
    public void readShouldReturnNull() {

	final Persistable model = this.repository.read(INVALID_ID);

	Assert.assertNull(model);
    }

    @Test
    public void readAllShouldNotReturnNull() {

	final List<Persistable> models = this.repository.readAll();

	Assert.assertNotNull(models);
    }

    @Test(expected = IllegalArgumentException.class)
    public void updateShouldThrowIllegalArgument() {
	this.repository.update(ID, null);
    }

    @Test(expected = IllegalStateException.class)
    public void updateShouldThrowIllegalState() {

	final Persistable model = this.getModel();

	this.repository.update(INVALID_ID, model);
    }

    @Test
    public void updateShouldExecute() {

	final Persistable model = this.getModel();

	this.repository.update(ID, model);
    }

    @Test
    public void deleteShouldExecute() {
	this.repository.delete(ID);
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
