package com.test.datagrid;

public class Run {

	public static void main(String[] args) {
		int threadCount = 10;
		if (args != null && args.length > 0) {
			String threadCountText = args[0];
			try {
				threadCount = Integer.parseInt(threadCountText);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		for (int i = 0; i < threadCount; i++) {
			new Thread(new Tester()).start();
		}

	}

}
