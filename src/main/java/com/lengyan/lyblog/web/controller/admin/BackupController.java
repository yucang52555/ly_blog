package com.lengyan.lyblog.web.controller.admin;

import com.lengyan.lyblog.model.domain.Post;
import com.lengyan.lyblog.model.domain.User;
import com.lengyan.lyblog.model.dto.BackupDto;
import com.lengyan.lyblog.model.dto.LyblogConst;
import com.lengyan.lyblog.model.dto.JsonResult;
import com.lengyan.lyblog.model.enums.*;
import com.lengyan.lyblog.service.MailService;
import com.lengyan.lyblog.service.OptionsService;
import com.lengyan.lyblog.service.PostService;
import com.lengyan.lyblog.utils.LyblogUtils;
import com.lengyan.lyblog.utils.LocaleMessageUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.core.util.ZipUtil;
import cn.hutool.cron.CronUtil;
import freemarker.template.Configuration;
import freemarker.template.TemplateModelException;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <pre>
 *     后台备份控制器
 * </pre>
 *
 * @author : lengyan
 * @date : 2018/1/21
 */
@Slf4j
@Controller
@RequestMapping(value = "/admin/backup")
@Api(tags = "后台备份控制器")
public class BackupController {

    @Autowired
    private PostService postService;

    @Autowired
    private MailService mailService;

    @Autowired
    private LocaleMessageUtil localeMessageUtil;

    @Autowired
    private OptionsService optionsService;

    @Autowired
    private Configuration configuration;

    /**
     * 渲染备份页面
     *
     * @param model model
     * @return 模板路径admin/admin_backup
     */
    @GetMapping
    public String backup(@RequestParam(value = "type", defaultValue = "resources") String type, Model model) {
        List<BackupDto> backups = null;
        if (StrUtil.equals(type, BackupTypeEnum.RESOURCES.getDesc())) {
            backups = LyblogUtils.getBackUps(BackupTypeEnum.RESOURCES.getDesc());
        } else if (StrUtil.equals(type, BackupTypeEnum.DATABASES.getDesc())) {
            backups = LyblogUtils.getBackUps(BackupTypeEnum.DATABASES.getDesc());
        } else if (StrUtil.equals(type, BackupTypeEnum.POSTS.getDesc())) {
            backups = LyblogUtils.getBackUps(BackupTypeEnum.POSTS.getDesc());
        } else {
            backups = new ArrayList<>();
        }
        model.addAttribute("backups", backups);
        model.addAttribute("type", type);
        return "admin/admin_backup";
    }

    /**
     * 执行备份
     *
     * @param type 备份类型
     * @return JsonResult
     */
    @GetMapping(value = "doBackup")
    @ResponseBody
    public JsonResult doBackup(@RequestParam("type") String type) {
        if (StrUtil.equals(BackupTypeEnum.RESOURCES.getDesc(), type)) {
            return this.backupResources();
        } else if (StrUtil.equals(BackupTypeEnum.DATABASES.getDesc(), type)) {
            return this.backupDatabase();
        } else if (StrUtil.equals(BackupTypeEnum.POSTS.getDesc(), type)) {
            return this.backupPosts();
        } else {
            return new JsonResult(ResultCodeEnum.FAIL.getCode(), localeMessageUtil.getMessage("code.admin.backup.backup-failed"));
        }
    }

    /**
     * 备份数据库
     *
     * @return 重定向到/admin/backup
     */
    public JsonResult backupDatabase() {
        try {
            if (LyblogUtils.getBackUps(BackupTypeEnum.DATABASES.getDesc()).size() > CommonParamsEnum.TEN.getValue()) {
                FileUtil.del(System.getProperties().getProperty("user.home") + "/lyblog/backup/databases/");
            }
            String srcPath = System.getProperties().getProperty("user.home") + "/lyblog/";
            String distName = "databases_backup_" + DateUtil.format(DateUtil.date(), "yyyyMMddHHmmss");
            //压缩文件
            ZipUtil.zip(srcPath + "lyblog.mv.db", System.getProperties().getProperty("user.home") + "/lyblog/backup/databases/" + distName + ".zip");
            log.info("Current time: {}, database backup was performed.", DateUtil.now());
            return new JsonResult(ResultCodeEnum.SUCCESS.getCode(), localeMessageUtil.getMessage("code.admin.backup.backup-success"));
        } catch (Exception e) {
            log.error("Backup database failed: {}", e.getMessage());
            return new JsonResult(ResultCodeEnum.FAIL.getCode(), localeMessageUtil.getMessage("code.admin.backup.backup-failed"));
        }
    }

    /**
     * 备份资源文件 重要
     *
     * @return JsonResult
     */
    public JsonResult backupResources() {
        try {
            if (LyblogUtils.getBackUps(BackupTypeEnum.RESOURCES.getDesc()).size() > CommonParamsEnum.TEN.getValue()) {
                FileUtil.del(System.getProperties().getProperty("user.home") + "/lyblog/backup/resources/");
            }
            File path = new File(ResourceUtils.getURL("classpath:").getPath());
            String srcPath = path.getAbsolutePath();
            String distName = "resources_backup_" + DateUtil.format(DateUtil.date(), "yyyyMMddHHmmss");
            //执行打包
            ZipUtil.zip(srcPath, System.getProperties().getProperty("user.home") + "/lyblog/backup/resources/" + distName + ".zip");
            log.info("Current time: {}, the resource file backup was performed.", DateUtil.now());
            return new JsonResult(ResultCodeEnum.SUCCESS.getCode(), localeMessageUtil.getMessage("code.admin.backup.backup-success"));
        } catch (Exception e) {
            log.error("Backup resource file failed: {}", e.getMessage());
            return new JsonResult(ResultCodeEnum.FAIL.getCode(), localeMessageUtil.getMessage("code.admin.backup.backup-failed"));
        }
    }

    /**
     * 备份文章，导出markdown文件
     *
     * @return JsonResult
     */
    public JsonResult backupPosts() {
        List<Post> posts = postService.findAllPosts(PostTypeEnum.POST_TYPE_POST.getDesc());
        posts.addAll(postService.findAllPosts(PostTypeEnum.POST_TYPE_PAGE.getDesc()));
        try {
            if (LyblogUtils.getBackUps(BackupTypeEnum.POSTS.getDesc()).size() > CommonParamsEnum.TEN.getValue()) {
                FileUtil.del(System.getProperties().getProperty("user.home") + "/lyblog/backup/posts/");
            }
            //打包好的文件名
            String distName = "posts_backup_" + DateUtil.format(DateUtil.date(), "yyyyMMddHHmmss");
            String srcPath = System.getProperties().getProperty("user.home") + "/lyblog/backup/posts/" + distName;
            for (Post post : posts) {
                LyblogUtils.postToFile(post.getPostContentMd(), srcPath, post.getPostTitle() + ".md");
            }
            //打包导出好的文章
            ZipUtil.zip(srcPath, srcPath + ".zip");
            FileUtil.del(srcPath);
            log.info("Current time: {}, performed an article backup.", DateUtil.now());
            return new JsonResult(ResultCodeEnum.SUCCESS.getCode(), localeMessageUtil.getMessage("code.admin.backup.backup-success"));
        } catch (Exception e) {
            log.error("Backup article failed: {}", e.getMessage());
            return new JsonResult(ResultCodeEnum.FAIL.getCode(), localeMessageUtil.getMessage("code.admin.backup.backup-failed"));
        }
    }

    /**
     * 删除备份
     *
     * @param fileName 文件名
     * @param type     备份类型
     * @return JsonResult
     */
    @GetMapping(value = "delBackup")
    @ResponseBody
    public JsonResult delBackup(@RequestParam("fileName") String fileName,
                                @RequestParam("type") String type) {
        String srcPath = System.getProperties().getProperty("user.home") + "/lyblog/backup/" + type + "/" + fileName;
        try {
            FileUtil.del(srcPath);
            return new JsonResult(ResultCodeEnum.SUCCESS.getCode(), localeMessageUtil.getMessage("code.admin.common.delete-success"));
        } catch (Exception e) {
            return new JsonResult(ResultCodeEnum.FAIL.getCode(), localeMessageUtil.getMessage("code.admin.common.delete-failed"));
        }
    }

    /**
     * 备份设置
     *
     * @param autoBackup autoBackup
     * @return 重定向到/admin/backup
     */
    @PostMapping(value = "backupOption")
    public String backupOption(@RequestParam("auto_backup") String autoBackup) throws TemplateModelException {
        if (StrUtil.equals(autoBackup, TrueFalseEnum.TRUE.getDesc())) {
            if (StrUtil.equals(LyblogConst.OPTIONS.get(BlogPropertiesEnum.AUTO_BACKUP.getProp()), TrueFalseEnum.FALSE.getDesc())) {
                CronUtil.start();
                log.info("The scheduled task starts successfully!");
            }
            optionsService.saveOption("auto_backup", TrueFalseEnum.TRUE.getDesc());
        } else {
            if (StrUtil.equals(LyblogConst.OPTIONS.get(BlogPropertiesEnum.AUTO_BACKUP.getProp()), TrueFalseEnum.TRUE.getDesc())) {
                CronUtil.stop();
                log.info("The scheduled task stops successfully!");
            }
            optionsService.saveOption("auto_backup", TrueFalseEnum.FALSE.getDesc());
        }
        configuration.setSharedVariable("options", optionsService.findAllOptions());
        LyblogConst.OPTIONS.clear();
        LyblogConst.OPTIONS = optionsService.findAllOptions();
        return "redirect:/admin/backup";
    }

    /**
     * 将备份发送到邮箱
     *
     * @param fileName 文件名
     * @param type     备份类型
     * @return JsonResult
     */
    @GetMapping(value = "sendToEmail")
    @ResponseBody
    public JsonResult sendToEmail(@RequestParam("fileName") String fileName,
                                  @RequestParam("type") String type,
                                  HttpSession session) {
        String srcPath = System.getProperties().getProperty("user.home") + "/lyblog/backup/" + type + "/" + fileName;
        User user = (User) session.getAttribute(LyblogConst.USER_SESSION_KEY);
        if (null == user.getUserEmail() || StrUtil.equals(user.getUserEmail(), "")) {
            return new JsonResult(ResultCodeEnum.FAIL.getCode(), localeMessageUtil.getMessage("code.admin.backup.no-email"));
        }
        if (StrUtil.equals(LyblogConst.OPTIONS.get(BlogPropertiesEnum.SMTP_EMAIL_ENABLE.getProp()), TrueFalseEnum.FALSE.getDesc())) {
            return new JsonResult(ResultCodeEnum.FAIL.getCode(), localeMessageUtil.getMessage("code.admin.common.no-post"));
        }
        new EmailToAdmin(srcPath, user).start();
        return new JsonResult(ResultCodeEnum.SUCCESS.getCode(), localeMessageUtil.getMessage("code.admin.backup.email-success"));
    }

    /**
     * 异步发送附件到邮箱
     */
    class EmailToAdmin extends Thread {
        private String srcPath;
        private User user;

        private EmailToAdmin(String srcPath, User user) {
            this.srcPath = srcPath;
            this.user = user;
        }

        @Override
        public void run() {
            File file = new File(srcPath);
            Map<String, Object> content = new HashMap<>(3);
            try {
                content.put("fileName", file.getName());
                content.put("createAt", LyblogUtils.getCreateTime(srcPath));
                content.put("size", LyblogUtils.parseSize(file.length()));
                mailService.sendAttachMail(user.getUserEmail(), localeMessageUtil.getMessage("code.admin.backup.have-new-backup"), content, "common/mail_template/mail_attach.ftl", srcPath);
            } catch (Exception e) {
                log.error("Mail server not configured: {}", e.getMessage());
            }
        }
    }
}
