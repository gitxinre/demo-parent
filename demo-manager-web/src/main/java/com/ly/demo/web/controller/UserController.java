package com.ly.demo.web.controller;

import com.ly.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.Map;

/**
 * @author xinre
 */
@Controller
public class UserController {


    @Autowired(required = false)
    private UserService userService;

    @RequestMapping("/user/{id}")
    public String getUserName(@PathVariable(value = "id") String id, Map<String, Object> map) {

        String userId = userService.getUserId(id);
        map.put("result", userId);
        System.out.println("result = " + userId);
        return "user";
    }
}
