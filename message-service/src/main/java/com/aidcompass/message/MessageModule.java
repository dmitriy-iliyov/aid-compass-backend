package com.aidcompass.message;

import com.aidcompass.common.mapper.ExceptionMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;


@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
@ComponentScan(basePackageClasses = {ExceptionMapper.class})
public class MessageModule {
    public static void main(String[] args) {
        SpringApplication.run(MessageModule.class, args);
    }
}