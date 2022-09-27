package com.fkmalls.meidusha.meidusha.entity.response.cases;

import lombok.Data;


/**
 * 用例详情
 *
 * @author djm
 * @date 2022/7/30
 */
@Data
public class CaseDetailResp {

    private Integer caseType;

    private String description;

    private Long id;

    private String modifier;

    private String requirementId;

    private String title;

    private Long productLineId;

    private Long groupId;

}
