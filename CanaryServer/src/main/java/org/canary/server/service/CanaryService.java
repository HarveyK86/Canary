package org.canary.server.service;

import org.canary.server.model.Canary;
import org.canary.server.repository.CanaryRepository;
import org.canary.server.repository.CrudRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public final class CanaryService extends AbstractService<Canary> {

    @Autowired
    private CanaryRepository repository;

    private CanaryService() {
	super();
    }

    @Override
    @Transactional
    public Canary create(final String message) {
	return this.repository.create(message);
    }

    @Override
    public CrudRepository<Canary> getRepository() {
	return this.repository;
    }

}
