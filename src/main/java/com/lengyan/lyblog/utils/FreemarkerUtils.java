/**
 * Copyright (C), 2019, XXX有限公司
 * FileName: FreemarkerUtils
 * Author:   kangtiancheng
 * Date:     2019/7/9 14:14
 * Description: freemarker工具类
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.lengyan.lyblog.utils;

import freemarker.template.Configuration;
import freemarker.template.Template;

import java.io.*;
import java.util.Map;

/**
 * 〈freemarker工具类--页面静态化〉
 * @author kangtiancheng
 * @create 2019/7/9
 * @since 1.0.0
 */
public class FreemarkerUtils {

    /**
     * @Desc：生成word/html文件
     * @param dataMap:数据集合
     * @param templateFilePath:模板文件所在目录
     * @param templateFileName:模板文件名称
     * @param docFilePath:生成的word文档的具体目录，包括目录+名称+.doc
     * @return boolean:true-创建成功 false：创建失败
     */
    public static boolean createFile(Map dataMap, String templateFilePath, String templateFileName, String docFilePath){
        try {
            Configuration configuration = new Configuration();//创建配置实例
            configuration.setDefaultEncoding("UTF-8");//设置编码
            configuration.setDirectoryForTemplateLoading(new File(templateFilePath));//加载模板文件
            Template template = configuration.getTemplate(templateFileName);//获取模板

            //创建文件
            File outFile = new File(docFilePath);
            //如果输出目标文件夹不存在，则创建
            if (!outFile.getParentFile().exists()){
                outFile.getParentFile().mkdirs();
            }
            //该文件已经存在，那么删除，避免更新了数据但是简历没有更新的情况发生
            if(outFile.isFile() && outFile.exists()){
                outFile.delete();
            }
            //将模板和数据模型合并生成文件
            Writer out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(outFile),"UTF-8"));
            //生成文件
            template.process(dataMap, out);
            //关闭流
            out.flush();
            out.close();
            return true;//返回文件路径
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
