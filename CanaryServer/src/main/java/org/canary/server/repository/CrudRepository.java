package org.canary.server.repository;

import java.util.List;

public interface CrudRepository<Model> {

    Model create(final String message);

    Model read(final int id);

    List<Model> readAll();

    void update(final int id, final Model model);

    void delete(final int id);

}
