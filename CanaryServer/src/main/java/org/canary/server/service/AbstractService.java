package org.canary.server.service;

import javax.annotation.PostConstruct;

import org.apache.log4j.Logger;
import org.canary.server.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

public abstract class AbstractService<Model> implements CrudService<Model> {

    private CrudRepository<Model> repository;

    private static final Logger LOGGER = Logger
	    .getLogger(AbstractService.class);

    protected AbstractService() {
	super();
    }

    @PostConstruct
    public final void postConstruct() {

	LOGGER.debug("postConstruct");

	this.repository = this.getRepository();
    }

    public abstract CrudRepository<Model> getRepository();

    @Override
    @Transactional(readOnly = true)
    public final Model read(final int id) {

	LOGGER.debug("read[id=" + id + "]");

	return this.repository.read(id);
    }

    @Override
    @Transactional
    public final void update(final Model model) {

	LOGGER.debug("update[model=" + model + "]");

	if (model == null) {

	    throw new IllegalArgumentException(
		    "Illegal argument; model cannot be null.");
	}

	this.repository.update(model);
    }

    @Override
    @Transactional
    public final void delete(final int id) {

	LOGGER.debug("delete[id=" + id + "]");

	this.repository.delete(id);
    }

}
