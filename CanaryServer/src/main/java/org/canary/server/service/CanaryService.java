package org.canary.server.service;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.canary.server.model.Canary;
import org.canary.server.repository.CanaryRepository;
import org.canary.server.repository.CrudRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CanaryService extends AbstractService<Canary> {

    private static final Logger LOGGER = Logger.getLogger(CanaryService.class);

    @Autowired
    private CanaryRepository repository;

    private CanaryService() {
	super();
    }

    @Override
    @Transactional
    public Canary create(final String message) {

	LOGGER.debug("create[message=" + message + "]");

	if (StringUtils.isBlank(message)) {

	    throw new IllegalArgumentException(
		    "Illegal argument; message cannot be blank.");
	}

	return this.repository.create(message);
    }

    @Override
    public final CrudRepository<Canary> getRepository() {

	LOGGER.debug("getRepository");

	return this.repository;
    }

}
