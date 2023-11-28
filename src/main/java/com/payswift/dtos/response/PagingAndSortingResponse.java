package com.payswift.dtos.response;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PagingAndSortingResponse<T> {

    private int recordCount;
    private T data;

}
