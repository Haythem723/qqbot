package org.qqbot.core;

import org.jdeferred2.Deferred;
import org.jdeferred2.Promise;
import org.jdeferred2.impl.DeferredObject;
import org.junit.jupiter.api.Test;

public class TestPromise {
	@Test
	public void testPromiseDone() {
		final Deferred deferred = new DeferredObject();
		Promise promise = deferred.promise();
		promise.then(res -> {
			System.out.println(res);
		});
		deferred.resolve("hhh");
	}
}
