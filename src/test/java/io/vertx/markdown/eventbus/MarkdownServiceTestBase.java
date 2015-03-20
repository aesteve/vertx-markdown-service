package io.vertx.markdown.eventbus;

import io.vertx.test.core.VertxTestBase;

import java.util.concurrent.CountDownLatch;

public class MarkdownServiceTestBase extends VertxTestBase {
	@Override
	public void setUp() throws Exception {
		super.setUp();
		CountDownLatch latch = new CountDownLatch(1);
		vertx.deployVerticle("io.vertx.markdown.MarkdownServiceVerticle", handler -> {
			if (handler.cause() != null) {
				handler.cause().printStackTrace();
			}
			assertTrue(handler.succeeded());
			latch.countDown();
		});
		awaitLatch(latch);
	}
}