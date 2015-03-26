package org.canary.server.service;

import javax.annotation.PostConstruct;

import org.canary.server.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

public abstract class AbstractService<Model> implements CrudService<Model> {

    private CrudRepository<Model> repository;

    protected AbstractService() {
	super();
    }

    @PostConstruct
    public final void postConstruct() {
	this.repository = this.getRepository();
    }

    public abstract CrudRepository<Model> getRepository();

    @Override
    @Transactional(readOnly = true)
    public final Model read(final int id) {
	return this.repository.read(id);
    }

    @Override
    @Transactional
    public final void update(final Model model) {
	this.repository.update(model);
    }

    @Override
    @Transactional
    public final void delete(final int id) {
	this.repository.delete(id);
    }

}
