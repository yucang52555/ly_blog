package com.lengyan.lyblog.repository;

import com.lengyan.lyblog.model.domain.Options;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * <pre>
 *     系统设置持久层
 * </pre>
 *
 * @author : lengyan
 * @date : 2017/11/14
 */
public interface OptionsRepository extends JpaRepository<Options, Long> {

    /**
     * 根据key查询单个option
     *
     * @param key key
     * @return Options
     */
    Options findOptionsByOptionName(String key);
}
