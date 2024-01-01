package com.karson.ecommerce.common.anotations;

import lombok.Getter;

public class ViewScope {

    private ViewScope() {}

    public static final class Request {
        private Request() {}

    }

    public static final class Response {
        private Response() {}
    }
}
