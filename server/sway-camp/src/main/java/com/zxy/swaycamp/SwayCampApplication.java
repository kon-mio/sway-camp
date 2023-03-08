package com.zxy.swaycamp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * 项目启动类
 *
 * @author Xinyuan Zhao
 * @since 2023/1/23
 */
@EnableAsync
@EnableTransactionManagement
@SpringBootApplication
public class SwayCampApplication {

    public static void main(String[] args) {
        SpringApplication.run(SwayCampApplication.class, args);
    }

}
