package com.test.datagrid.cache;

public class ReadDistributedCache extends AbstractDistributedCache {

    private final static String confFile = "hotrod-read-client.properties";

    @Override
    protected String getConf() {
	return confFile;
    }

}
