package com.lengyan.lyblog.web.interceptor;

import com.lengyan.lyblog.model.dto.LyblogConst;
import com.lengyan.lyblog.utils.FileUtils;
import com.lengyan.lyblog.utils.FreemarkerUtils;
import com.lengyan.lyblog.utils.Md5Utils;
import com.lengyan.lyblog.web.controller.core.BaseController;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.View;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * <pre>
 *     freemarker页面静态化拦截器(实现请求一次，判断静态化页面是否存在，若存在，则跳转静态html页面)
 * </pre>
 *
 * @author : lengyan
 * @date : 2019/6/28
 */
@Slf4j
@Component
public class FreeMarkerInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        log.info("---------------------开始进入请求地址拦截----------------------------" + System.currentTimeMillis());
//        String relativelyPath=System.getProperty("user.dir");
//        log.info("---------------relativelyPath-------------------------" + relativelyPath);
//        String requestUrl = request.getRequestURL().toString();
//        log.info("---------------requestUrl-------------------------" + requestUrl);
//        String md5url = Md5Utils.MD5(requestUrl);
//        log.info("---------------md5url-------------------------" + md5url);
//        String htmlRealPath = System.getProperty("user.dir") +"\\target\\classes\\static\\html\\" + md5url + ".html";
//        log.info("---------------htmlRealPath-------------------------" + htmlRealPath);
//        if (FileUtils.isFileExist(htmlRealPath)) {
//            log.info("---------------htmlRealPath-------------------------" + htmlRealPath);
//            log.info("---------------返回静态html页面，不用继续加载访问后台-------------------------");
//            //返回静态html页面，不用继续加载访问后台
//            return false;
//        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        log.info("--------------处理请求完成后视图渲染之前的处理操作---------------" + System.currentTimeMillis());
//        String requestUrl = request.getRequestURL().toString();
//        log.info("---------------requestUrl-------------------------" + requestUrl);
//        String md5url = Md5Utils.MD5(requestUrl);
//        log.info("---------------md5url-------------------------" + md5url);
//        String baseDir = System.getProperty("user.dir").replace("\\", "/");
//        log.info("---------------baseDir-------------------------" + baseDir);
//        String htmlRealPath = baseDir +"/target/classes/static/html/" + md5url + ".html";
//        log.info("---------------htmlRealPath-------------------------" + htmlRealPath);
//        Map<String, String> options = LyblogConst.OPTIONS;
//        Map<String, Object> model = modelAndView.getModel();
//        model.put("options", options);
//        model.put("themeName", BaseController.THEME);
//        String templatePath = modelAndView.getViewName();
//        String templateFilePath = baseDir +"/target/classes/templates/" + templatePath.substring(0, templatePath.lastIndexOf("/"));
//        log.info("---------------templateFilePath-------------------------" + templateFilePath);
////        String templateFileName = baseDir +"/target/classes/templates/" + templatePath;
//        String templateFileName = templatePath.substring(templatePath.lastIndexOf("/") + 1, templatePath.length()) + ".ftl";
//        log.info("---------------templateFileName-------------------------" + templateFileName);
//        boolean staticFlag = FreemarkerUtils.createFile(model, templateFilePath, templateFileName, htmlRealPath);
//        log.info("---------------页面静态化结果-------------------------" + staticFlag);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        log.info("---------------视图渲染之后的操作-------------------------" + System.currentTimeMillis());
    }
}
