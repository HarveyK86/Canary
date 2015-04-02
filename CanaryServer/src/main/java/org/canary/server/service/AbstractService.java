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
    public Model read(final int id) {

	LOGGER.debug("read[id=" + id + "]");

	return this.repository.read(id);
    }

    @Override
    @Transactional
    public void update(final int id, final Model model) {

	LOGGER.debug("update[id=" + id + ", model=" + model + "]");

	if (model == null) {

	    throw new IllegalArgumentException(
		    "Illegal argument; model cannot be null.");
	}

	this.repository.update(id, model);
    }

    @Override
    @Transactional
    public void delete(final int id) {

	LOGGER.debug("delete[id=" + id + "]");

	this.repository.delete(id);
    }

}
