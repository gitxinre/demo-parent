package com.ly.demo.sso.client.crm.filter;

import com.google.common.collect.Maps;
import com.ly.demo.sso.client.crm.util.HttpClientUtils;
import com.ly.demo.sso.client.crm.util.SSOClientUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Map;

/**
 * @author xinre
 */
@Slf4j
public class SSOClientFilter implements Filter {


    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) servletRequest;
        HttpServletResponse resp = (HttpServletResponse) servletResponse;

        HttpSession session = req.getSession();
        // sso 1、判断是否有局部会话 (有局部会话使用 isLogin 属性进行区分)
        log.debug("=== c001、客户端校验是否有局部会话 isLogin ===");
        Boolean isLogin = (Boolean) session.getAttribute("isLogin"); // 使用包装类型机型接收，布尔类型会有问题

        if (isLogin != null && isLogin) {
            // 有局部会话，放行
            log.debug("=== c002-01、有局部会话 isLogin：[true]，直接放行filterChain.doFilter(servletRequest, servletResponse) ===");
            filterChain.doFilter(servletRequest, servletResponse);
        } else {
            // sso 10、判断url地址中是否携带tokenId信息
            log.debug("=== c002-02、没有局部会话 isLogin：[{}]，判断是否传递 全局tokenId ===", isLogin);
            String tokenId = req.getParameter("tokenId");
            if (StringUtils.isNotBlank(tokenId)) {
                // sso 11、tokenId 不为空 ，拥有统一认证中心发放，调用统一认证中心反向认证tokenId是否正确
                log.debug("=== c003-01、全局tokenId：[{}] 不为空，认证中心反向验证tokenId是否合法。并传递另外两个参数 客户端登出url：[{}],jSessionId：[{}] ===", tokenId, SSOClientUtils.CLIENT_HOST_URL + "/logout", session.getId());
                String url = SSOClientUtils.SERVER_URL_PREFIX + "/verify";
                Map<String, String> params = Maps.newHashMap();
                params.put("tokenId", tokenId);
                // sso-logout 13、登出 时 需要的 客户端地址与sessionId信息 -- s
                params.put("clientUrl", SSOClientUtils.CLIENT_HOST_URL + "/logout");
                params.put("jSessionId", session.getId());
                // sso-logout 13、登出 时 需要的 客户端地址与sessionId信息 -- e
                try {
                    String result = HttpClientUtils.sendHttpRequest(url, params);
                    if ("true".equals(result)) {
                        // 是认证中心返回的tokenId
                        // sso 12、创建局部会话
                        log.debug("=== c003-01-01、认证中心反向验证tokenId [合法]，创建局部会话属性 isLogin：[true]，直接放行 ===");
                        session.setAttribute("isLogin", true);
                        filterChain.doFilter(servletRequest, servletResponse);
                        return;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            log.debug("=== c003-02、全局tokenId：[{}] 为空、或者认证中心认证不通过。直接重定向到认证中心检查是否有其他系统登录 ===", tokenId);
            // sso 2、没有登录（没有局部会话），重定向到统一认证中心，检查是否有其他系统已经登录
            SSOClientUtils.redirectToSSOUrl(req, resp);
        }

    }

    @Override
    public void destroy() {

    }
}
