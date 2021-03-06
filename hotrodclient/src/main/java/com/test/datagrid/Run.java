package com.test.datagrid;

import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicLong;

import com.test.datagrid.cache.ReadDistributedCache;
import com.test.datagrid.cache.WriteDistributedCache;

public class Run {

    private static AtomicLong seq = new AtomicLong(0);

    private static String data = "1234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234";

    private static ReadDistributedCache readDistributedCache;
    private static WriteDistributedCache writeDistributedCahce;

    public static void main(String[] args) {
	String type = "put";
	if (args != null && args.length > 0) {
	    if (!args[0].equals(type)) {
		type = args[0];
	    }
	}

	readDistributedCache = new ReadDistributedCache();
	readDistributedCache.init();

	writeDistributedCahce = new WriteDistributedCache();
	writeDistributedCahce.init();

	if (type.equals("put")) {
	    put();
	} else {
	    get();
	}
    }

    public static void put() {
	int i = 0;
	// while (true) {
	try {
	    String id = "user11";// + seq.getAndIncrement();
	    User user = new User(id, data);
	    // DistributedCache.put(id, user);
	    writeDistributedCahce.putIfAbsent(id, user);
	    System.out.println("put: " + user);
	    // if (i > 1024 * 1024 * 6) {
	    // break;
	    // }
	} catch (Exception e) {
	    e.printStackTrace();
	}
	// }
    }

    public static void get() {
	while (true) {
	    try {
		long i = ThreadLocalRandom.current().nextLong(1000000);
		String id = "user11";// + i;
		User user = readDistributedCache.get(id);
		if (user == null) {
		    System.out.println("can't get user by " + id);
		} else {
		    System.out.println(user);
		}
		Thread.sleep(3000);
	    } catch (Exception e) {
		e.printStackTrace();
	    }
	}
    }

}
