# vertx-markdown-service
Basic Vert.x service to generate html from markdown over the event bus

This project aims at showing how easy it is to proxify an existing service through Vert.x's event bus.


Take a look at [this example](https://github.com/aesteve/vertx-markdown-service/blob/master/src/test/java/io/vertx/markdown/eventbus/MarkdownServiceTestBase.java) to see how the service is deployed in a real-life example.

Take a look at [this class](https://github.com/aesteve/vertx-markdown-service/blob/master/src/main/java/io/vertx/markdown/MarkdownServiceVerticle.java) to understand how you can proxify your own service on the event bus.

Read or copy/paste [build.gradle](https://github.com/aesteve/vertx-markdown-service/blob/master/build.gradle) to have the correct dependencies and Gradle tasks to generate your own event bus proxy for your service.

Finally, take a look at [this example](https://github.com/aesteve/vertx-markdown-service/blob/master/src/test/java/io/vertx/markdown/eventbus/GenerateThroughEventBusTest.java) to understand how services are invoked through the event bus.
