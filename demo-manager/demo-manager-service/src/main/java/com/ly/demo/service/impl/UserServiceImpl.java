package com.ly.demo.service.impl;

import com.ly.demo.common.util.IdGenerator;
import com.ly.demo.service.UserService;
import org.springframework.stereotype.Service;

/**
 * @author xinre
 */
@Service
public class UserServiceImpl implements UserService {
    @Override
    public String getUserId(String id) {

        return id + "-" + IdGenerator.guid();
    }
}
