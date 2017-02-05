package com.test.datagrid.user;

import java.io.Serializable;

public class User implements Serializable {

    private static final long serialVersionUID = -1314959919648108817L;

    public User() {
    };

    public User(String id, String name) {
	super();
	this.id = id;
	this.name = name;
    }

    private String id;
    private String name;

    public String getId() {
	return id;
    }

    public void setId(String id) {
	this.id = id;
    }

    public String getName() {
	return name;
    }

    public void setName(String name) {
	this.name = name;
    }

    @Override
    public String toString() {
	return "User [id=" + id + ", name=" + name + "]";
    }

}
