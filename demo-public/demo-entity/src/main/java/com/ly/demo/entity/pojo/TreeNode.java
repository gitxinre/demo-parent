package com.ly.demo.entity.pojo;

import lombok.*;

import java.util.List;

/**
 * @author xinre
 */
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TreeNode {


    private String id;
    private String text;
    private String parentId;
    //private String rootId;

    private TreeNode parent;

    private String qtip;////鼠标悬停提示信息

    private boolean isType = false;

    private int level; //需要计算处理

    private boolean leaf;

    private String iconCls = "no-icon";

    private List<TreeNode> children;

    private Boolean checked;

    private Boolean expanded;

    private Object data;
}
