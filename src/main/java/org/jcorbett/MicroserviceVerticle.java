package org.jcorbett;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.core.json.JsonObject;

import java.util.Random;

public class MicroserviceVerticle extends AbstractVerticle {
    public static Random Rand = new Random();

    @Override
    public void start(Future<Void> future) {
        vertx.createHttpServer()
            .requestHandler(
                r -> {
                    byte[] randBytes = new byte[512];
                    Rand.nextBytes(randBytes);
                    JsonObject resp = new JsonObject();
                    resp.put("message", "HelloWorld!");
                    resp.put("random", randBytes);
                    r.response().headers().set("Content-Type", "application/json");
                    r.response().end(resp.encode());
                })
            .listen(config().getInteger("http.port", 8080), result -> {
                if (result.succeeded()) {
                    future.complete();
                } else {
                    future.fail(result.cause());
                }
            });
    }

}


