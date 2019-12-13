package com.ly.demo.sso.web.util;

import org.springframework.util.StreamUtils;

import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.Map;

/**
 * @author xinre
 */
public class HttpClientUtils {

    public static String sendHttpRequest(String httpUrl, Map<String, String> params) throws Exception {

        HttpURLConnection connection = openUrlConnection(httpUrl);
        // 5、拼接参数
        if (params != null && params.size() > 0) {
            StringBuilder sb = new StringBuilder();
            for (Map.Entry<String, String> entry :
                    params.entrySet()) {
                sb.append("&")
                        .append(entry.getKey())
                        .append("=")
                        .append(entry.getValue());
                System.out.println("entry = " + entry);
            }
            connection.getOutputStream().write(sb.substring(1).getBytes("UTF-8"));
        }

        // 6、发送请求到服务器
        connection.connect();

        // 7、获取远程相应的内容

        String responseContent = StreamUtils.copyToString(connection.getInputStream(), Charset.forName("UTF-8"));

        // 8、关闭连接
        connection.disconnect();

        return responseContent;
    }


    /**
     * @param httpUrl    发送请求的地址
     * @param jSessionId 会话Id
     */
    public static void sendHttpRequest(String httpUrl, String jSessionId) throws Exception {

        HttpURLConnection conn = openUrlConnection(httpUrl);
        conn.addRequestProperty("Cookie", "JSESSIONID=" + jSessionId);
        //发送请求到服务器
        conn.connect();
        conn.getInputStream();
        conn.disconnect();
    }

    private static HttpURLConnection openUrlConnection(String httpUrl) throws Exception {
        //建立URL连接对象
        URL url = new URL(httpUrl);
        //创建连接
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        //设置请求的方式(需要是大写的)
        connection.setRequestMethod("POST");
        //设置需要输出
        connection.setDoOutput(true);
        return connection;
    }


}
