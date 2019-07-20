package com.lengyan.lyblog.web.controller.api;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.PageUtil;
import cn.hutool.core.util.StrUtil;
import com.lengyan.lyblog.model.domain.Comment;
import com.lengyan.lyblog.model.domain.Post;
import com.lengyan.lyblog.model.domain.Tag;
import com.lengyan.lyblog.model.dto.ListPage;
import com.lengyan.lyblog.model.dto.LyblogConst;
import com.lengyan.lyblog.model.enums.*;
import com.lengyan.lyblog.service.CommentService;
import com.lengyan.lyblog.service.PostService;
import com.lengyan.lyblog.utils.CollectionUtils;
import com.lengyan.lyblog.utils.CommentUtil;
import com.lengyan.lyblog.utils.SpringUtils;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.TemplateExceptionHandler;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ConcurrentModel;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping(value = "/api/htmlGen")
@Api(tags = "ftl文件页面静态化")
public class ApiHtmlGenController {

    @Autowired
    Configuration config;

    @Autowired
    PostService postService;

    @Autowired
    CommentService commentService;

    /**
     * 页面静态化
     */
    @GetMapping
    public void staticBlog() throws Exception {
        //静态化
        config.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
        Template temple = config.getTemplate("post.ftl");//获取模板

        List<Post> postList = postService.findAllPosts(PostTypeEnum.POST_TYPE_POST.getDesc());
        for (int i = 0; i < CollectionUtils.sizeOf(postList); i++) {
            Post post = postList.get(i);
            String fileName = SpringUtils.getClassPath() + post.getPostUrl() + ".html";
            Writer out = new OutputStreamWriter(new FileOutputStream(fileName));//生成最终页面并写到文件
            try {
                Model model = new ConcurrentModel();
                if (null == post || !post.getPostStatus().equals(PostStatusEnum.PUBLISHED.getCode())) {
                    continue;
                }
                //获得当前文章的发布日期
                Date postDate = post.getPostDate();
                //查询当前文章日期之前的所有文章
                List<Post> beforePosts = postService.findByPostDateBefore(postDate);
                //查询当前文章日期之后的所有文章
                List<Post> afterPosts = postService.findByPostDateAfter(postDate);

                if (null != beforePosts && beforePosts.size() > 0) {
                    model.addAttribute("beforePost", beforePosts.get(beforePosts.size() - 1));
                }
                if (null != afterPosts && afterPosts.size() > 0) {
                    model.addAttribute("afterPost", afterPosts.get(afterPosts.size() - 1));
                }
                List<Comment> comments = null;
                if (StrUtil.equals(LyblogConst.OPTIONS.get(BlogPropertiesEnum.NEW_COMMENT_NEED_CHECK.getProp()), TrueFalseEnum.TRUE.getDesc()) || LyblogConst.OPTIONS.get(BlogPropertiesEnum.NEW_COMMENT_NEED_CHECK.getProp()) == null) {
                    comments = commentService.findCommentsByPostAndCommentStatus(post, CommentStatusEnum.PUBLISHED.getCode());
                } else {
                    comments = commentService.findCommentsByPostAndCommentStatusNot(post, CommentStatusEnum.RECYCLE.getCode());
                }
                //获取文章的标签用作keywords
                List<Tag> tags = post.getTags();
                List<String> tagWords = new ArrayList<>();
                if (tags != null) {
                    for (Tag tag : tags) {
                        tagWords.add(tag.getTagName());
                    }
                }
                //默认显示10条
                Integer size = 10;
                //获取每页评论条数
                if (StrUtil.isNotBlank(LyblogConst.OPTIONS.get(BlogPropertiesEnum.INDEX_COMMENTS.getProp()))) {
                    size = Integer.parseInt(LyblogConst.OPTIONS.get(BlogPropertiesEnum.INDEX_COMMENTS.getProp()));
                }
                //评论分页
                ListPage<Comment> commentsPage = new ListPage<Comment>(CommentUtil.getComments(comments),1, size);
                int[] rainbow = PageUtil.rainbow(1, commentsPage.getTotalPage(), 3);
                model.addAttribute("is_post",true);
                model.addAttribute("post", post);
                model.addAttribute("comments", commentsPage);
                model.addAttribute("commentsCount", comments.size());
                model.addAttribute("rainbow", rainbow);
                model.addAttribute("tagWords", CollUtil.join(tagWords, ","));

                temple.process(model, out);//处理
            } catch (TemplateException e) {
                e.printStackTrace();
            } finally {
                out.close();
            }
        }
    }
}
