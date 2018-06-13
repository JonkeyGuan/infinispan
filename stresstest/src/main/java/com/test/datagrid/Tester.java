package com.test.datagrid;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Tester implements Runnable {

	private final static Logger log = LoggerFactory.getLogger(Tester.class);

	private DistributedCache cache;
	private String key;
	private String cacheName;
	
	public Tester(String cacheName) {
		this.cacheName  =cacheName;
	}

	public void run() {
		cache = new DistributedCache();
		cache.init(cacheName);

		while (true) {
			key = Thread.currentThread().getName() + "-" + System.currentTimeMillis();
			put(key);
			get(key);
		}
	}

	public void put(String key) {
		String value = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS"));
		try {
			log.info("putting " + key + "=" + value);
			cache.put(key, value);
			log.info("put is done");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void get(String key) {
		String value = "";
		try {
			log.info("getting " + key);
			value = cache.get(key);
			if (value == null) {
				value = "";
			}
			log.info("got " + key + "=" + value);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
