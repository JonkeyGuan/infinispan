package com.test;

import java.io.Serializable;

public class User implements Serializable {

	private static final long serialVersionUID = -1850878979770633647L;

	public String name;
	public String address;
	public int age;
	public String[] attr;

	public static User randomUser(int i) {
		User user = new User();
		user.name = "name" + i;
		user.address = "address" + i;
		user.age = i;
		user.attr = new String[1024];
		for (int j = 0; j < 1024; j++) {
			user.attr[j] = "" + j;
		}
		return user;
	}

}
