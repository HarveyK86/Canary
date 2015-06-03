package org.canary.server.model;

import java.util.ArrayList;
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

import org.canary.server.repository.Persistable;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
@Table(name = "user")
public final class User implements Persistable {

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
    @Column(name = "Role_Id")
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "user_role", joinColumns = @JoinColumn(name = "User_Id"))
    private List<Role> roles;

    public User() {
	super();
    }

    @Override
    public int getId() {
	return this.id;
    }

    public void setId(final int id) {
	this.id = id;
    }

    public String getUsername() {
	return this.username;
    }

    public void setUsername(final String username) {
	this.username = username;
    }

    @JsonIgnore
    public String getPassword() {
	return this.password;
    }

    @JsonProperty("password")
    public void setPassword(final String password) {
	this.password = password;
    }

    public List<Role> getRoles() {
	return Collections.unmodifiableList(this.roles);
    }

    public void setRoles(final List<Role> roles) {
	this.roles = new ArrayList<Role>(roles);
    }

}
