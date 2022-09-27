package com.fkmalls.meidusha.meidusha.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import java.util.List;

/**
 * dengjinming
 * 2022/8/18
 */
public class StringListToLIstUtil {

    public List<Long> toLongList(String stringList) {
        JSONObject jsonObject=new JSONObject();
        StringBuilder stringBuilder=new StringBuilder(stringList);
        stringBuilder.insert(0,"{\"json\":");
        stringBuilder.insert(stringBuilder.length(),"}");
        jsonObject= JSON.parseObject(stringBuilder.toString());
        return (List<Long>) jsonObject.get("json");
    }
}
