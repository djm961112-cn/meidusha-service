package com.fkmalls.meidusha.meidusha.entity.response.cases;

import lombok.Data;

/**
 * 导出的xmind所含有的内容
 *
 * @author djm
 * @date 2022/7/30
 */
@Data
public class ExportXmindResp {

    private String fileName;

    private byte[] data;
}
