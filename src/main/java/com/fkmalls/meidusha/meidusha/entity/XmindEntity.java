package com.fkmalls.meidusha.meidusha.entity;

import com.fkmalls.meidusha.meidusha.constants.enums.XmindMarkers;
import lombok.Data;

import java.util.List;

/**
 * @author: djm
 * @date: 2022年06月23日 18:43
 */
@Data
public class XmindEntity {
    /**
    * 子节点
    * */
    private String children;
    /**
     * 节点内容
     * */
    private String title;
    /**
     * 子节点标记
     * */
    private List<String> markers;


}
