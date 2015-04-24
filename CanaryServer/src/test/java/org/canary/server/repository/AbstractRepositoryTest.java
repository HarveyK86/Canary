package org.canary.server.repository;

import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public abstract class AbstractRepositoryTest<Model> {

    private CrudRepository<Model> repository;

    private static final int ID = 1;
    private static final int INVALID_ID = -1;

    @Before
    public final void before() {
	this.repository = this.getRepository();
    }

    public abstract CrudRepository<Model> getRepository();

    public abstract Model getModel();

    @Test
    public final void readShouldNotReturnNull() {

	final Model model = this.repository.read(ID);

	Assert.assertNotNull(model);
    }

    @Test
    public final void readShouldReturnNull() {

	final Model model = this.repository.read(INVALID_ID);

	Assert.assertNull(model);
    }

    @Test
    public final void readAllShouldNotReturnNull() {

	final List<Model> models = this.repository.readAll();

	Assert.assertNotNull(models);
    }

    @Test(expected = IllegalArgumentException.class)
    public void updateShouldThrowIllegalArgument() {
	this.repository.update(ID, null);
    }

    @Test(expected = IllegalStateException.class)
    public void updateShouldThrowIllegalState() {

	final Model model = this.getModel();

	this.repository.update(INVALID_ID, model);
    }

    @Test
    public void updateShouldExecute() {

	final Model model = this.getModel();

	this.repository.update(ID, model);
    }

    @Test
    public void deleteShouldExecute() {
	this.repository.delete(ID);
    }

    protected final int getValidId() {
	return ID;
    }

}
