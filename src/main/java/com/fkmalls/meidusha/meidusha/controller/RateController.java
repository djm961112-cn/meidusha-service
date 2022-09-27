package com.fkmalls.meidusha.meidusha.controller;

import com.fkmalls.meidusha.meidusha.auth.PassToken;
import com.fkmalls.meidusha.meidusha.entity.response.controller.Response;
import io.joshworks.restclient.http.HttpResponse;
import io.joshworks.restclient.http.Unirest;
import lombok.extern.slf4j.Slf4j;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

/**
 * dengjinming
 * 2022/8/26
 */
@RestController
@Slf4j
public class RateController {

    @PassToken
    @RequestMapping(value = "/selectRateForHKD",method = RequestMethod.GET)
    public Response<?> selectRateForHKD() {
        /**
         * 各类货币兑港元电汇牌价
         */
        HttpResponse<String> response = Unirest.get("https://www.bochk.com/whk/rates/exchangeRatesHKD/exchangeRatesHKD-input.action?lang=cn")
                .header("Upgrade-Insecure-Requests", "1")
                .header("Cookie", "FontSize=0; JSESSIONID=0000Hj5UIuIEvmOEj2og0ublv32:-1; lang=zh_CN")
                .header("User-Agent", "apifox/1.0.0 (https://www.apifox.cn)")
                .asString();

        //获取html的文档对象
        Document doc = Jsoup.parse(String.valueOf(response.asString()));
        //获取页面下id="form_table"的标签
        String table = String.valueOf(doc.getElementsByClass("form_table"));
        return Response.success(table);
    }

    @PassToken
    @RequestMapping(value = "/selectRateInChina",method = RequestMethod.GET)
    public Response<?> selectRateInChina() {
        /**
         * 中国银行外汇牌价
         */
        HttpResponse<String> response = Unirest.get("https://www.boc.cn/sourcedb/whpj/")
                .header("Upgrade-Insecure-Requests", "1")
                .header("Sec-Fetch-User", "?1")
                .header("Cookie", "ariaDefaultTheme=undefined")
                .header("If-None-Match", "\"071ffea24b9d81:0\"")
                .header("If-Modified-Since", "Fri, 26 Aug 2022 08:22:02 GMT")
                .header("User-Agent", "apifox/1.0.0 (https://www.apifox.cn)")
                .asString();

        //获取html的文档对象
        Document doc = Jsoup.parse(String.valueOf(response.asString()));
        //获取页面下id="tr"的标签
        String table = String.valueOf(doc.getElementsByTag("table"));
        return Response.success(table);

    }

    @PassToken
    @RequestMapping(value = "/yy",method = RequestMethod.GET)
    public Response<?> yy() {
        /**
         * 羊了个羊
         */

        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        MediaType mediaType = MediaType.parse("application/json");
        Request request = new Request.Builder()
                .url("https://cat-match.easygame2021.com/sheep/v1/game/game_over?rank_score=1&rank_state=1&rank_time=1&rank_role=1&skin=1")
                .get()
                .addHeader("Host", "cat-match.easygame2021.com")
                .addHeader("t", "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJleHAiOjE2OTQ2NjM4MDcsIm5iZiI6MTY2MzU2MTYwNywiaWF0IjoxNjYzNTU5ODA3LCJqdGkiOiJDTTpjYXRfbWF0Y2g6bHQxMjM0NTYiLCJvcGVuX2lkIjoiIiwidWlkIjoyMzUyOTMyNTcsImRlYnVnIjoiIiwibGFuZyI6IiJ9.FRGg6OXODoe1qoOLyZdKP-EVlm-tZRMUL_6XYWmZZjs")
                .addHeader("User-Agent", "Mozilla/5.0 (Linux; Android 7.1.1; GOME 2017M27A Build/N4F26M; wv) AppleWebKit/537.36 (KHTML, like Gecko) Version/4.0 Chrome/86.0.4240.99 XWEB/4313 MMWEBSDK/20220805 Mobile Safari/537.36 MMWEBID/1205 MicroMessenger/8.0.27.2220(0x28001B37) WeChat/arm64 Weixin NetType/WIFI Language/zh_CN ABI/arm64 MiniProgramEnv/android")
                .addHeader("charset", "utf-8")
                .addHeader("content-type", "application/json")
                .addHeader("Referer", "https://servicewechat.com/wx141bfb9b73c970a9/15/page-frame.html")
                .build();
        okhttp3.Response response = null;
        try {
            response = client.newCall(request).execute();
            return Response.success(response);
        } catch (IOException e) {
            e.printStackTrace();
            return Response.success();
        }
    }


}
