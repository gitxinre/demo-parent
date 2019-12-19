package com.ly.demo.common.util;


import com.ly.demo.entity.pojo.BaseEntity;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Transformer;

import java.util.*;

/**
 * @author xinre
 */
public class CollectionUtil {

    /**
     * 排序算法（先按照首字母排序，首字母相同的按照id排序）
     *
     * @param baseEntityList 需要排序的集合（集合中的对象必须继承BaseEntity）
     */
    public static void baseEntityListSort(List<? extends BaseEntity> baseEntityList) {
        Collections.sort(baseEntityList, new Comparator<BaseEntity>() {
            //重点是这个函数
            public int compare(BaseEntity o1, BaseEntity o2) {
                String s1 = o1.getNameHeadChar();
                String s2 = o2.getNameHeadChar();
                if (s1.compareTo(s2) == 0) {
                    return o1.getId().compareTo(o2.getId());
                }
                return s1.compareTo(s2);//从a-z的排
                //return s2.compareTo(s1);//从z-a的排
            }
        });
    }

    /**
     * 获取集合中属性的list集合
     *
     * @param baseEntityList 基础对象集合
     * @return id属性list集合
     */
    public static List<String> baseEntityList2IdList(List<BaseEntity> baseEntityList) {

        @SuppressWarnings("unchecked")
        List<String> r = (List<String>) CollectionUtils.collect(
                baseEntityList, new Transformer() {
                    public Object transform(Object arg0) {
                        BaseEntity baseEntity = (BaseEntity) arg0;
                        return baseEntity.getId();
                    }
                });
        return r;
    }

    /**
     * 获取集合中属性的set集合
     *
     * @param baseEntityList 基础对象集合
     * @return id属性set集合
     */
    public static Set<String> baseEntityList2IdSet(List<BaseEntity> baseEntityList) {
        return new HashSet<String>(baseEntityList2IdList(baseEntityList));
    }

    public static void main(String[] args) {
        List<BaseEntity> baseEntitys = new ArrayList<>();

        BaseEntity baseEntity1 = new BaseEntity("1", "mm0", "mm0");
        BaseEntity baseEntity2 = new BaseEntity("1", "mm1", "mm1");
        BaseEntity baseEntity3 = new BaseEntity("2", "mm2", "mm2");
        baseEntitys.add(baseEntity1);
        baseEntitys.add(baseEntity2);
        baseEntitys.add(baseEntity3);
        List<String> list = CollectionUtil.baseEntityList2IdList(baseEntitys);
        System.out.println("list = " + list);
        Set<String> set = CollectionUtil.baseEntityList2IdSet(baseEntitys);
        System.out.println("set = " + set);
    }

}
