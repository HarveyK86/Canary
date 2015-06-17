package org.canary.server.model;

import org.junit.Assert;
import org.junit.Test;

public final class PermissionTest {

    @Test
    public void getAuthorityShouldReturnName() {

	for (final Permission permission : Permission.values()) {
	    Assert.assertEquals(permission.name(), permission.getAuthority());
	}
    }

    @Test
    public void valueOfShouldNotReturnNull() {

	String name;
	Permission value;

	for (final Permission permission : Permission.values()) {

	    name = permission.name();
	    value = Permission.valueOf(name);

	    Assert.assertNotNull(value);
	}
    }

}
