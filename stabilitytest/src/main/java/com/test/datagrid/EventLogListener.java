package com.test.datagrid;

import org.infinispan.client.hotrod.annotation.ClientCacheEntryCreated;
import org.infinispan.client.hotrod.annotation.ClientCacheEntryExpired;
import org.infinispan.client.hotrod.annotation.ClientCacheEntryModified;
import org.infinispan.client.hotrod.annotation.ClientCacheEntryRemoved;
import org.infinispan.client.hotrod.annotation.ClientCacheFailover;
import org.infinispan.client.hotrod.annotation.ClientListener;
import org.infinispan.client.hotrod.event.ClientCacheEntryCreatedEvent;
import org.infinispan.client.hotrod.event.ClientCacheEntryExpiredEvent;
import org.infinispan.client.hotrod.event.ClientCacheEntryModifiedEvent;
import org.infinispan.client.hotrod.event.ClientCacheEntryRemovedEvent;
import org.infinispan.client.hotrod.event.ClientCacheFailoverEvent;

@ClientListener
public class EventLogListener {
	
	public static EventLogListener Instance = new EventLogListener();

//	@ClientCacheEntryCreated
//	public void handleCreatedEvent(ClientCacheEntryCreatedEvent e) {
//		System.out.println(e);
//	}
//
//	@ClientCacheEntryModified
//	public void handleModifiedEvent(ClientCacheEntryModifiedEvent e) {
//		System.out.println(e);
//	}
//
//	@ClientCacheEntryExpired
//	public void handleExpiredEvent(ClientCacheEntryExpiredEvent e) {
//		System.out.println(e);
//	}
//
//	@ClientCacheEntryRemoved
//	public void handleRemovedEvent(ClientCacheEntryRemovedEvent e) {
//		System.out.println(e);
//	}

	@ClientCacheFailover
	public void handleFailover(ClientCacheFailoverEvent e) {
		System.out.println(e.toString());
	}
}
