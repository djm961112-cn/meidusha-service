package com.fkmalls.meidusha.meidusha.entity.response.dir;

import lombok.Data;

/**
 * 返回给前端的所有文件夹的列表
 *
 * @author djm
 * @date 2022/7/30
 */
@Data
public class BizListResp {

    private String bizId;

    private String text;

    private boolean select;
}
