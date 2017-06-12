package com.test.datagrid;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicLongArray;

import org.infinispan.client.hotrod.impl.operations.PingOperation;

public class Detect implements Runnable {

	private static final int PADDING = 8;
	private static final int CONCURRENCY = 8;
	private AtomicLongArray lastActivityTimes = new AtomicLongArray(CONCURRENCY * PADDING);

	private DistributedCache distributedCache;
	private int interval;

	public Detect(DistributedCache distributedCache, int interval) {
		this.distributedCache = distributedCache;
		this.interval = interval;
	}

	public long getLastActivityTime() {
		long result = Long.MIN_VALUE;
		for (int i = 0; i < CONCURRENCY; ++i) {
			result = Math.max(result, lastActivityTimes.get(i * PADDING));
		}
		return result;
	}

	public void updateLastActivityTime() {
		long now = System.currentTimeMillis();
		Random random = ThreadLocalRandom.current();

		while (true) {
			int i = PADDING * random.nextInt(CONCURRENCY);
			long stored = lastActivityTimes.get(i);
			if (stored >= now)
				break;
			if (lastActivityTimes.compareAndSet(i, stored, now))
				break;
		}
	}

	public void run() {
		while (true) {
			PingOperation.PingResult result = distributedCache.ping();
			if (result == PingOperation.PingResult.SUCCESS || result == PingOperation.PingResult.SUCCESS_WITH_COMPAT) {
				updateLastActivityTime();
			}
			try {
				Thread.sleep(interval * 1000);
			} catch (InterruptedException ignored) {
				ignored.printStackTrace();
			}
		}

	}

}
