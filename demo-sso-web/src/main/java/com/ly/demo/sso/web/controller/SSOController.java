package com.ly.demo.sso.web.controller;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.ly.demo.sso.web.entity.dto.ClientInfoDTO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

/**
 * @author xinre
 */
@Slf4j
@Controller
public class SSOController {

    public static Set<String> T_TOKEN = Sets.newHashSet();
    public static Map<String, List<ClientInfoDTO>> T_CLIENT_INFO = Maps.newHashMap();


    @RequestMapping("/checkLogin")
    public String checkLogin(String redirectUrl, HttpServletRequest request, HttpSession session, Map<String, Object> model) {

        System.out.println("===/checkLogin");
        System.out.println("checkLogin session = " + session.getId());
        prientCookieInfo(request);

        // sso 3、判断是否有全局会话
        log.debug("=== s004、/checkLogin，是否存在 全局tokenId ===");
        String tokenId = (String) session.getAttribute("tokenId");

        if (StringUtils.isBlank(tokenId)) {
            // sso 4、 无全局会话
            log.debug("=== s004-01、无全局tokenId，跳转到认证中心登录页，并以参数形式带上重定向客户端地址url：[{}] ===", redirectUrl);
            //map.put("aaa", redirectUrl);
            model.put("redirectUrl", redirectUrl);
            return "login";

        } else {
            // 有全局会话
            // sso 13-end、取出全局令牌信息，重定向到redirectUrl，并带上tokenId（model或者get方式传参）
            log.debug("=== s004-02、有全局tokenId：[{}]，客户端登录成功，重定向到客户端地址url：[{}] ===", tokenId, redirectUrl);
            model.put("tokenId", tokenId);
            return "redirect:" + redirectUrl;
        }

    }

    @RequestMapping("/login")
    public String login(String username, String password, String redirectUrl, HttpServletRequest request, HttpServletResponse response, HttpSession session, Map<String, Object> model) {

        System.out.println("===/login");
        System.out.println("login session = " + session.getId());
        prientCookieInfo(request);


        // sso 5、数据库检查账号密码是否正确
        log.debug("=== s005、注册中心登录功能，校验账号密码信息 ===");
        if ("zhangsan".equals(username) && "666".equals(password)) {
            // 数据库匹配成功，登陆成功
            // sso 6、创建令牌tokenId
            String tokenId = UUID.randomUUID().toString();
            ///////////////////// -s
            Cookie cookie = new Cookie("tokenId", tokenId);
            cookie.setPath("/");
            response.addCookie(cookie);
            ///////////////////// -e
            // sso 7、把令牌放入全局会话中
            session.setAttribute("tokenId", tokenId);
            // sso 8、把令牌tokenId放入数据库或者缓存中
            SSOController.T_TOKEN.add(tokenId);
            // sso 9、重定向到 redirectUrl，把令牌信息带上使用模型对象进行传参或者url后get形式传参
            model.put("tokenId", tokenId);
            log.debug("=== s005-01、账号密码验证 [通过]，创建 全局tokenId：[{}]，全局sessionId：[{}]，此时重定向到客户端地址，并带上tokenId信息，此时数据库或缓存中tokenId信息为：[{}] ===", tokenId, session.getId(), SSOController.T_TOKEN);
            return "redirect:" + redirectUrl;

        } else {
            // 密码账号不对，重新返回登录界面，带上 redirectUrl 信息，也是用模型对象或者get形式传参
            log.debug("=== s005-02、账号密码验证 [不通过]，此时重新返回登录界面，并带上重定向到客户端url：[{}] ===", redirectUrl);
            model.put("redirectUrl", redirectUrl);
            return "login";
        }
    }

    @RequestMapping("/verify")
    @ResponseBody
    public String verify(String tokenId, String clientUrl, String jSessionId) {

        log.debug("=== s003、认证中心验证tokenId 是否合法 ===");
        if (SSOController.T_TOKEN.contains(tokenId)) {
            // sso-logout 14、记录客户端登出信息 -- s
            log.debug("=== s003-01、认证中心验证tokenId [合法-逻辑前]，tokenId：[{}]，数据库或缓存中tokenId：[{}] ===", tokenId, SSOController.T_TOKEN);
            List<ClientInfoDTO> clientInfoDTOList = SSOController.T_CLIENT_INFO.get(tokenId);
            if (CollectionUtils.isEmpty(clientInfoDTOList)) {
                clientInfoDTOList = Lists.newArrayList();
                SSOController.T_CLIENT_INFO.put(tokenId, clientInfoDTOList);
            }
            ClientInfoDTO build = ClientInfoDTO.builder().clientUrl(clientUrl).jSessionId(jSessionId).build();
            clientInfoDTOList.add(build);
            log.debug("=== s003-01、认证中心验证tokenId [合法-逻辑后]，tokenId：[{}]，数据库或缓存中tokenId：[{}]，数据库或缓存中客户端登出信息：[{}] ===", tokenId, SSOController.T_TOKEN, SSOController.T_CLIENT_INFO);
            // sso-logout 14、记录客户端登出信息 -- e
            return "true";
        }
        log.debug("=== s003-02、认证中心验证tokenId [不合法] ===");
        return "false";
    }

    // sso-logout 16、注册中心登出方法 -- s
    @RequestMapping("/logout")
    public String logout(HttpServletRequest request, HttpSession session) {

        System.out.println("===/logout");
        System.out.println("logout session = " + session.getId());
        prientCookieInfo(request);

        // 1、销毁全局会话（监听全局session的listener进行销毁所有子系统session）
        session.invalidate();
        return "login";
    }
    // sso-logout 16、注册中心登出方法 -- e


    private void prientCookieInfo(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {

            for (Cookie cookie :
                    cookies) {
                System.out.println("cookie.getComment() = [" + cookie.getComment() + "] == cookie.getDomain() = [" + cookie.getDomain() + "]");
                System.out.println("cookie.getName() = [" + cookie.getName() + "] == cookie.getPath() = [" + cookie.getPath() + "]");
                System.out.println("cookie.getValue() = [" + cookie.getValue() + "] == cookie.getMaxAge() = [" + cookie.getMaxAge() + "]");
                System.out.println("cookie.getSecure() = [" + cookie.getSecure() + "] == cookie.getVersion() = [" + cookie.getVersion() + "]");
            }
        } else {
            System.out.println("cookies = [null]");
        }
    }
}
