package com.ly.demo.sso.client.crm.util;

import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Properties;

/**
 * @author xinre
 */
@Slf4j
public class SSOClientUtils {
    private static Properties ssoProperties = new Properties();
    public static String SERVER_URL_PREFIX;
    public static String CLIENT_HOST_URL;

    static {
        try {
            ssoProperties.load(SSOClientUtils.class.getClassLoader().getResourceAsStream("sso.properties"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        SERVER_URL_PREFIX = ssoProperties.getProperty("server-url-prefix");
        CLIENT_HOST_URL = ssoProperties.getProperty("client-host-url");

    }

    public static String getRedirectUrl(HttpServletRequest request) {
        return CLIENT_HOST_URL + request.getServletPath();
    }

    public static void redirectToSSOUrl(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String url = SERVER_URL_PREFIX +
                "/checkLogin?redirectUrl=" +
                getRedirectUrl(request);
        System.out.println("url = " + url);
        response.sendRedirect(url);
    }

}
