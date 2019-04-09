package com.gss.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

@Configuration//加载配置文件
@ImportResource(locations = "classpath:applicationContext-monitor.xml")
public class DruidStatInterceptor {
}
