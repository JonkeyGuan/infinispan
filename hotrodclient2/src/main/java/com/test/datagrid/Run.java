package com.test.datagrid;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Run {

	private final static Logger log = LoggerFactory.getLogger(Run.class);

	private static AtomicLong seq = new AtomicLong(0);

	private static String data = "1234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234";

	public static void main(String[] args) {
		String type = "put";
		if (args != null && args.length > 0) {
			if (!args[0].equals(type)) {
				type = args[0];
			}
		}

		if (type.equalsIgnoreCase("put")) {
			put();
		} else if (type.equalsIgnoreCase("putInBulk")) {
			putInBulk();
		} else if (type.endsWith("getViaThreads")) {
			getViaThreads();
		} else {
			get();
		}
	}

	public static void put() {
		// int i = 0;
		while (true) {

			if (seq.get() > 150000)
				break;

			try {
				String id = "user" + seq.incrementAndGet();
				User user = new User(id, data);
				DistributedCache.put(id, user);
				String dateText = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS"));
				log.info(dateText + "	put	" + id);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public static void putInBulk() {
		int i = 0;
		Map<String, User> map = new HashMap<String, User>();

		String dateText = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS"));
		log.info(dateText + "	put	start");

		while (true) {
			try {
				if (seq.get() > 150000) {
					if (map.size() > 0) {
						DistributedCache.putAll(map);
						dateText = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS"));
						log.info(dateText + "	put	last batch");
					}
					break;
				}

				String id = "user" + seq.incrementAndGet();
				User user = new User(id, data);
				map.put(id, user);
				i++;
				if (i % 10000 == 0) {
					DistributedCache.putAll(map);
					map.clear();
					i = 0;
					dateText = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS"));
					log.info(dateText + "	put	" + id);
				}

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public static void get() {
		String id = "user" + seq.incrementAndGet();
		while (true) {
			try {
				// long i = ThreadLocalRandom.current().nextLong(1000000);

				if (seq.get() > 150000)
					break;

				User user = DistributedCache.get(id);
				if (user == null) {
					// system.out.println("can't get user by " + id);
				} else {
					String dateText = LocalDateTime.now()
							.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS"));
					log.info(dateText + "	get	" + id);
					id = "user" + seq.incrementAndGet();
				}
				// Thread.sleep(3000);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public static void getViaThreads() {

		for (int i = 0; i < 10000; i++) {
			Runnable myRunnable = new Runnable() {
				public void run() {
					while (true) {
						String id = "user" + new Random().nextInt(100);
						User user = DistributedCache.get(id);
						if (user != null) {
							String dateText = LocalDateTime.now()
									.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS"));
							log.info(dateText + " " + Thread.currentThread().getName() + "	get	" + id);
						}
					}
				}
			};
			Thread thread = new Thread(myRunnable);
			thread.start();
		}

	}

}
