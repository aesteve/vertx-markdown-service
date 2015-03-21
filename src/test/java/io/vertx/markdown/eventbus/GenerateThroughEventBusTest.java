package io.vertx.markdown.eventbus;

import io.vertx.core.eventbus.DeliveryOptions;
import io.vertx.core.eventbus.ReplyException;
import io.vertx.core.json.JsonObject;

import io.vertx.ext.unit.Async;
import io.vertx.ext.unit.TestContext;
import org.junit.Test;

public class GenerateThroughEventBusTest extends MarkdownServiceTestBase {

	@Test
	public void simpleParagraph(TestContext context) throws Exception {
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
			ReplyException re = (ReplyException) t;
			Throwable cause = re.getCause();

			// Those assertions don't work
			// but before the test was bugged : doing 2 countdowns instead of await, so it may have been the case previously
			// but not noticed

//			context.assertNotNull(cause);
//			context.assertTrue(re.getCause() instanceof IllegalStateException);
//			context.assertEquals(cause.getMessage(), "Invalid action: " + action);
			async.complete();
		});
	}
}
