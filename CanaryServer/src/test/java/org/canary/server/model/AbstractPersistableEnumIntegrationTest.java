package org.canary.server.model;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import javax.annotation.Resource;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext.xml")
public abstract class AbstractPersistableEnumIntegrationTest<Model extends Enum<Model>> {

    @Resource
    private SessionFactory sessionFactory;

    private Class<Model> clazz;;

    private static final String SQL = "SELECT * FROM %s ORDER BY Id";

    @Before
    public final void before() {
	this.clazz = this.getModelClass();
    }

    @Test
    @Transactional
    @SuppressWarnings("unchecked")
    public final void getIdAndNameShouldMatchDatabase()
	    throws NoSuchMethodException {

	final Model[] localModelsArray = this.clazz.getEnumConstants();
	final List<Model> localModels = Arrays.asList(localModelsArray);
	final Iterator<Model> localIterator = localModels.iterator();
	final String name = this.clazz.getSimpleName();
	final String sql = String.format(SQL, name);
	final Session session = this.sessionFactory.getCurrentSession();
	final Query query = session.createSQLQuery(sql);
	final List<Object[]> persistedModels = query.list();
	final Iterator<Object[]> persistedIterator = persistedModels.iterator();

	Model localModel;
	Object[] persistedModel;

	Assert.assertEquals(localModels.size(), persistedModels.size());

	while (localIterator.hasNext() && persistedIterator.hasNext()) {

	    localModel = localIterator.next();
	    persistedModel = persistedIterator.next();

	    Assert.assertEquals(localModel.ordinal(), persistedModel[0]);
	    Assert.assertEquals(localModel.name(), persistedModel[1]);
	}
    }

    public abstract Class<Model> getModelClass();

}
