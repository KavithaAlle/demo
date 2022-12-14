package com.tourism.verticle;

import com.tourism.controller.AdministratorController;
import com.tourism.controller.AuthResourcesController;
import com.tourism.controller.GraphController;
import com.tourism.controller.UserController;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.http.HttpServerOptions;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.BodyHandler;
import io.vertx.ext.web.handler.CookieHandler;
import io.vertx.ext.web.handler.SessionHandler;
import io.vertx.ext.web.handler.StaticHandler;
import io.vertx.ext.web.sstore.LocalSessionStore;
import io.vertx.ext.web.sstore.SessionStore;

public class ServerVerticle extends AbstractVerticle {
    public static Router router;

    @Override
    public void start() {
        router = Router.router(vertx); // 实例化一个路由器出来，用来路由不同的rest接口
        router.route().handler(CookieHandler.create());
        SessionStore sessionStore = LocalSessionStore.create(vertx, "EugeneSessionMap", 1000 * 60 * 30);//30分钟后进行销毁
        SessionHandler sessionHandler = SessionHandler.create(sessionStore);
        router.route().handler(sessionHandler);
        router.route().handler(BodyHandler.create()); // 增加一个处理器，将请求的上下文信息，放到RoutingContext中
        router.get("/graph/information").handler(GraphController::getGraphInformation);//处理一个get方法的rest接口
        router.post("/administrator/login").handler(AdministratorController::handleLogin);// 处理一个get方法的rest接口
        router.post("/user/login").handler(UserController::handleLogin);// 处理一个get方法的rest接口
        router.get("/static/AuthResources/:param1").handler(AuthResourcesController::handleAuthResource);
        router.route("/static/NormalResources/*").handler(StaticHandler.create("static/NormalResources"));
        router.route("/static/test/*").handler(StaticHandler.create("static/test"));
        vertx.createHttpServer(new HttpServerOptions().setMaxWebsocketFrameSize(1000 * 1000)).requestHandler(router::accept).listen(80);// 创建一个HttpServer，监听80端口，并交由路由器分发处理用户请求
    }

    public static Router getRouter() {
        return router;
    }
}
