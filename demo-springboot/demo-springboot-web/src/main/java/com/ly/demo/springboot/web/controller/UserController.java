package com.ly.demo.springboot.web.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;
import java.util.UUID;

/**
 * @author xinre
 */
@Api(description = "用户管理公共接口")
@Controller
public class UserController {


    @ApiOperation(value = "查询用户信息")
    @RequestMapping(value = "/user/{id}",method = RequestMethod.GET)
    @ResponseBody
    public String getUserName(@PathVariable(value = "id") String id, Map<String, Object> map) {

        String userId = UUID.randomUUID().toString();
        map.put("result", userId);
        System.out.println("result = " + userId);
        return userId;
    }
}
