package com.lengyan.lyblog.web.controller.front;

import com.lengyan.lyblog.model.domain.Comment;
import com.lengyan.lyblog.model.domain.Gallery;
import com.lengyan.lyblog.model.domain.Post;
import com.lengyan.lyblog.model.dto.LyblogConst;
import com.lengyan.lyblog.model.dto.ListPage;
import com.lengyan.lyblog.model.enums.BlogPropertiesEnum;
import com.lengyan.lyblog.model.enums.CommentStatusEnum;
import com.lengyan.lyblog.model.enums.PostTypeEnum;
import com.lengyan.lyblog.model.enums.TrueFalseEnum;
import com.lengyan.lyblog.service.CommentService;
import com.lengyan.lyblog.service.GalleryService;
import com.lengyan.lyblog.service.PostService;
import com.lengyan.lyblog.utils.CommentUtil;
import com.lengyan.lyblog.web.controller.core.BaseController;
import cn.hutool.core.util.PageUtil;
import cn.hutool.core.util.StrUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * <pre>
 *     前台内置页面，自定义页面控制器
 * </pre>
 *
 * @author : lengyan
 * @date : 2018/4/26
 */
@Controller
public class FrontPageController extends BaseController {

    @Autowired
    private GalleryService galleryService;

    @Autowired
    private PostService postService;

    @Autowired
    private CommentService commentService;

    /**
     * 跳转到图库页面
     *
     * @return 模板路径/themes/{theme}/gallery
     */
    @GetMapping(value = "/gallery")
    public String gallery(Model model) {
        List<Gallery> galleries = galleryService.findAllGalleries();
        model.addAttribute("galleries", galleries);
        return this.render("gallery");
    }

    /**
     * 友情链接
     *
     * @return 模板路径/themes/{theme}/links
     */
    @GetMapping(value = "/links")
    public String links() {
        return this.render("links");
    }

    /**
     * 渲染自定义页面
     *
     * @param postUrl 页面路径
     * @param model   model
     * @return 模板路径/themes/{theme}/post
     */
    @GetMapping(value = "/p/{postUrl}")
    public String getPage(@PathVariable(value = "postUrl") String postUrl,
                          @RequestParam(value = "cp",defaultValue = "1") Integer cp,
                          Model model) {
        Post post = postService.findByPostUrl(postUrl, PostTypeEnum.POST_TYPE_PAGE.getDesc());
        if (null == post) {
            return this.renderNotFound();
        }
        List<Comment> comments = null;
        if (StrUtil.equals(LyblogConst.OPTIONS.get(BlogPropertiesEnum.NEW_COMMENT_NEED_CHECK.getProp()), TrueFalseEnum.TRUE.getDesc()) || LyblogConst.OPTIONS.get(BlogPropertiesEnum.NEW_COMMENT_NEED_CHECK.getProp()) == null) {
            comments = commentService.findCommentsByPostAndCommentStatus(post, CommentStatusEnum.PUBLISHED.getCode());
        } else {
            comments = commentService.findCommentsByPostAndCommentStatusNot(post, CommentStatusEnum.RECYCLE.getCode());
        }
        //默认显示10条
        Integer size = 10;
        //获取每页评论条数
        if (StrUtil.isNotBlank(LyblogConst.OPTIONS.get(BlogPropertiesEnum.INDEX_COMMENTS.getProp()))) {
            size = Integer.parseInt(LyblogConst.OPTIONS.get(BlogPropertiesEnum.INDEX_COMMENTS.getProp()));
        }
        //评论分页
        ListPage<Comment> commentsPage = new ListPage<Comment>(CommentUtil.getComments(comments),cp, size);
        int[] rainbow = PageUtil.rainbow(cp, commentsPage.getTotalPage(), 3);
        model.addAttribute("is_page",true);
        model.addAttribute("post", post);
        model.addAttribute("comments", commentsPage);
        model.addAttribute("commentsCount", comments.size());
        model.addAttribute("rainbow", rainbow);
        postService.updatePostView(post);

        //如果设置了自定义模板，则渲染自定义模板
        if(StrUtil.isNotEmpty(post.getCustomTpl())){
            return this.render(post.getCustomTpl());
        }
        return this.render("page");
    }
}
