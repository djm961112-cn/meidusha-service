package com.fkmalls.meidusha.meidusha.entity.json;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author: djm
 * @date: 2022年06月22日 16:30
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Conton {
    private String parentid;
    private String name;
    private Integer customers=0;

}