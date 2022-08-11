package com.tourism.verticle;

import com.tourism.core.CLIBootstrap;
import io.vertx.core.AbstractVerticle;

public class CLIVerticle extends AbstractVerticle {
    @Override
    public void start() throws Exception {
        CLIBootstrap.start();
    }
}
