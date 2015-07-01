package org.canary.server.repository;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.canary.server.model.Message;
import org.canary.server.model.User;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

@Repository
public class MessageRepository extends AbstractRepository<Message> {

    private static final Logger LOGGER = Logger
	    .getLogger(MessageRepository.class);

    private MessageRepository() {
	super();
    }

    public Message create(final User author, final String value) {

	LOGGER.debug("create[author=" + author + ", value=" + value + "]");

	if (author == null || StringUtils.isBlank(value)) {

	    throw new IllegalArgumentException(
		    "Illegal argument; author cannot be null and value cannot be blank.");
	}

	final Message message = new Message();
	final Session session = super.getSession();
	final int id;

	message.setAuthor(author);
	message.setValue(value);
	id = (int) session.save(message);

	return super.read(id);
    }

    @Override
    public final Class<Message> getModelClass() {

	LOGGER.debug("getModelClass");

	return Message.class;
    }

}
