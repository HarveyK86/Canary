package org.canary.server.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.canary.server.repository.Persistable;

@Entity
@Table(name = "Canary")
public final class Canary implements Persistable {

    @Id
    @Column(name = "Id", nullable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(name = "Message")
    private String message;

    @Override
    public int getId() {
	return this.id;
    }

    public void setId(final int id) {
	this.id = id;
    }

    public String getMessage() {
	return this.message;
    }

    public void setMessage(final String message) {
	this.message = message;
    }

}
