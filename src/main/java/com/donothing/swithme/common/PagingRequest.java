package com.donothing.swithme.common;

import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

public class PagingRequest {
    private int page = 1; // 기본값 설정
    private int size = 10; // 기본값 설정

    @Schema(description = "정렬 방향 (asc: 오름차순, desc: 내림차순)", example = "desc")
    private SortOrder order = SortOrder.DESC;

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public SortOrder getOrder() {
        return order;
    }

    public void setOrder(SortOrder order) {
        this.order = order;
    }

    public Pageable toPageable() {
        return PageRequest.of(page - 1, size);
    }
}

