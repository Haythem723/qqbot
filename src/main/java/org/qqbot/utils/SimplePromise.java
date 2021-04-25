package org.qqbot.utils;

import org.jdeferred2.Deferred;
import org.jdeferred2.DoneCallback;
import org.jdeferred2.Promise;
import org.jdeferred2.impl.DeferredObject;

public class SimplePromise<T> {
	private final Deferred<T, T, T> deferred;
	private final Promise<T, T, T> promise;

	public SimplePromise(DoneCallback<? super T> doneCallback) {
		this.deferred = new DeferredObject<T, T, T>();
		this.promise = this.deferred.promise();
		this.promise.then(doneCallback);
	}
	public Promise<T, T, T> resolve(T res) {
		return this.deferred.resolve(res);
	}
}
