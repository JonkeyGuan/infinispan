package com.test.datagrid.user;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jboss.logging.Logger;

public class UserServlet extends HttpServlet {

    private static final long serialVersionUID = -7003525307704686462L;

    private static final Logger log = Logger.getLogger(UserServlet.class);

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

	String action = request.getParameter("action");
	if (action == null) {
	    action = "put";
	}

	String id = request.getParameter("id");
	if (id == null) {
	    id = "user";
	}

	String name = request.getParameter("name");
	if (name == null) {
	    name = "" + System.currentTimeMillis();
	}

	String random = request.getParameter("random");
	if (random == null) {
	    random = "";
	}

	if (action.equalsIgnoreCase("put")) {
	    putUser(id, name, random);
	} else {
	    getUser(id);
	}
    }

    private void putUser(String id, String name, String random) {
	String userId = "";
	if (id.isEmpty()) {
	    userId = "user";
	} else {
	    userId = id;
	}

	if (random.equalsIgnoreCase("true")) {
	    userId = userId + System.currentTimeMillis();
	}

	User user = new User(userId, name);
	EmbeddedCache.put(userId, user);
	log.info("put: " + user);
	// System.out.println("put: " + user);
    }

    private void getUser(String id) {
	String userId = "";
	if (id.isEmpty()) {
	    userId = "user";
	} else {
	    userId = id;
	}

	User user = EmbeddedCache.get(userId);
	log.info("get: " + user);
	// System.out.println("get: " + user);
    }

}
