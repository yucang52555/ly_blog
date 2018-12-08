package com.lengyan.lyblog;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.ApplicationContext;

/**
 * <pre>
 *     Lyblog run!
 * </pre>
 *
 * @author : lengyan
 * @date : 2017/11/14
 */
@Slf4j
@SpringBootApplication
@EnableCaching
public class LyblogApplication {
    public static void main(String[] args) {
        ApplicationContext context = SpringApplication.run(LyblogApplication.class, args);
        String serverPort = context.getEnvironment().getProperty("server.port");
        log.info("ly_blog started at http://localhost:" + serverPort);
    }
}
