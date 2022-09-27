package com.fkmalls.meidusha.meidusha.entity.response;

import lombok.Data;

/**
 * 返回的人员
 *
 * @author djm
 * @date 2022/7/30
 */
@Data
public class PersonResp {

    /**
     * 前缀
     */
    private String staffNamePY;

    /**
     * 中文名
     */
    private String staffNameCN;
}
