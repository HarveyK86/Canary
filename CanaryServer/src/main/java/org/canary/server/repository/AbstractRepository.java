package org.canary.server.repository;

import javax.annotation.PostConstruct;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class AbstractRepository<Model> implements
	CrudRepository<Model> {

    @Autowired
    private SessionFactory sessionFactory;

    private Class<Model> clazz;

    protected AbstractRepository() {
	super();
    }

    @PostConstruct
    public final void postConstruct() {
	this.clazz = this.getModelClass();
    }

    public abstract Class<Model> getModelClass();

    @Override
    @SuppressWarnings("unchecked")
    public final Model read(final int id) {

	final Session session = this.getSession();

	return (Model) session.get(this.clazz, id);
    }

    @Override
    public final void update(final Model model) {

	final Session session = this.getSession();

	session.update(model);
    }

    @Override
    public final void delete(final int id) {

	final Model model = this.read(id);
	final Session session = this.getSession();

	session.delete(model);
    }

    protected final Session getSession() {
	return this.sessionFactory.getCurrentSession();
    }

}
