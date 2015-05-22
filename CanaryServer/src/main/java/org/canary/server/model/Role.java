package org.canary.server.model;

import org.springframework.security.core.GrantedAuthority;

public enum Role implements GrantedAuthority {

    USER;

    @Override
    public final String getAuthority() {
	return this.name();
    }

}
