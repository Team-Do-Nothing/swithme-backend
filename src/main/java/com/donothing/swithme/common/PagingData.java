package com.donothing.swithme.common;

import lombok.Getter;

@Getter
public class PagingData<T> {
    private final T data;
    private final int PageNumber;
    private final Long totalCount;

    public PagingData(final T data, int PageNumber, final long totalCount) {
        this.data = data;
        this.PageNumber = PageNumber;
        this.totalCount = totalCount;
    }
}
