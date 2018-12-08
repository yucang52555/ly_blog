package com.lengyan.lyblog.web.controller.api;

import com.lengyan.lyblog.model.dto.Archive;
import com.lengyan.lyblog.model.dto.JsonResult;
import com.lengyan.lyblog.model.enums.ResponseStatusEnum;
import com.lengyan.lyblog.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <pre>
 *     文章归档API
 * </pre>
 *
 * @author : lengyan
 * @date : 2018/6/6
 */
@RestController
@RequestMapping(value = "/api/archives")
public class ApiArchivesController {

    @Autowired
    private PostService postService;

    /**
     * 根据年份归档
     *
     * @return JsonResult
     */
    @GetMapping(value = "/year")
    public JsonResult archivesYear() {
        List<Archive> archives = postService.findPostGroupByYear();
        if (null != archives && archives.size() > 0) {
            return new JsonResult(ResponseStatusEnum.SUCCESS.getCode(), ResponseStatusEnum.SUCCESS.getMsg(), archives);
        } else {
            return new JsonResult(ResponseStatusEnum.EMPTY.getCode(), ResponseStatusEnum.EMPTY.getMsg());
        }
    }

    /**
     * 根据月份归档
     *
     * @return JsonResult
     */
    @GetMapping(value = "/year/month")
    public JsonResult archivesYearAndMonth() {
        List<Archive> archives = postService.findPostGroupByYearAndMonth();
        if (null != archives && archives.size() > 0) {
            return new JsonResult(ResponseStatusEnum.SUCCESS.getCode(), ResponseStatusEnum.SUCCESS.getMsg(), archives);
        } else {
            return new JsonResult(ResponseStatusEnum.EMPTY.getCode(), ResponseStatusEnum.EMPTY.getMsg());
        }
    }
}
