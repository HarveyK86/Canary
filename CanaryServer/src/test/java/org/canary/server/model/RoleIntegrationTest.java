package org.canary.server.model;

public class RoleIntegrationTest extends AbstractPersistableEnumIntegrationTest<Role> {

    @Override
    public final Class<Role> getModelClass() {
	return Role.class;
    }

}
