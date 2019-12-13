package com.ly.demo.sso.web.listener;

import com.ly.demo.sso.web.controller.SSOController;
import com.ly.demo.sso.web.entity.dto.ClientInfoDTO;
import com.ly.demo.sso.web.util.HttpClientUtils;
import org.apache.commons.collections.CollectionUtils;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;
import java.util.List;

/**
 * @author xinre
 */
public class GlobalSessionListener implements HttpSessionListener {

    @Override
    public void sessionCreated(HttpSessionEvent httpSessionEvent) {

        System.out.println("httpSessionEvent.getSession().getId() = " + httpSessionEvent.getSession().getId());

    }

    // sso-logout 17、销毁子系统session，监听里边实现统一与全局session同步 -- s
    @Override
    public void sessionDestroyed(HttpSessionEvent httpSessionEvent) {

        System.out.println("httpSessionEvent.getSession().getId() = " + httpSessionEvent.getSession().getId());
        HttpSession session = httpSessionEvent.getSession();
        // 1、获取全局session中tokenId信息
        String tokenId = (String) session.getAttribute("tokenId");
        // 2、删除全局session数据库中tokenId信息（或者缓存中tokenId信息）
        SSOController.T_TOKEN.remove(tokenId);
        // 3、获取出注册的子系统信息，同时删除注册的子系统信息
        List<ClientInfoDTO> clientLogoutInfoList = SSOController.T_CLIENT_INFO.remove(tokenId);
        if (!CollectionUtils.isEmpty(clientLogoutInfoList)) {
            try {
                for (ClientInfoDTO clientInfoDTO :
                        clientLogoutInfoList) {
                    // 4、依次调用子系统的登出方法
                    HttpClientUtils.sendHttpRequest(clientInfoDTO.getClientUrl(), clientInfoDTO.getJSessionId());
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    // sso-logout 17、销毁子系统session，监听里边实现统一与全局session同步 -- e
}
