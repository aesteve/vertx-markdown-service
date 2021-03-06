package io.vertx.markdown;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.serviceproxy.ProxyHelper;

public class MarkdownServiceVerticle extends AbstractVerticle {

	public final static String ADDRESS = "vertx.markdown";

	private MarkdownService service;

	@Override
	public void start(Future<Void> startFuture) throws Exception {
		service = MarkdownService.create(config());
		String address = config().getString("address", ADDRESS);
		ProxyHelper.registerService(MarkdownService.class, vertx, service, address);
		startFuture.complete();
	}

	@Override
	public void stop(Future<Void> stopFuture) throws Exception {
		stopFuture.complete();
	}
}
