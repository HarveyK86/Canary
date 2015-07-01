package org.canary.server.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.apache.log4j.Logger;
import org.canary.server.repository.Persistable;

@Entity
@Table(name = "message")
public final class Message implements Persistable {

    @Id
    @Column(name = "Id", nullable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @ManyToOne
    @JoinColumn(name = "AuthorUser_Id")
    private User author;

    @Column(name = "Value")
    private String value;

    private static final Logger LOGGER = Logger.getLogger(Message.class);

    public Message() {

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

    public User getAuthor() {

	LOGGER.debug("getAuthor");

	return this.author;
    }

    public void setAuthor(final User author) {

	LOGGER.debug("setAuthor[author=" + author + "]");

	this.author = author;
    }

    public String getValue() {

	LOGGER.debug("getvalue");

	return this.value;
    }

    public void setValue(final String value) {

	LOGGER.debug("setValue[value=" + value + "]");

	this.value = value;
    }

    @Override
    public String toString() {

	return "Message [id=" + this.id + ", author=" + this.author
		+ ", value=" + this.value + "]";
    }

}
