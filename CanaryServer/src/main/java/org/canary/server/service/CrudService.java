package org.canary.server.service;

public interface CrudService<Model> {

    Model create(final String message);

    Model read(final int id);

    void update(final int id, final Model canary);

    void delete(final int id);

}
