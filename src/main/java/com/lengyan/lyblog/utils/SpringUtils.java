package com.lengyan.lyblog.utils;

import org.springframework.util.ClassUtils;
import org.springframework.util.ResourceUtils;

import java.io.FileNotFoundException;
import java.net.URL;

/**
 * spring工具类
 * @Auther: kangtiancheng
 * @Date: 2019/3/6 16:15
 * @Description:
 */
public class SpringUtils {

    public static String getClassPath() throws FileNotFoundException{
        String url = ResourceUtils.getURL("classpath:").getPath();
        return url;
    }

    public static String getAbsolutePath() {
        return ClassUtils.getDefaultClassLoader().getResource("").getPath();
    }

    public static void main(String[] args) throws Exception {
        System.out.println(getClassPath());
        System.out.println(getAbsolutePath());
    }
}
