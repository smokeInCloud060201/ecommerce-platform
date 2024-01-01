package com.karson.ecommerce.common.constants;

import lombok.Getter;

@Getter
public class ResponseErrorCode {
    private ResponseErrorCode() {}

    public static final String RESOURCE_NOT_FOUND = "F001";
    public static final String BAD_REQUEST = "B001";
    public static final String UNKNOW_ERROR = "U001";
    public static final String UN_AUTHORIZE = "U002";
}
