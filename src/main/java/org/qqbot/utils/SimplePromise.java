package org.qqbot.utils;

import org.jdeferred2.Deferred;
import org.jdeferred2.DoneCallback;
import org.jdeferred2.FailCallback;
import org.jdeferred2.Promise;
import org.jdeferred2.impl.DeferredObject;

public class SimplePromise<T> {
	private final Deferred<T, T, T> deferred;
	private final Promise<T, T, T> promise;

	public SimplePromise(PromiseHandler<T> handler) {
		this.deferred = new DeferredObject<T, T, T>();
		this.promise = deferred.promise();
		new Thread(() -> {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			handler.handler(deferred);
		}).start();
	}

	public SimplePromise(PromiseHandler<T> handler, DoneCallback<? super T> doneCallback) {
		this.deferred = new DeferredObject<T, T, T>();
		this.promise = deferred.promise();
		this.then(doneCallback);
		new Thread(() -> {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			handler.handler(deferred);
		}).start();
	}

	public SimplePromise(PromiseHandler<T> handler, DoneCallback<? super T> doneCallback, FailCallback<? super T> failCallback) {
		this.deferred = new DeferredObject<T, T, T>();
		this.promise = deferred.promise();
		this.then(doneCallback);
		this.fail(failCallback);
		new Thread(() -> {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			handler.handler(deferred);
		}).start();
	}

	public Promise<T, T, T> me() {
		return this.promise;
	}

	public Promise<T, T, T> then(DoneCallback<? super T> doneCallback) {
		return this.promise.then(doneCallback);
	}

	public Promise<T, T, T> fail(FailCallback<? super T> failCallback) {
		return this.promise.fail(failCallback);
	}

	public Promise<T, T, T> resolve(T res) {
		return this.deferred.resolve(res);
	}

	public interface PromiseHandler<T> {
		void handler(Deferred<T, T, T> deferred);
	}
}
