package com.gss.config;

import com.alibaba.druid.pool.DruidDataSource;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration//声明为配置文件
public class DruidConfig {

    @Bean(name = "druidDatasource")
    //加载配置文件中以spring.datasource开头的配置
    @ConfigurationProperties(value = "spring.datasource")
    public DataSource dataSource(){
        System.out.println("----->加载数据源！");
        DruidDataSource druidDataSource = new DruidDataSource();
        return druidDataSource;
    }
}
