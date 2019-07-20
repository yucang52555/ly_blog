package com.lengyan.lyblog.web.controller.front;

import com.lengyan.lyblog.model.domain.Post;
import com.lengyan.lyblog.model.dto.LyblogConst;
import com.lengyan.lyblog.model.enums.BlogPropertiesEnum;
import com.lengyan.lyblog.model.enums.PostTypeEnum;
import com.lengyan.lyblog.service.PostService;
import cn.hutool.core.util.StrUtil;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import springfox.documentation.annotations.ApiIgnore;

import java.util.List;

/**
 * <pre>
 *     sitemap，rss页面控制器
 * </pre>
 *
 * @author : lengyan
 * @date : 2018/4/26
 */
@Controller
@Api(tags = "sitemap，rss页面控制器")
public class FrontOthersController {

    @Autowired
    private PostService postService;

    /**
     * 获取文章rss
     *
     * @return rss
     */
    @GetMapping(value = {"feed", "feed.xml", "atom", "atom.xml"}, produces = "application/xml;charset=UTF-8")
    @ResponseBody
    public String feed() {
        String rssPosts = LyblogConst.OPTIONS.get(BlogPropertiesEnum.RSS_POSTS.getProp());
        if (StrUtil.isBlank(rssPosts)) {
            rssPosts = "20";
        }
        //获取文章列表并根据时间排序
        Sort sort = new Sort(Sort.Direction.DESC, "postDate");
        Pageable pageable = PageRequest.of(0, Integer.parseInt(rssPosts), sort);
        Page<Post> postsPage = postService.findPostByStatus(0, PostTypeEnum.POST_TYPE_POST.getDesc(), pageable);
        List<Post> posts = postsPage.getContent();
        return postService.buildRss(posts);
    }

    /**
     * 获取sitemap
     *
     * @return sitemap
     */
    @GetMapping(value = {"sitemap", "sitemap.xml"}, produces = "application/xml;charset=UTF-8")
    @ResponseBody
    public String siteMap() {
        //获取文章列表并根据时间排序
        Sort sort = new Sort(Sort.Direction.DESC, "postDate");
        Pageable pageable = PageRequest.of(0, 999, sort);
        Page<Post> postsPage = postService.findPostByStatus(0, PostTypeEnum.POST_TYPE_POST.getDesc(), pageable);
        List<Post> posts = postsPage.getContent();
        return postService.buildSiteMap(posts);
    }
}
