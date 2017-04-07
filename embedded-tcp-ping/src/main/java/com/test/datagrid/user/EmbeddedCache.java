package com.test.datagrid.user;

import java.io.IOException;

import org.infinispan.Cache;
import org.infinispan.configuration.cache.Configuration;
import org.infinispan.configuration.cache.ConfigurationBuilder;
import org.infinispan.manager.DefaultCacheManager;
import org.infinispan.manager.EmbeddedCacheManager;

public class EmbeddedCache {

    private static EmbeddedCacheManager manager;
    private static Cache<String, User> cache;

    static {
//	try {
//	    manager = new DefaultCacheManager("infinispan.xml");
//	    cache = manager.getCache("myCache");
//	} catch (IOException e) {
//	    e.printStackTrace();
//	}
	
	try {
	    manager = new DefaultCacheManager("infinispan.xml");
	    Configuration rc = manager.getCacheConfiguration("CacheTemplate");
	    Configuration c = new ConfigurationBuilder().read(rc).build();
	     
	    String newCacheName = "myCache";
	    manager.defineConfiguration(newCacheName, c);
	    cache = manager.getCache(newCacheName);
	} catch (IOException e) {
	    e.printStackTrace();
	}
	
    }

    public static void put(String id, User user) {
	cache.put(id, user);
    }

    public static User get(String id) {
	return cache.get(id);
    }

}
