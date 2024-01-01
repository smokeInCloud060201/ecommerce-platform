package com.karson.ecommerce.common.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;


@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ResponseDto <T> {
    private String errorCode;
    private String errorMessage;
    private MetaData metaData;
    @Builder.Default
    private int statusCode = 200;
    private T data;

    @JsonFormat(pattern = "dd/MM/yyyy hh:mm a")
    @Builder.Default
    private LocalDateTime timeStamp = LocalDateTime.now();
}
