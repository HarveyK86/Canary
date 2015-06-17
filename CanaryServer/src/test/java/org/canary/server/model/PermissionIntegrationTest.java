package org.canary.server.model;

public class PermissionIntegrationTest extends AbstractPersistableEnumIntegrationTest<Permission> {

    @Override
    public final Class<Permission> getModelClass() {
	return Permission.class;
    }

}
