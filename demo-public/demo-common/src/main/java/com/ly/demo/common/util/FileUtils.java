package com.ly.demo.common.util;

import com.ly.demo.common.universal.ThreadHolder;
import sun.misc.BASE64Encoder;

import java.io.IOException;
import java.net.URLEncoder;

/**
 * @author xinre
 */
public class FileUtils {

    /**
     * 下载文件时，针对不同浏览器，进行附件名的编码
     *
     * @param filename 下载文件名
     * @return 编码后的下载附件名
     * @throws IOException
     */
    //* @param agent    客户端浏览器(通过request.getHeader("user-agent")获得)
    public static String encodeDownloadFilename(String filename) {
        String agent = ThreadHolder.getRequest().getHeader("user-agent");
        try {
            if (agent.contains("Firefox")) { // 火狐浏览器
                filename = "=?UTF-8?B?" + new BASE64Encoder().encode(filename.getBytes("utf-8")) + "?=";
            } else { // IE及其他浏览器
                filename = URLEncoder.encode(filename, "utf-8");
            }
        } catch (Exception e) {
            throw new RuntimeException();
        }
        return filename;
    }
}
