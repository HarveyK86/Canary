package org.canary.server.model;

import org.junit.Assert;
import org.junit.Test;

public final class RoleTest {

    @Test
    public void getAuthorityShouldReturnName() {

	for (final Role role : Role.values()) {
	    Assert.assertEquals(role.name(), role.getAuthority());
	}
    }

    @Test
    public void valueOfShouldNotReturnNull() {

	String name;
	Role value;

	for (final Role role : Role.values()) {

	    name = role.name();
	    value = Role.valueOf(name);

	    Assert.assertNotNull(value);
	}
    }

}
