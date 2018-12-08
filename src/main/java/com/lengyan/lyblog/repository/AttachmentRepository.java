package com.lengyan.lyblog.repository;

import com.lengyan.lyblog.model.domain.Attachment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * <pre>
 *     附件持久层
 * </pre>
 *
 * @author : lengyan
 * @date : 2018/1/10
 */
public interface AttachmentRepository extends JpaRepository<Attachment, Long> {
}
