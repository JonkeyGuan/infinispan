package com.test.datagrid;

import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicLong;

public class Run {

    private static AtomicLong seq = new AtomicLong(0);

    private static String data = "1234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234";

    public static void main(String[] args) {
	String type = "put";
	if (args != null && args.length > 0) {
	    if (!args[0].equals(type)) {
		type = args[0];
	    }
	}

	if (type.equals("put")) {
	    put();
	} else {
	    get();
	}
    }

    public static void put() {
 	int i = 0;
 	while (true) {
 	    String id = "user" + seq.getAndIncrement();
 	    User user = new User(id, data);
 	    DistributedCache.put(id, user);
 	    System.out.println("put: " + user);
 	    if (i > 1024 * 1024 * 6) {
 		break;
 	    }
 	}
     }

     public static void get() {
 	while (true) {
 	    long i = ThreadLocalRandom.current().nextLong(1000000);
 	    String id = "user" + i;
 	    User user = DistributedCache.get(id);
 	    if (user == null) {
 		System.out.println("can't get user by " + id);
 	    } else {
 		System.out.println(user);
 	    }
 	}
     }

}
