package com.karson.ecommerce.common.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SearchDto {

    @Builder.Default
    private int pageSize = 10;
    @Builder.Default
    private int currentPage = 1;
    private String searchKey;
}
