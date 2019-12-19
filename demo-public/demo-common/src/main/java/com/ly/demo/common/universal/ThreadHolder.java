package com.ly.demo.common.universal;



import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * 线程绑定类（请求拦截器中在渲染视图之后调用remove()清空ThreadLocal）
 *
 * @author xinre
 */
public class ThreadHolder {

    private static final ThreadLocal<Map<String, Object>> LOCAL = new ThreadLocal<Map<String, Object>>();

    public static void set(String key, Object value) {
        Map<String, Object> map = LOCAL.get();
        if (map == null) {
            map = new HashMap<String, Object>();
            LOCAL.set(map);
        }
        map.put(key, value);
    }

    public static Object get(String key) {
        Map<String, Object> map = LOCAL.get();
        if (map == null) {
            map = new HashMap<String, Object>();
            LOCAL.set(map);
        }
        return map.get(key);
    }


    /*public static void setUser(UserDTO user) {
        set(Constant.CURRENT_USER, user);
    }

    public static UserDTO getUser() {
        return (UserDTO) get(Constant.CURRENT_USER);
    }*/

    public static void setRequest(HttpServletRequest request) {
        set(Constant.CURRENT_REQUEST, request);
    }

    public static HttpServletRequest getRequest() {
        return (HttpServletRequest) get(Constant.CURRENT_REQUEST);
    }

    public static void remove() {
        LOCAL.remove();
    }

}
