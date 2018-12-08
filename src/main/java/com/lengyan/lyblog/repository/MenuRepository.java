package com.lengyan.lyblog.repository;

import com.lengyan.lyblog.model.domain.Menu;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * <pre>
 *     菜单持久层
 * </pre>
 *
 * @author : lengyan
 * @date : 2018/1/24
 */
public interface MenuRepository extends JpaRepository<Menu, Long> {
}
