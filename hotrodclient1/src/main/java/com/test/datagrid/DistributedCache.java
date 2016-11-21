package com.test.datagrid;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.infinispan.client.hotrod.RemoteCache;
import org.infinispan.client.hotrod.RemoteCacheManager;
import org.infinispan.client.hotrod.configuration.ClusterConfigurationBuilder;
import org.infinispan.client.hotrod.configuration.ConfigurationBuilder;
import org.infinispan.client.hotrod.exceptions.HotRodClientException;
import org.infinispan.client.hotrod.impl.ConfigurationProperties;
import org.infinispan.commons.CacheConfigurationException;
import org.infinispan.commons.util.FileLookupFactory;
import org.infinispan.commons.util.Util;

public class DistributedCache {

    // Match IPv4 (host:port) or IPv6 ([host]:port) addresses
    private static final Pattern ADDRESS_PATTERN = Pattern.compile("(\\[([0-9A-Fa-f:]+)\\]|([^:/?#]*))(?::(\\d*))?");

    private static RemoteCacheManager cacheManager;
    private static RemoteCache<String, User> cache;

    static {
	ConfigurationBuilder builder = new ConfigurationBuilder();
	ClassLoader cl = Thread.currentThread().getContextClassLoader();
	builder.classLoader(cl);
	InputStream stream = FileLookupFactory.newInstance().lookupFile(RemoteCacheManager.HOTROD_CLIENT_PROPERTIES, cl);
	Properties properties = null;
	if (stream == null) {
	    System.out.println("can't find file:" + RemoteCacheManager.HOTROD_CLIENT_PROPERTIES);
	} else {
	    try {
		properties = new Properties();
		properties.load(stream);
		builder.withProperties(properties);
	    } catch (IOException e) {
		throw new HotRodClientException("Issues configuring from client " + RemoteCacheManager.HOTROD_CLIENT_PROPERTIES, e);
	    } finally {
		Util.close(stream);
	    }
	}

	if (properties != null) {
	    String authUsername = properties.getProperty("auth.username");
	    String authPassword = properties.getProperty("auth.password");
	    String authRealm = properties.getProperty("auth.realm");
	    builder.security().authentication().callbackHandler(new TestCallbackHandler(authUsername, authRealm, authPassword.toCharArray()));

	    String clusterName = properties.getProperty("infinispan.client.hotrod.cluster_name");
	    String clusterServerList = properties.getProperty("infinispan.client.hotrod.cluster_server_list");
	    ClusterConfigurationBuilder clusterConfigurationBuilder = builder.addCluster(clusterName);
	    for (String server : clusterServerList.split(";")) {
		Matcher matcher = ADDRESS_PATTERN.matcher(server);
		if (matcher.matches()) {
		    String v6host = matcher.group(2);
		    String v4host = matcher.group(3);
		    String host = v6host != null ? v6host : v4host;
		    String portString = matcher.group(4);
		    int port = portString == null ? ConfigurationProperties.DEFAULT_HOTROD_PORT : Integer.parseInt(portString);
		    clusterConfigurationBuilder.addClusterNode(host, port);
		} else {
		    throw new CacheConfigurationException(server);
		}

	    }

	    cacheManager = new RemoteCacheManager(builder.build());
	    cache = cacheManager.getCache("AionUser");
	}

    }

    public static void put(String id, User user) {
	cache.put(id, user);
    }

    public static void putIfAbsent(String id, User user) {
	cache.putIfAbsent(id, user);
    }

    public static User get(String id) {
	return cache.get(id);
    }
}
