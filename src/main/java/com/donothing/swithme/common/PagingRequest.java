package com.donothing.swithme.common;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

public class PagingRequest {
    private int page = 1; // 기본값 설정
    private int size = 10; // 기본값 설정

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

    public Pageable toPageable() {
        return PageRequest.of(page - 1, size);
    }

}

