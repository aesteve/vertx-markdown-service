package io.vertx.markdown.eventbus;

import io.vertx.core.eventbus.DeliveryOptions;
import io.vertx.core.eventbus.ReplyException;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.unit.Async;
import io.vertx.ext.unit.TestContext;
import io.vertx.markdown.MarkdownServiceVerticle;
import io.vertx.markdown.MarkdownServiceVertxEBProxy;

import org.junit.Test;

public class GenerateThroughEventBusTest extends MarkdownServiceTestBase {

	@Test
	public void simpleParagraphThroughEB(TestContext context) throws Exception {
		String notFormated = "Happiness is a warm puppy";
		Async async = context.async();
		JsonObject message = new JsonObject();
		message.put("markdown", notFormated);
		DeliveryOptions options = new DeliveryOptions();
		options.addHeader("action", "generateHtml");
		vertx.eventBus().send("vertx.markdown", message, options, reply -> {
			context.assertTrue(reply.succeeded(), "No error while parsing markdown");
			context.assertNotNull(reply.result(), "Reply should not be null");
			context.assertTrue(reply.result().body() instanceof String, "A String was returned");
			context.assertEquals(reply.result().body(), "<p>" + notFormated + "</p>", "The generated html is the same as the sent String");
			async.complete();
		});
	}

	@Test
	public void simpleParagraphThroughProxy(TestContext context) throws Exception {
		String notFormated = "Happiness is a warm puppy";
		Async async = context.async();
		MarkdownServiceVertxEBProxy proxy = new MarkdownServiceVertxEBProxy(vertx, MarkdownServiceVerticle.ADDRESS);
		proxy.generateHtml(notFormated, res -> {
			context.assertTrue(res.succeeded());
			context.assertEquals(res.result(), "<p>" + notFormated + "</p>", "The generated html is the same as the sent String");
			async.complete();
		});
	}

	@Test
	public void unknownAction(TestContext context) throws Exception {
		String action = "generateXml";
		Async async = context.async();
		String notFormated = "Happiness is a warm puppy";
		JsonObject message = new JsonObject();
		message.put("markdown", notFormated);
		DeliveryOptions options = new DeliveryOptions();
		options.addHeader("action", action);
		options.setSendTimeout(500);
		vertx.eventBus().send("vertx.markdown", message, options, ar -> {
			context.assertTrue(ar.failed());
			Throwable t = ar.cause();
			context.assertTrue(t instanceof ReplyException);
			async.complete();
		});
	}
}
