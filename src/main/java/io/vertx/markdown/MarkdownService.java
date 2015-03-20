package io.vertx.markdown;

import io.vertx.codegen.annotations.ProxyGen;
import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;
import io.vertx.markdown.impl.PegdownService;
import io.vertx.serviceproxy.ProxyHelper;

@ProxyGen
public interface MarkdownService {

	static MarkdownService create(JsonObject config) {
		// TODO : read config and instantiate the right processor
		return new PegdownService();
	}

	static MarkdownService createEventBusProxy(Vertx vertx, String address) {
	    return ProxyHelper.createProxy(MarkdownService.class, vertx, address);
	}
	
	/**
	 * Single method : Generates HTML from a Markdown string
	 * @param markdown the markdown String
	 * @param handler Callback once rendering is done
	 */
	public void generateHtml(String markdown, Handler<AsyncResult<String>> handler);
}