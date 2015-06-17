package org.canary.server.model;

import org.apache.log4j.Logger;
import org.springframework.security.core.GrantedAuthority;

public enum Permission implements GrantedAuthority {

    CREATE_MESSAGE, //
    READ_MESSAGE, //
    UPDATE_MESSAGE, //
    DELETE_MESSAGE, //

    CREATE_USER, //
    READ_USER, //
    UPDATE_USER, //
    DELETE_USER;

    private static final Logger LOGGER = Logger.getLogger(Permission.class);

    private Permission() {
	// do nothing
    }

    @Override
    public final String getAuthority() {

	LOGGER.debug("getAuthority");

	return this.name();
    }

}
