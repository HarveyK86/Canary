package org.canary.server.service;

import org.canary.server.model.Message;

public interface MessageServiceInterface extends CrudService<Message> {

    Message create(final String value);

}
