package org.canary.server.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Table;

import org.apache.log4j.Logger;
import org.canary.server.repository.Persistable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
@Table(name = "user")
@SuppressWarnings("serial")
public final class User implements Persistable, UserDetails {

    @Id
    @Column(name = "Id", nullable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(name = "Username")
    private String username;

    @JsonIgnore
    @Column(name = "Password")
    private String password;

    @Enumerated
    @Column(name = "Permission_Id")
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "user_permission", //
    joinColumns = @JoinColumn(name = "User_Id"))
    private List<Permission> permissions;

    private static final Logger LOGGER = Logger.getLogger(User.class);

    public User() {

	super();

	LOGGER.debug("constructor");
    }

    @Override
    public int getId() {

	LOGGER.debug("getId");

	return this.id;
    }

    public void setId(final int id) {

	LOGGER.debug("setId[id=" + id + "]");

	this.id = id;
    }

    @Override
    public String getUsername() {

	LOGGER.debug("getUsername");

	return this.username;
    }

    public void setUsername(final String username) {

	LOGGER.debug("setUsername[username=" + username + "]");

	this.username = username;
    }

    @Override
    @JsonIgnore
    public String getPassword() {

	LOGGER.debug("getPassword");

	return this.password;
    }

    @JsonProperty("password")
    public void setPassword(final String password) {

	LOGGER.debug("setPassword[password=" + password + "]");

	this.password = password;
    }

    public boolean hasPermission(final Permission permission) {

	LOGGER.debug("hasPermission[permission=" + permission + "]");

	return this.permissions.contains(permission);
    }

    public List<Permission> getPermissions() {

	LOGGER.debug("getPermissions");

	return Collections.unmodifiableList(this.permissions);
    }

    public void setPermissions(final List<Permission> permissions) {

	LOGGER.debug("setPermissions[permissions=" + permissions + "]");

	this.permissions = new ArrayList<Permission>(permissions);
    }

    @Override
    @JsonIgnore
    public Collection<? extends GrantedAuthority> getAuthorities() {

	LOGGER.debug("getAuthorities");

	return this.getPermissions();
    }

    @Override
    @JsonIgnore
    public boolean isAccountNonExpired() {

	LOGGER.debug("isAccountNonExpired");

	return true;
    }

    @Override
    @JsonIgnore
    public boolean isAccountNonLocked() {

	LOGGER.debug("isAccountNonLocked");

	return true;
    }

    @Override
    @JsonIgnore
    public boolean isCredentialsNonExpired() {

	LOGGER.debug("isCredentialsNonExpired");

	return true;
    }

    @Override
    @JsonIgnore
    public boolean isEnabled() {

	LOGGER.debug("isEnabled");

	return true;
    }

}
