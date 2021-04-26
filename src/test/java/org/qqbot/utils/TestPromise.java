package org.qqbot.utils;

import org.jdeferred2.Deferred;
import org.jdeferred2.Promise;
import org.jdeferred2.impl.DeferredObject;
import org.junit.jupiter.api.Test;

public class TestPromise {
	@Test
	public void testPromiseDone() throws InterruptedException {
		System.out.println("start");
		new SimplePromise<String>(deferred -> {
			try {
				Thread.sleep(1000);
				if (null == null) {
					deferred.reject("reject");
					return;
				}
				deferred.resolve("resolve");
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}).then(System.out::println).fail(System.out::println);
		Thread.sleep(2000);
	}
}
