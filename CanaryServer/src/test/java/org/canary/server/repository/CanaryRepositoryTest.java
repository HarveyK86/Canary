package org.canary.server.repository;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.canary.server.model.Canary;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.test.util.ReflectionTestUtils;

@RunWith(MockitoJUnitRunner.class)
public final class CanaryRepositoryTest extends AbstractRepositoryTest<Canary> {

    @Mock
    private SessionFactory sessionFactory;

    @Mock
    private Session session;

    @Mock
    private Criteria criteria;

    private CanaryRepository repository;

    private static final String WHITESPACE_STRING = " ";
    private static final String MESSAGE = "Message";

    private static final Logger LOGGER = Logger
	    .getLogger(CanaryRepositoryTest.class);

    @Override
    public CrudRepository<Canary> getRepository() {

	final Canary canary = this.getModel();
	final int id = super.getValidId();
	final List<Canary> canaries = new ArrayList<Canary>();

	this.repository = Mockito.mock(CanaryRepository.class);

	ReflectionTestUtils.setField(this.repository, "sessionFactory",
		this.sessionFactory);

	this.repository.postConstruct();

	Mockito.doCallRealMethod() //
		.when(this.repository) //
		.create(Matchers.anyString());

	Mockito.doCallRealMethod() //
		.when(this.repository) //
		.read(Matchers.anyInt());

	Mockito.doCallRealMethod() //
		.when(this.repository) //
		.readAll();

	Mockito.doCallRealMethod() //
		.when(this.repository) //
		.update(Matchers.anyInt(), //
			Matchers.any(Canary.class));

	Mockito.doCallRealMethod() //
		.when(this.repository) //
		.delete(Matchers.anyInt());

	Mockito.doCallRealMethod() //
		.when((AbstractRepository<Canary>) this.repository) //
		.getSession();

	Mockito.when(this.sessionFactory //
		.getCurrentSession()) //
		.thenReturn(this.session);

	Mockito.when(this.session //
		.get(Matchers.any(Class.class), //
			Matchers.eq(id))) //
		.thenReturn(canary);

	Mockito.when(this.session //
		.createCriteria(Matchers.any(Class.class))) //
		.thenReturn(this.criteria);

	Mockito.when(this.session //
		.save(Matchers.any(Canary.class))) //
		.thenReturn(id);

	Mockito.when(this.criteria //
		.list()) //
		.thenReturn(canaries);

	return this.repository;
    }

    @Test
    public void createShouldThrowIllegalArgument() {

	for (final String message : new String[] { null, StringUtils.EMPTY,
		WHITESPACE_STRING }) {

	    try {

		this.repository.create(message);
		Assert.fail();

	    } catch (final IllegalArgumentException e) {
		LOGGER.debug("Expected illegal argument caught while testing.");
	    }
	}
    }

    @Test
    public void createShouldNotReturnNull() {

	final Canary canary = this.repository.create(MESSAGE);

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
