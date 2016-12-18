package com.test.datagrid;

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

	public static User get(String id) {
		return cache.get(id);
	}
}
