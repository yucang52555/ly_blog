package com.lengyan.lyblog;

import org.apache.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.ApplicationContext;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * <pre>
 *     Lyblog run!
 * </pre>
 *
 * @author : lengyan
 * @date : 2017/11/14
 */
@SpringBootApplication
@EnableCaching
@EnableAutoConfiguration
public class LyblogApplication {
    private static final Logger log = Logger.getLogger(LyblogApplication.class);

    public static void main(String[] args) {
        ApplicationContext context = SpringApplication.run(LyblogApplication.class, args);
        String serverPort = context.getEnvironment().getProperty("server.port");
        log.info("ly_blog started at http://localhost:" + serverPort);
    }
}
