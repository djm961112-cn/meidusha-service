package com.fkmalls.meidusha.meidusha.entity.response.cases;

import lombok.Data;

/**
 * 脑图-查看用例上方的概览信息(不包括content)
 *
 * @author djm
 * @date 2022/7/30
 */
@Data
public class CaseGeneralInfoResp {

    private Long id;

    private String title;

    private String requirementId;

    private Long productLineId;
}
