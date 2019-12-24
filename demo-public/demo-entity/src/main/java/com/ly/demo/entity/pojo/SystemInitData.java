package com.ly.demo.entity.pojo;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author xinre, created on 2019/12/20
 */
@Getter
@Setter
@Builder
@ToString
public class SystemInitData {

    private String id; // 主键 无其他含义
    private String paramCode;
    private String paramValue;
    private String paramCategory;
    private String paramName;
    private String paramOption;
    private Integer status; // 1启用；2停用
    private String remark;
    private String version;

}
