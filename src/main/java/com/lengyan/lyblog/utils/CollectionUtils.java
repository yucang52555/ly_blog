package com.lengyan.lyblog.utils;

import java.util.List;

/**
 * 集合工具类
 * @Auther: kangtiancheng
 * @Date: 2019/2/10 16:15
 * @Description:
 */
public class CollectionUtils {
    /**
     * 获取集合的大小
     * @param
     * @return
     * @变更记录 2016年10月17日 上午10:58:43  ktc
     */
    public static int sizeOf(List<?> elements) {
        return elements == null ? 0: elements.size();
    }

    /**
     * 获取数组的大小
     * @param
     * @return
     * @变更记录 2016年10月17日 上午10:58:43  ktc
     */
    public static int lengthOf(Object[] elements) {
        return elements == null ? 0: elements.length;
    }
}
