package com.lengyan.lyblog.web.controller.api;

import com.lengyan.lyblog.model.domain.Gallery;
import com.lengyan.lyblog.model.dto.JsonResult;
import com.lengyan.lyblog.model.enums.ResponseStatusEnum;
import com.lengyan.lyblog.service.GalleryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

/**
 * <pre>
 *     图库API
 * </pre>
 *
 * @author : lengyan
 * @date : 2018/6/6
 */
@RestController
@RequestMapping(value = "/api/galleries")
public class ApiGalleryController {

    @Autowired
    private GalleryService galleryService;

    /**
     * 获取所有图片
     *
     * @return JsonResult
     */
    @GetMapping
    public JsonResult galleries() {
        List<Gallery> galleries = galleryService.findAllGalleries();
        if (null != galleries && galleries.size() > 0) {
            return new JsonResult(ResponseStatusEnum.SUCCESS.getCode(), ResponseStatusEnum.SUCCESS.getMsg(), galleries);
        } else {
            return new JsonResult(ResponseStatusEnum.EMPTY.getCode(), ResponseStatusEnum.EMPTY.getMsg());
        }
    }

    /**
     * 获取单张图片的信息
     *
     * @param id id
     * @return JsonResult
     */
    @GetMapping(value = "/{id}")
    public JsonResult galleries(@PathVariable("id") Long id) {
        Optional<Gallery> gallery = galleryService.findByGalleryId(id);
        if (gallery.isPresent()) {
            return new JsonResult(ResponseStatusEnum.SUCCESS.getCode(), ResponseStatusEnum.SUCCESS.getMsg(), gallery.get());
        } else {
            return new JsonResult(ResponseStatusEnum.NOTFOUND.getCode(), ResponseStatusEnum.NOTFOUND.getMsg());
        }
    }
}
