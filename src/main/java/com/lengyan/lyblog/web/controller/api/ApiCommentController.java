package com.lengyan.lyblog.web.controller.api;

import com.lengyan.lyblog.service.CommentService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <pre>
 *     评论API
 * </pre>
 *
 * @author : lengyan
 * @date : 2018/6/6
 */
@RestController
@RequestMapping(value = "/api/comments")
@Api(tags = "API-评论控制器")
public class ApiCommentController {

    @Autowired
    private CommentService commentService;
}
