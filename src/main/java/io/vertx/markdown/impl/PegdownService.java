package io.vertx.markdown.impl;

import org.pegdown.Extensions;
import org.pegdown.PegDownProcessor;

import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;
import io.vertx.markdown.MarkdownService;
import io.vertx.markdown.result.MarkdownAsyncResult;

public class PegdownService implements MarkdownService {
	private final PegDownProcessor processor;
	public PegdownService(){
		processor = new PegDownProcessor(Extensions.ALL);
	}
	
	@Override
	public void generateHtml(String markdown, Handler<AsyncResult<String>> handler) {
		try {
			String result = processor.markdownToHtml(markdown);
			handler.handle(new MarkdownAsyncResult(result));
		} catch (Exception e) {
			handler.handle(new MarkdownAsyncResult(e));
		}
	}
	
}