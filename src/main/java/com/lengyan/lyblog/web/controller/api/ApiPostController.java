package com.lengyan.lyblog.web.controller.api;

import com.lengyan.lyblog.model.domain.Post;
import com.lengyan.lyblog.model.dto.LyblogConst;
import com.lengyan.lyblog.model.dto.JsonResult;
import com.lengyan.lyblog.model.enums.BlogPropertiesEnum;
import com.lengyan.lyblog.model.enums.PostStatusEnum;
import com.lengyan.lyblog.model.enums.PostTypeEnum;
import com.lengyan.lyblog.model.enums.ResponseStatusEnum;
import com.lengyan.lyblog.service.PostService;
import cn.hutool.core.util.StrUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <pre>
 *     文章API
 * </pre>
 *
 * @author : lengyan
 * @date : 2018/6/6
 */
@RestController
@RequestMapping(value = "/api/posts")
public class ApiPostController {

    @Autowired
    private PostService postService;

    /**
     * 获取文章列表 分页
     *
     * @param page 页码
     * @return JsonResult
     */
    @GetMapping(value = "/page/{page}")
    public JsonResult posts(@PathVariable(value = "page") Integer page) {
        Sort sort = new Sort(Sort.Direction.DESC, "postDate");
        Integer size = 10;
        if (StrUtil.isNotBlank(LyblogConst.OPTIONS.get(BlogPropertiesEnum.INDEX_POSTS.getProp()))) {
            size = Integer.parseInt(LyblogConst.OPTIONS.get(BlogPropertiesEnum.INDEX_POSTS.getProp()));
        }
        Pageable pageable = PageRequest.of(page - 1, size, sort);
        Page<Post> posts = postService.findPostByStatus(PostStatusEnum.PUBLISHED.getCode(), PostTypeEnum.POST_TYPE_POST.getDesc(), pageable);
        if (null == posts) {
            return new JsonResult(ResponseStatusEnum.EMPTY.getCode(), ResponseStatusEnum.EMPTY.getMsg());
        }
        return new JsonResult(ResponseStatusEnum.SUCCESS.getCode(), ResponseStatusEnum.SUCCESS.getMsg(), posts);
    }

    @GetMapping(value = "/hot")
    public JsonResult hotPosts() {
        List<Post> posts = postService.hotPosts();
        if (null != posts && posts.size() > 0) {
            return new JsonResult(ResponseStatusEnum.SUCCESS.getCode(), ResponseStatusEnum.SUCCESS.getMsg(), posts);
        } else {
            return new JsonResult(ResponseStatusEnum.EMPTY.getCode(), ResponseStatusEnum.EMPTY.getMsg());
        }
    }

    /**
     * 获取单个文章信息
     *
     * @param postId 文章编号
     * @return JsonResult
     */
    @GetMapping(value = "/{postId}")
    public JsonResult posts(@PathVariable(value = "postId") Long postId) {
        Post post = postService.findByPostId(postId, PostTypeEnum.POST_TYPE_POST.getDesc());
        if (null != post) {
            return new JsonResult(ResponseStatusEnum.SUCCESS.getCode(), ResponseStatusEnum.SUCCESS.getMsg(), post);
        } else {
            return new JsonResult(ResponseStatusEnum.NOTFOUND.getCode(), ResponseStatusEnum.NOTFOUND.getMsg());
        }
    }
}
