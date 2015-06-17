package org.canary.server.service;

import java.util.List;

import org.canary.server.model.Permission;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public abstract class AbstractServiceTest<Model> extends AuthenticationTestBase {

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

    @Test
    public final void readAllShouldNotReturnNull() {

	final List<Model> models = this.service.readAll();

	Assert.assertNotNull(models);
    }

    @Test(expected = IllegalArgumentException.class)
    public final void updateShouldThrowIllegalArgument() {
	this.service.update(ID, null);
    }

    @Test
    public final void updateShouldExecute() {

	final Model model = this.getModel();

	super.login(Permission.values());
	this.service.update(ID, model);
	super.logout();
    }

    @Test
    public final void deleteShouldExecute() {
	this.service.delete(ID);
    }

    protected final int getValidId() {
	return ID;
    }

}
