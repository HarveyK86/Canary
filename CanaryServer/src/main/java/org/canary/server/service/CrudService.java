package org.canary.server.service;

import java.util.List;

public interface CrudService<Model> {

    Model read(final int id);

    List<Model> readAll();

    void update(final int id, final Model model);

    void delete(final int id);

}
