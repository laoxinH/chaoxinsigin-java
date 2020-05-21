package com.cxsigin.laoxin.utils;

import com.alibaba.fastjson.JSONObject;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class GetUtil {

    /**
     * 向指定URL发送GET方法的请求
     * @return
     */
    public static String sendGet(String url, String param, Map<String, String> header) throws IOException {
        String result = "";
        BufferedReader in = null;
        String urlNameString = url;
        if (param != null) {
            urlNameString = url + "?" + param;
        }
        URL realUrl = new URL(urlNameString);
        // 打开和URL之间的连接
        URLConnection connection = realUrl.openConnection();
        //设置超时时间
        connection.setConnectTimeout(5000);
        connection.setReadTimeout(15000);
        // 设置通用的请求属性
        if (header!=null) {
            Iterator<Map.Entry<String, String>> it =header.entrySet().iterator();
            while(it.hasNext()){
                Map.Entry<String, String> entry = it.next();
                // System.out.println(entry.getKey()+":"+entry.getValue());
                connection.setRequestProperty(entry.getKey(), entry.getValue());
            }
        }

        connection.setRequestProperty("accept", "*/*");
        connection.setRequestProperty("connection", "Keep-Alive");
        connection.setRequestProperty("user-agent",
                "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/81.0.4044.138 Safari/537.36");

        // 建立实际的连接
        connection.connect();
        // 获取所有响应头字段
        // Map<String, List<String>> map = connection.getHeaderFields();
        // 遍历所有的响应头字段
        /*for (String key : map.keySet()) {
            System.out.println(key + "--->" + map.get(key));
        }*/
        // 定义 BufferedReader输入流来读取URL的响应，设置utf8防止中文乱码
        in = new BufferedReader(new InputStreamReader(connection.getInputStream(), "utf-8"));
        String line;
        while ((line = in.readLine()) != null) {
            result += line;
        }
        if (in != null) {
            in.close();
        }
        // 解析json字符串为对象

       // JSONObject parse = (JSONObject) JSONObject.parse(result);
        return result;
    }

    /**
     * 向指定 URL 发送POST方法的请求
     */
    public static List<String> getCookie(String url, String param, Map<String, String> header) throws UnsupportedEncodingException, IOException {


        PrintWriter out = null;
        BufferedReader in = null;
        String result = "";
        URL realUrl = new URL(url);
        // 打开和URL之间的连接
        HttpURLConnection conn = (HttpURLConnection) realUrl.openConnection();
        //设置超时时间
        conn.setConnectTimeout(30000);
        conn.setReadTimeout(30000);

        // 设置通用的请求属性
        if (header!=null) {
            for (Map.Entry<String, String> entry : header.entrySet()) {
                conn.setRequestProperty(entry.getKey(), entry.getValue());
            }
        }
        conn.setRequestProperty("accept", "*/*");
        conn.setRequestProperty("connection", "Keep-Alive");
        conn.setRequestProperty("user-agent",
                "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/81.0.4044.138 Safari/537.36");
        // 发送POST请求必须设置如下两行
        conn.setDoOutput(true);
        conn.setDoInput(true);
        //conn.setInstanceFollowRedirects(true);
        conn.setRequestMethod("POST");
        // 获取URLConnection对象对应的输出流
        out = new PrintWriter(conn.getOutputStream());
        // 发送请求参数
        //System.out.println(param);
        out.print(param);
        // flush输出流的缓冲
        out.flush();

        Map<String, List<String>> headerField = conn.getHeaderFields();

        return headerField.get("Set-Cookie");

/*        System.out.println(headerField);
        // 定义BufferedReader输入流来读取URL的响应
        in = new BufferedReader(
                new InputStreamReader(conn.getInputStream(), "utf8"));
        String line;
        while ((line = in.readLine()) != null) {
            result += line;
        }
        if(out!=null){
            out.close();
        }
        if(in!=null){
            in.close();
        }*/
        //return result;
    }
}