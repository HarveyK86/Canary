package org.canary.server.repository;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.canary.server.model.Canary;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

@Repository
public class CanaryRepository extends AbstractRepository<Canary> {

    private static final Logger LOGGER = Logger
	    .getLogger(CanaryRepository.class);

    private CanaryRepository() {
	super();
    }

    @Override
    public Canary create(final String message) {

	LOGGER.debug("create[message=" + message + "]");

	if (StringUtils.isBlank(message)) {

	    throw new IllegalArgumentException(
		    "Illegal argument; message cannot be blank.");
	}

	final Canary canary = new Canary();
	final Session session = super.getSession();
	final int id;

	canary.setMessage(message);
	id = (int) session.save(canary);

	return super.read(id);
    }

    @Override
    public final Class<Canary> getModelClass() {

	LOGGER.debug("getModelClass");

	return Canary.class;
    }

}
