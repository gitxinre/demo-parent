package com.ly.demo.common.util;

import org.apache.commons.lang3.StringUtils;

/**
 * 业务层级算法
 *
 * @author xinre
 */
public class LevelUtils {

    public static final String LEVEL_SEPARATOR = ".";
    public static final String LEVEL_ROOT = "0";

    public static String calculateLevel(String parentId, String parentLevel) {

        if (StringUtils.isBlank(parentLevel)) {
            return LEVEL_ROOT;
        }
        return StringUtils.join(parentLevel, LEVEL_SEPARATOR, parentId);

    }
}
