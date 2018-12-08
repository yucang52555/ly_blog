package com.lengyan.lyblog.web.controller.admin;

import com.lengyan.lyblog.model.domain.User;
import com.lengyan.lyblog.model.dto.LyblogConst;
import com.lengyan.lyblog.model.dto.JsonResult;
import com.lengyan.lyblog.model.enums.ResultCodeEnum;
import com.lengyan.lyblog.service.UserService;
import com.lengyan.lyblog.utils.LocaleMessageUtil;
import cn.hutool.crypto.SecureUtil;
import freemarker.template.Configuration;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

/**
 * <pre>
 *     后台用户管理控制器
 * </pre>
 *
 * @author : lengyan
 * @date : 2017/12/24
 */
@Slf4j
@Controller
@RequestMapping(value = "/admin/profile")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private Configuration configuration;

    @Autowired
    private LocaleMessageUtil localeMessageUtil;

    /**
     * 获取用户信息并跳转
     *
     * @return 模板路径admin/admin_profile
     */
    @GetMapping
    public String profile() {
        return "admin/admin_profile";
    }

    /**
     * 处理修改用户资料的请求
     *
     * @param user    user
     * @param session session
     * @return JsonResult
     */
    @PostMapping(value = "save")
    @ResponseBody
    public JsonResult saveProfile(@Valid @ModelAttribute User user, BindingResult result, HttpSession session) {
        try {
            if (result.hasErrors()) {
                for (ObjectError error : result.getAllErrors()) {
                    return new JsonResult(ResultCodeEnum.FAIL.getCode(), error.getDefaultMessage());
                }
            }
            userService.saveByUser(user);
            configuration.setSharedVariable("user", userService.findUser());
            session.removeAttribute(LyblogConst.USER_SESSION_KEY);
        } catch (Exception e) {
            log.error("Failed to modify user profile: {}", e.getMessage());
            return new JsonResult(ResultCodeEnum.FAIL.getCode(), localeMessageUtil.getMessage("code.admin.common.edit-failed"));
        }
        return new JsonResult(ResultCodeEnum.SUCCESS.getCode(), localeMessageUtil.getMessage("code.admin.common.edit-success"));
    }

    /**
     * 处理修改密码的请求
     *
     * @param beforePass 旧密码
     * @param newPass    新密码
     * @param userId     用户编号
     * @param session    session
     * @return JsonResult
     */
    @PostMapping(value = "changePass")
    @ResponseBody
    public JsonResult changePass(@ModelAttribute("beforePass") String beforePass,
                                 @ModelAttribute("newPass") String newPass,
                                 @ModelAttribute("userId") Long userId,
                                 HttpSession session) {
        try {
            User user = userService.findByUserIdAndUserPass(userId, SecureUtil.md5(beforePass));
            if (null != user) {
                user.setUserPass(SecureUtil.md5(newPass));
                userService.saveByUser(user);
                session.removeAttribute(LyblogConst.USER_SESSION_KEY);
            } else {
                return new JsonResult(ResultCodeEnum.FAIL.getCode(), localeMessageUtil.getMessage("code.admin.user.old-password-error"));
            }
        } catch (Exception e) {
            log.error("Password change failed: {}", e.getMessage());
            return new JsonResult(ResultCodeEnum.FAIL.getCode(), localeMessageUtil.getMessage("code.admin.user.update-password-failed"));
        }
        return new JsonResult(ResultCodeEnum.SUCCESS.getCode(), localeMessageUtil.getMessage("code.admin.user.update-password-success"));
    }
}
