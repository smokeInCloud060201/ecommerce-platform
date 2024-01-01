package com.karson.ecommerce.common.utils;

import com.karson.ecommerce.common.dtos.ResponseDto;
import com.karson.ecommerce.common.exceptions.BadRequestException;

public class DtoUtil {
    private DtoUtil() {}

    public static Long toLong(String id) throws BadRequestException {
        if (!StringUtil.isBlank(id)) {
            try {
                return Long.parseLong(id);
            } catch (Exception e) {
                throw new BadRequestException("Id not correct ", e);
            }
        } else throw new BadRequestException("Id not correct");
    }

    public static <T> ResponseDto<T> toResponseDto(T dataResponse) {
        return ResponseDto.<T>builder()
                .data(dataResponse)
                .build();
    }
}
