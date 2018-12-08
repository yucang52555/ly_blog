package com.lengyan.lyblog.web.controller.admin;

import com.lengyan.lyblog.model.dto.LyblogConst;
import com.lengyan.lyblog.model.dto.JsonResult;
import com.lengyan.lyblog.model.enums.ResultCodeEnum;
import com.lengyan.lyblog.service.OptionsService;
import com.lengyan.lyblog.utils.LocaleMessageUtil;
import freemarker.template.Configuration;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * <pre>
 *     后台设置选项控制器
 * </pre>
 *
 * @author : lengyan
 * @date : 2017/12/13
 */
@Slf4j
@Controller
@RequestMapping("/admin/option")
public class OptionController {

    @Autowired
    private OptionsService optionsService;

    @Autowired
    private Configuration configuration;

    @Autowired
    private LocaleMessageUtil localeMessageUtil;

    /**
     * 请求跳转到option页面并完成渲染
     *
     * @return 模板路径admin/admin_option
     */
    @GetMapping
    public String options() {
        return "admin/admin_option";
    }

    /**
     * 保存设置选项
     *
     * @param options options
     * @return JsonResult
     */
    @PostMapping(value = "/save")
    @ResponseBody
    public JsonResult saveOptions(@RequestParam Map<String, String> options) {
        try {
            optionsService.saveOptions(options);
            //刷新options
            configuration.setSharedVariable("options", optionsService.findAllOptions());
            LyblogConst.OPTIONS.clear();
            LyblogConst.OPTIONS = optionsService.findAllOptions();
            log.info("List of saved options: " + options);
            return new JsonResult(ResultCodeEnum.SUCCESS.getCode(), localeMessageUtil.getMessage("code.admin.common.save-success"));
        } catch (Exception e) {
            log.error("Save settings option failed: {}", e.getMessage());
         return new JsonResult(ResultCodeEnum.FAIL.getCode(), localeMessageUtil.getMessage("code.admin.common.save-failed"));
         }
         }
         }
