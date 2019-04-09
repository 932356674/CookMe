package com.gss;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

@SpringBootApplication
@ServletComponentScan(basePackages = "com.gss.config")//扫描servlet的相关注解
@MapperScan(basePackages = "com.gss.mapper")
public class CookmeApplication {

    public static void main(String[] args) {
        SpringApplication.run(CookmeApplication.class, args);
    }

}
