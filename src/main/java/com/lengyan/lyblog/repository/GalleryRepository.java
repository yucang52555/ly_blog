package com.lengyan.lyblog.repository;

import com.lengyan.lyblog.model.domain.Gallery;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * <pre>
 *     图库持久层
 * </pre>
 *
 * @author : lengyan
 * @date : 2018/2/26
 */
public interface GalleryRepository extends JpaRepository<Gallery, Long> {
}
