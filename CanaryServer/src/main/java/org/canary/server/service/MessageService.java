package org.canary.server.service;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.canary.server.model.Message;
import org.canary.server.repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MessageService implements MessageServiceInterface {

    @Autowired
    private MessageRepository repository;

    private static final Logger LOGGER = Logger.getLogger(MessageService.class);

    private MessageService() {
	super();
    }

    @Override
    @Transactional
    @PreAuthorize("hasAuthority('CREATE_MESSAGE')")
    public Message create(final String value) {

	LOGGER.debug("create[value=" + value + "]");

	if (StringUtils.isBlank(value)) {

	    throw new IllegalArgumentException(
		    "Illegal argument; value cannot be blank.");
	}

	return this.repository.create(value);
    }

    @Override
    @Transactional(readOnly = true)
    @PreAuthorize("hasAuthority('READ_MESSAGE')")
    public Message read(final int id) {

	LOGGER.debug("read[id=" + id + "]");

	return this.repository.read(id);
    }

    @Override
    @Transactional(readOnly = true)
    @PreAuthorize("hasAuthority('READ_MESSAGE')")
    public List<Message> readAll() {

	LOGGER.debug("readAll");

	return this.repository.readAll();
    }

    @Override
    @Transactional
    @PreAuthorize("hasAuthority('UPDATE_MESSAGE')")
    public void update(final int id, final Message message) {

	LOGGER.debug("update[id=" + id + ", message=" + message + "]");

	if (message == null) {

	    throw new IllegalArgumentException(
		    "Illegal argument; message cannot be null.");
	}

	this.repository.update(id, message);
    }

    @Override
    @Transactional
    @PreAuthorize("hasAuthority('DELETE_MESSAGE')")
    public void delete(final int id) {

	LOGGER.debug("delete[id=" + id + "]");

	this.repository.delete(id);
    }

}
