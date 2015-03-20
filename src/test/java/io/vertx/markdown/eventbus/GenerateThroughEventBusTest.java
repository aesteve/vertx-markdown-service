package io.vertx.markdown.eventbus;

import java.util.concurrent.CountDownLatch;

import io.vertx.core.eventbus.DeliveryOptions;
import io.vertx.core.eventbus.ReplyException;
import io.vertx.core.json.JsonObject;

import org.junit.Test;

public class GenerateThroughEventBusTest extends MarkdownServiceTestBase {
	@Test
	public void simpleParagraph() throws Exception {
		String notFormated = "Happiness is a warm puppy";
		CountDownLatch latch = new CountDownLatch(1);
		JsonObject message = new JsonObject();
		message.put("markdown", notFormated);
		DeliveryOptions options = new DeliveryOptions();
		options.addHeader("action", "generateHtml");
		vertx.eventBus().send("vertx.markdown", message, options, reply -> {
			assertTrue("No error while parsing markdown", reply.succeeded());
			assertNotNull("Reply should not be null", reply.result());
			assertTrue("A String was returned", reply.result().body() instanceof String);
			assertEquals("The generated html is the same as the sent String", reply.result().body(), "<p>" + notFormated + "</p>");
			latch.countDown();
		});
		awaitLatch(latch);
	}

	@Test
	public void unknownAction() throws Exception {
		String action = "generateXml";
		CountDownLatch latch = new CountDownLatch(1);
		String notFormated = "Happiness is a warm puppy";
		JsonObject message = new JsonObject();
		message.put("markdown", notFormated);
		DeliveryOptions options = new DeliveryOptions();
		options.addHeader("action", action);
		options.setSendTimeout(500);
		vertx.eventBus().send("vertx.markdown", message, options, onFailure(t -> {
			assertTrue(t instanceof ReplyException);
			ReplyException re = (ReplyException)t;
			Throwable cause = re.getCause();
			assertNotNull(cause);
			assertTrue(re.getCause() instanceof IllegalStateException);
			assertEquals(cause.getMessage(), "Invalid action: "+action);
			
			latch.countDown();
		}));
		latch.countDown();
	}
}
