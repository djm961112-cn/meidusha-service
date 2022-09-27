package com.fkmalls.meidusha.meidusha.util;

import com.alibaba.fastjson.JSONObject;
import com.fkmalls.meidusha.meidusha.entity.json.Conton;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author: djm
 * @date: 2022年06月22日 16:28
 */
public class JsonUtil {

    /**
     * 将json字符串保存到json文件中
     *
     * @param jsonString
     * @param jsonFilePath
     * @return
     */
    public static boolean saveJsonData(String jsonString, String jsonFilePath) {
        boolean result = false;
        BufferedWriter bufferedWriter = null;
        try {
            File file = new File(jsonFilePath);
            if (!file.getParentFile().exists()) {
                file.getParentFile().mkdirs();
                file.createNewFile();
            }
            bufferedWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file), StandardCharsets.UTF_8));
            bufferedWriter.write(jsonString);
            result = true;
        } catch (IOException e) {
            System.out.println("保存数据到json文件异常！" + e);
        } finally {
            if (null != bufferedWriter) {
                try {
                    bufferedWriter.close();
                } catch (IOException exception) {
                    System.out.println("保存数据到json文件后，关闭流异常" + exception);
                }
            }
        }
        return result;
    }
    //读文件
    public static String txt2String(File file, List<Conton> list){
        String result = "";
        try{
            BufferedReader br = new BufferedReader(new FileReader(file));//构造一个BufferedReader类来读取文件
            String s = null;
            while((s = br.readLine())!=null){//使用readLine方法，一次读一行
                //result = result + "\n" +s;
                result =s;
                String []strs=result.split("");
                for (Conton conton:list){
                    if (conton.getName()!=null){
                        if (strs[3].hashCode()==9){
                            String username=result.substring(0,3);
                            String name=result.substring(4).split("\\\\")[0];
                            String name2=result.substring(4).split("\\\\")[1];
                            String name3=result.substring(4).split("\\\\")[2];
                            if (name.equals(conton.getName())) {
                                conton.setCustomers(conton.getCustomers() + 1);
                            }
                        }else{
                            String username=result.substring(0,2);
                            String name=result.substring(3).split("\\\\")[0];
                            String name2=result.substring(3).split("\\\\")[1];
                            String name3=result.substring(3).split("\\\\")[2];
                            if (name.equals(conton.getName())){
                                conton.setCustomers(conton.getCustomers()+1);
                            }
                        }
                    }

                }
            }
            br.close();
        }catch(Exception e){
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 从json文件中读取数据
     *
     * @param jsonFilePath
     * @return
     */
    public static String getDataFromJsonFile(String jsonFilePath) {
        StringBuilder jsonString = new StringBuilder();
        File file = new File(jsonFilePath);
        if (file.exists()) {
            BufferedReader bufferedReader = null;
            try {
                bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(file), StandardCharsets.UTF_8));
                char[] chars = new char[8192];
                int length;
                while ((length = bufferedReader.read(chars)) != -1) {
                    String temp = new String(chars, 0, length);
                    jsonString.append(temp);
                }
            } catch (IOException e) {
                System.out.println("=====获取数据异常=====" + e);
            } finally {
                if (null != bufferedReader) {
                    try {
                        bufferedReader.close();
                    } catch (IOException ex) {
                        System.out.println("=======获取数据时，关闭流异常=======" + ex);
                    }
                }
            }
        }
        return jsonString.toString();
    }

//
//    public static String map_json(Map map){
//        JSONObject json = JSONObject.fromObject(map);
//
//        return json.toString();
//    }
//
//
//    public static void main(String[] args) throws IOException {
//        //String url="F://file/res.json";
//        String url="F://file/res.json";
//        File file = new File("F://file/data2.txt");
//
//        List<Conton> list=HtmlParaseUtils.getAllConton();
//        Map<String,Conton> map=new HashMap<>();
//
//        txt2String(file,list);
//
//        for (Conton conton:list){
//            String id=conton.getParentid();
//            conton.setParentid("0");
//            map.put(id,conton);
//        }
//
//        String json=map_json(map);
//
//        saveJsonData(json,url);
//
//
//    }

}