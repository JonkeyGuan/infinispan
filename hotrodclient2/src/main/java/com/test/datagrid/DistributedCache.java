package com.test.datagrid;

import java.util.Map;

import org.infinispan.client.hotrod.RemoteCache;
import org.infinispan.client.hotrod.RemoteCacheManager;

public class DistributedCache {

	private static RemoteCacheManager cacheManager;
	private static RemoteCache<String, User> cache;

	static {
		cacheManager = new RemoteCacheManager();
		cache = cacheManager.getCache("myCache");
	}

	public static void put(String id, User user) {
		cache.put(id, user);
	}
	
	public static void putAll(Map<String, User> map) {
		cache.putAll(map);
	}

	public static User get(String id) {
		return cache.get(id);
	}
}
