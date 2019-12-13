package com.ly.demo.sso.client.crm.controller;

import com.ly.demo.sso.client.crm.util.SSOClientUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author xinre
 */
@WebServlet(name = "mainServlet", urlPatterns = "/main")
public class MainServlet extends HttpServlet{

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        // sso-logout 15、客户端调用注册中心的登出方法 -- s
        req.setAttribute("ssoServerLogoutUrl", SSOClientUtils.SERVER_URL_PREFIX + "/logout");
        // sso-logout 15、客户端调用注册中心的登出方法 -- e
        req.getRequestDispatcher("/WEB-INF/templates/main.jsp").forward(req, resp);
    }
}
