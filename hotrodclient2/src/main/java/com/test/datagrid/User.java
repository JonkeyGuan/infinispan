package com.test.datagrid;

import java.io.Serializable;

public class User implements Serializable {

	private static final long serialVersionUID = -1314959919648108817L;

	public User() {
	};

	public User(String id, String data) {
		super();
		this.id = id;
		this.data = data;
	}

	private String id;
	private String data;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getDate() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", data=" + data + "]";
	}

}
