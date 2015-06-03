package org.canary.server.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.canary.server.repository.Persistable;

@Entity
@Table(name = "message")
public final class Message implements Persistable {

    @Id
    @Column(name = "Id", nullable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(name = "Value")
    private String value;

    public Message() {
	super();
    }

    @Override
    public int getId() {
	return this.id;
    }

    public void setId(final int id) {
	this.id = id;
    }

    public String getValue() {
	return this.value;
    }

    public void setValue(final String value) {
	this.value = value;
    }

}
