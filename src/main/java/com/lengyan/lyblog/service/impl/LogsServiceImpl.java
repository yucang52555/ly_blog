package com.lengyan.lyblog.service.impl;

import com.lengyan.lyblog.model.domain.Logs;
import com.lengyan.lyblog.repository.LogsRepository;
import com.lengyan.lyblog.service.LogsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * <pre>
 *     日志业务逻辑实现类
 * </pre>
 *
 * @author : lengyan
 * @date : 2018/1/19
 */
@Service
public class LogsServiceImpl implements LogsService {

    @Autowired
    private LogsRepository logsRepository;

    /**
     * 保存日志
     *
     * @param logs logs
     * @return Logs
     */
    @Override
    public Logs saveByLogs(Logs logs) {
        return logsRepository.save(logs);
    }

    /**
     * 根据编号移除
     *
     * @param logsId logsId
     */
    @Override
    public void removeByLogsId(Long logsId) {
        Optional<Logs> logs = this.findLogsByLogsId(logsId);
        logsRepository.delete(logs.get());
    }

    /**
     * 移除所有日志
     */
    @Override
    public void removeAllLogs() {
        logsRepository.deleteAll();
    }

    /**
     * 查询所有日志并分页
     *
     * @param pageable pageable
     * @return Page
     */
    @Override
    public Page<Logs> findAllLogs(Pageable pageable) {
        return logsRepository.findAll(pageable);
    }

    /**
     * 查询最新的五条日志
     *
     * @return List
     */
    @Override
    public List<Logs> findLogsLatest() {
        return logsRepository.findTopFive();
    }

    /**
     * 根据编号查询
     *
     * @param logsId logsId
     * @return Optional
     */
    @Override
    public Optional<Logs> findLogsByLogsId(Long logsId) {
        return logsRepository.findById(logsId);
    }
}
