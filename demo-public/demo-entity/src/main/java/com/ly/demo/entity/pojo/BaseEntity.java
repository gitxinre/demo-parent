package com.ly.demo.entity.pojo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

/**
 * 基础简单对象
 *
 * @author xinre
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BaseEntity implements Serializable {

    private String id;
    private String name;
    private String nameHeadChar;
}
