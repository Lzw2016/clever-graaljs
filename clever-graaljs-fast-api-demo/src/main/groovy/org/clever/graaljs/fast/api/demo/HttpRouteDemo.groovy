package org.clever.graaljs.fast.api.demo

import org.clever.hot.reload.route.HttpRoute
import org.clever.hot.reload.route.HttpRouteRegister

class HttpRouteDemo implements HttpRoute {
    static final BASE_PATH = "org.clever.graaljs.fast.api.demo.controller";

    @Override
    void routing(HttpRouteRegister register) {
        register.startClass("${BASE_PATH}.Test01", "/api/hot_reload/test")
                .get("/t01", "t01")
                .get("/t02", "t02")
                .endClass()
    }
}
