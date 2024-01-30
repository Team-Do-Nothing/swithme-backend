package com.donothing.swithme.common;

import lombok.Getter;

@Getter
public class PagingData<T> {
    private final T data;
    private final Long totalCount;

    public PagingData(final T data, final long totalCount) {
        this.data = data;
        this.totalCount = totalCount;
    }
}
