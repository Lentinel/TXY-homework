package com.edu;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.transaction.TransactionAutoConfiguration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication(exclude = {TransactionAutoConfiguration.class})
@EnableTransactionManagement //开启注解方式的事务管理
@Slf4j
public class EduApplication {
    public static void main(String[] args) {
        SpringApplication.run(EduApplication.class, args);
        log.info("服务启动");
    }
}
