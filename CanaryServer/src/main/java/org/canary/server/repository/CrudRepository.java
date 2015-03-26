package org.canary.server.repository;

public interface CrudRepository<Model> {

    Model create(final String message);

    Model read(final int id);

    void update(final Model model);

    void delete(final int id);

}
