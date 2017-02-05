package com.test.datagrid.cache;

public class WriteDistributedCache extends AbstractDistributedCache {

    private final static String confFile = "hotrod-write-client.properties";

    @Override
    protected String getConf() {
	return confFile;
    }

}
