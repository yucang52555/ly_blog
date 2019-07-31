/**
 * Copyright (C), 2019, XXX有限公司
 * FileName: FileUtils
 * Author:   kangtiancheng
 * Date:     2019/7/10 11:05
 * Description: 文件操作工具类
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.lengyan.lyblog.utils;

import java.io.File;

/**
 * 〈一句话功能简述〉<br> 
 * 〈文件操作工具类〉
 *
 * @author kangtiancheng
 * @create 2019/7/10
 * @since 1.0.0
 */
public class FileUtils {

    /**
     * 判断文件是否存在
     * @param filePath
     * @return //判断是文件，并且文件存在，返回true, 反之则为false
     */
    public static boolean isFileExist(String filePath) {
        boolean flag = false;
        File file = new File(filePath);
        //判断是文件，并且文件存在，返回true
        if (file.isFile() && file.exists()) {
            flag = true;
        }
        return flag;
    }
}
