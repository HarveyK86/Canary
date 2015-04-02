package org.canary.server.service;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public abstract class AbstractServiceTest<Model> {

    private CrudService<Model> service;

    private static final int ID = 1;
    private static final int INVALID_ID = -1;

    @Before
    public final void before() {
	this.service = this.getService();
    }

    public abstract CrudService<Model> getService();

    public abstract Model getModel();

    @Test
    public final void readShouldNotReturnNull() {

	final Model model = this.service.read(ID);

	Assert.assertNotNull(model);
    }

    @Test
    public final void readShouldReturnNull() {

	final Model model = this.service.read(INVALID_ID);

	Assert.assertNull(model);
    }

    @Test(expected = IllegalArgumentException.class)
    public final void updateShouldThrowIllegalArgument() {
	this.service.update(ID, null);
    }

    @Test
    public final void updateShouldExecute() {

	final Model model = this.getModel();

	this.service.update(ID, model);
    }

    @Test
    public final void deleteShouldExecute() {
	this.service.delete(ID);
    }

    protected final int getValidId() {
	return ID;
    }

}
