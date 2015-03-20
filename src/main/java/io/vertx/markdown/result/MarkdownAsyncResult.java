package io.vertx.markdown.result;

import io.vertx.core.AsyncResult;

public class MarkdownAsyncResult implements AsyncResult<String> {
	private final String result;
	private final Throwable failure;
	
	public MarkdownAsyncResult(String result, Throwable failure){
		this.result = result;
		this.failure = failure;
	}
	
	public MarkdownAsyncResult(String result){
		this(result, null);
	}
	
	public MarkdownAsyncResult(Throwable failure){
		this(null, failure);
	}
	
	@Override
	public String result() {
		return result;
	}

	@Override
	public Throwable cause() {
		return failure;
	}

	@Override
	public boolean succeeded() {
		return failure == null;
	}

	@Override
	public boolean failed() {
		return failure != null;
	}
}