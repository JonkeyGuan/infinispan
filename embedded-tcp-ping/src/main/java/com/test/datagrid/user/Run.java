package com.test.datagrid.user;

import java.util.concurrent.atomic.AtomicLong;

import org.jboss.logging.Logger;

public class Run {

    private static final Logger log = Logger.getLogger(Run.class);

    private static AtomicLong seq = new AtomicLong(1);

    public static void main(String[] args) {
	Run run = new Run();
	// run.simple();
	// run.continuousUnique();
	// run.continuousSameSeq();
	run.continuousSingle();
    }

    public void simple() {
	User user = EmbeddedCache.get("user1");
	log.info(user);
	EmbeddedCache.put("user1", new User("user1", "user" + System.currentTimeMillis()));
	user = EmbeddedCache.get("user1");
	log.info(user);
	while (true) {
	    try {
		Thread.sleep(1000);
	    } catch (InterruptedException e) {
		e.printStackTrace();
	    }
	}
    }

    public void continuousUnique() {
	while (true) {
	    try {
		String userId = "userA" + seq.getAndIncrement();
		String value = "" + System.currentTimeMillis();
		User user = new User(userId, value);
		EmbeddedCache.put(userId, user);
		log.info("put: " + user);
		Thread.sleep(1);
	    } catch (InterruptedException e) {
		e.printStackTrace();
	    }
	}
    }

    public void continuousSameSeq() {
	while (true) {
	    try {
		String userId = "user" + seq.getAndIncrement();
		String value = "" + System.currentTimeMillis();
		User user = new User(userId, value);
		EmbeddedCache.put(userId, user);
		log.info("put: " + user);
		Thread.sleep(1);
	    } catch (InterruptedException e) {
		e.printStackTrace();
	    }
	}
    }

    public void continuousSingle() {
	while (true) {
//	    try {
		String userId = "user";
		String value = "" + System.currentTimeMillis();
		User user = new User(userId, value);
		EmbeddedCache.put(userId, user);
		log.info("put: " + user);
//		Thread.sleep(1);
//	    } catch (InterruptedException e) {
//		e.printStackTrace();
//	    }
	}
    }

}
