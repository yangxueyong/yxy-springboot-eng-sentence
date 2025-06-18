package com.example.eng.entity.base;

import lombok.Data;

@Data
public class MyPage {

    private int pageNum;

    private int pageSize;

    public boolean hasPrevious() {
        return pageNum >= 1 && pageSize > 0;
    }

}
