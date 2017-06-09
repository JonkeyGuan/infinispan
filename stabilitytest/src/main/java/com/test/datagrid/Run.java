package com.test.datagrid;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Run {

	private final static Logger log = LoggerFactory.getLogger(Run.class);

	private DistributedCache distributedCache;

	public static void main(String[] args) {
		int interval = 60;
		String key = "test";
		if (args != null && args.length > 0) {
			String intervalText = args[0];
			try {
				interval = Integer.parseInt(intervalText);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		if (args != null && args.length > 1) {
			key = args[1];
		}
		
		new Run().run(interval, key);

	}

	public void run(int interval, String key) {
		distributedCache = new DistributedCache();
		distributedCache.init();

		log.info("access interval is " + interval + " ms" );
		log.info("access key is " + key );
		while (true) {
			try {
				put(key);
				get(key);
				log.info(distributedCache.ping().toString());
				Thread.sleep(interval);
			} catch (Exception e) {
				e.printStackTrace();
				if (e.getStackTrace() != null) {
					log.error(e.getStackTrace().toString());
				}
			}
		}
	}
	
	public void put(String key) {
		String value = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS"));
		try {
			log.info("putting " + key + "=" + value);
			distributedCache.put(key, value);
			log.info("put is done");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void get(String key) {
		String value = "";
		try {
			log.info("getting " + key);
			value = distributedCache.get(key);
			if (value == null) {
				value = "";
			}
			log.info("got " + key + "=" + value);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
