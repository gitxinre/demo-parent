package com.ly.demo.common.util;


import com.ly.demo.entity.pojo.BaseEntity;
import com.ly.demo.entity.pojo.TreeNode;
import org.apache.commons.collections.CollectionUtils;

import java.util.*;

/**
 * 树工具类（针对TreeNode实体类，属性：id、parentId、text）
 *
 * @author xinre
 */
public class TreeUtils {

    /**
     * 根节点父Id
     */
    public static final String ROOT_PARENT_ID = "#";

    /**
     * listMap集合转换成listTreeNode集合
     *
     * @param listMap listMap集合
     * @param keys    map中key的数组且顺序固定对应的是treeNode中key：id、text、parentId 等
     * @return
     */
    public static List<TreeNode> listMap2ListTreeNode(List<Map<String, Object>> listMap, String... keys) {

        for (int i = 0, size = keys.length; i < size; i++) {

        }

        return null;
    }

    public static List<TreeNode> build(List<TreeNode> allNodes) {
        List<TreeNode> roots = listRoot(allNodes);
        @SuppressWarnings("unchecked")
        List<TreeNode> notRoots = (List<TreeNode>) CollectionUtils.subtract(allNodes, roots);
        return build(roots, notRoots);
    }

    public static List<TreeNode> buildByRootParentId(String rootParentId, List<TreeNode> allNodes) {
        List<TreeNode> roots = listRootByRootParentId(rootParentId, allNodes);
        @SuppressWarnings("unchecked")
        List<TreeNode> notRoots = (List<TreeNode>) CollectionUtils.subtract(allNodes, roots);
        return build(roots, notRoots);
    }

    public static List<TreeNode> buildByRootId(String rootId, List<TreeNode> allNodes) {
        List<TreeNode> roots = listRootByRootId(rootId, allNodes);
        @SuppressWarnings("unchecked")
        List<TreeNode> notRoots = (List<TreeNode>) CollectionUtils.subtract(allNodes, roots);
        return build(roots, notRoots);
    }

    private List<? extends BaseEntity> sort(List<? extends BaseEntity> list) {
        Collections.sort(list, new Comparator<BaseEntity>() {
            @Override
            public int compare(BaseEntity o1, BaseEntity o2) {
                return o1.getId().compareTo(o2.getId());
            }
        });
        return list;
    }

    private List<Map<String, Object>> sort(List<Map<String, Object>> list, final String firstPrioritySort, final String secondPrioritySort) {
        Collections.sort(list, new Comparator<Map<String, Object>>() {
            @Override
            public int compare(Map<String, Object> s1, Map<String, Object> s2) {
                int flag;
                // 首选按年龄升序排序
                flag = s1.get(firstPrioritySort).toString().compareTo(s2.get(firstPrioritySort).toString());
                if (flag == 0) {
                    // 再按学号升序排序
                    flag = s1.get(secondPrioritySort).toString().compareTo(s2.get(secondPrioritySort).toString());
                }
                return flag;
            }
        });
        return list;
    }

    /**
     * 构建树
     *
     * @param roots    根节点集合
     * @param notRoots 非根节点集合
     * @return 完整树集合
     */
    private static List<TreeNode> build(List<TreeNode> roots, List<TreeNode> notRoots) {
        for (TreeNode root : roots) {
            root.setChildren(findChildren(root, notRoots));
        }
        return roots;
    }

    /**
     * 获取根节点集合
     *
     * @param allNodes 所有节点集合
     * @return 根节点集合
     */
    private static List<TreeNode> listRoot(List<TreeNode> allNodes) {
        List<TreeNode> r = new ArrayList<TreeNode>();
        for (TreeNode node : allNodes) {
            boolean isRoot = true;
            for (TreeNode comparedOne : allNodes) {
                if (node.getParentId().equals(comparedOne.getId())) {
                    isRoot = false;
                    break;
                }
            }
            if (isRoot) {
                node.setLevel(0);
                r.add(node);
                // node.setRootId(node.getId());
            }
        }
        return r;
    }

    /**
     * 获取根节点集合
     *
     * @param rootId   根节点Id
     * @param allNodes 所有节点集合
     * @return 根节点集合
     */
    private static List<TreeNode> listRootByRootId(String rootId, List<TreeNode> allNodes) {
        List<TreeNode> r = new ArrayList<TreeNode>();
        for (TreeNode node : allNodes) {
            if (node.getId().equals(rootId)) {
                node.setLevel(0);
                r.add(node);
                // node.setRootId(node.getId());
            }
        }
        return r;
    }

    /**
     * 获取根节点集合
     *
     * @param rootParentId 根节点父Id
     * @param allNodes     所有节点集合
     * @return 根节点集合
     */
    private static List<TreeNode> listRootByRootParentId(String rootParentId, List<TreeNode> allNodes) {
        List<TreeNode> r = new ArrayList<TreeNode>();
        for (TreeNode node : allNodes) {
            if (node.getParentId().equals(rootParentId)) {
                node.setLevel(0);
                r.add(node);
                // node.setRootId(node.getId());
            }
        }
        return r;
    }

    private static List<TreeNode> findChildren(TreeNode root, List<TreeNode> allNodes) {

        List<TreeNode> r = new ArrayList<TreeNode>();
        for (TreeNode node : allNodes) {
            if (node.getParentId().equals(root.getId())) {
                //node.setParent(root);
                node.setLevel(root.getLevel() + 1);
                r.add(node);
            }
        }
        @SuppressWarnings("unchecked")
        List<TreeNode> notChildren = (List<TreeNode>) CollectionUtils.subtract(allNodes, r);
        for (TreeNode node : r) {
            List<TreeNode> r1 = findChildren(node, notChildren);
            if (r1 == null || r1.isEmpty()) {
                node.setLeaf(true);
            } else {
                node.setLeaf(false);
            }
            node.setChildren(r1);
        }
        return r;
    }
}
