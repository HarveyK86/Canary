package org.canary.server.repository;

import javax.annotation.PostConstruct;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class AbstractRepository<Model> implements
	CrudRepository<Model> {

    @Autowired
    private SessionFactory sessionFactory;

    private Class<Model> clazz;

    private static final Logger LOGGER = Logger
	    .getLogger(AbstractRepository.class);

    protected AbstractRepository() {
	super();
    }

    @PostConstruct
    public final void postConstruct() {

	LOGGER.debug("postConstruct");

	this.clazz = this.getModelClass();
    }

    public abstract Class<Model> getModelClass();

    @Override
    @SuppressWarnings("unchecked")
    public final Model read(final int id) {

	LOGGER.debug("read[id=" + id + "]");

	final Session session = this.getSession();

	return (Model) session.get(this.clazz, id);
    }

    @Override
    public final void update(final Model model) {

	LOGGER.debug("update[model=" + model + "]");

	if (model == null) {

	    throw new IllegalArgumentException(
		    "Illegal argument; model cannot be null.");
	}

	final Session session = this.getSession();

	session.update(model);
    }

    @Override
    public final void delete(final int id) {

	LOGGER.debug("delete[id=" + id + "]");

	final Model model = this.read(id);
	final Session session = this.getSession();

	session.delete(model);
    }

    protected final Session getSession() {

	LOGGER.debug("getSession");

	return this.sessionFactory.getCurrentSession();
    }

}
