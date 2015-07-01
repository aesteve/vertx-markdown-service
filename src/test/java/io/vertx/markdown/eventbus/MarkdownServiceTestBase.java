package io.vertx.markdown.eventbus;

import io.vertx.core.Vertx;
import io.vertx.ext.unit.TestContext;
import io.vertx.ext.unit.junit.VertxUnitRunner;
import org.junit.After;
import org.junit.Before;
import org.junit.runner.RunWith;

@RunWith(VertxUnitRunner.class)
public abstract class MarkdownServiceTestBase {

	protected Vertx vertx;

	@Before
	public void setUp(TestContext context) throws Exception {
		vertx = Vertx.vertx();
		vertx.deployVerticle("io.vertx.markdown.MarkdownServiceVerticle", context.asyncAssertSuccess());
	}

	@After
	public void tearDown(TestContext context) throws Exception {
		if (vertx != null) {
			vertx.close(context.asyncAssertSuccess());
			vertx = null;
		}
	}
}