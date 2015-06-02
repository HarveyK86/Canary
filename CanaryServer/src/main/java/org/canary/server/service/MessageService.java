package org.canary.server.service;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.canary.server.model.Message;
import org.canary.server.repository.CrudRepository;
import org.canary.server.repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MessageService extends AbstractService<Message> {

    @Autowired
    private MessageRepository repository;

    private static final Logger LOGGER = Logger.getLogger(MessageService.class);

    private MessageService() {
	super();
    }

    @Override
    @Transactional
    @PreAuthorize("hasAuthority('USER')")
    public Message create(final Object... args) {

	final String value = (String) args[0];

	LOGGER.debug("create[value=" + value + "]");

	if (StringUtils.isBlank(value)) {

	    throw new IllegalArgumentException(
		    "Illegal argument; value cannot be blank.");
	}

	return this.repository.create(value);
    }

    @Override
    public final CrudRepository<Message> getRepository() {

	LOGGER.debug("getRepository");

	return this.repository;
    }

}
