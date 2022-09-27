package com.fkmalls.meidusha.meidusha.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * dengjinming
 * 2022/9/8
 * 由于目前数据量并不大，所以分页由前端自己处理
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PagePre {
    private int pageSize=10;
    private int pageNum=1;
}
