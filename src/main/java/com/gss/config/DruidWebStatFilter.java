package com.gss.config;

import com.alibaba.druid.support.http.WebStatFilter;

import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebInitParam;

//过滤器
@WebFilter(filterName = "statfilter",urlPatterns = "/*",
initParams = {
        //排除以下文件
        @WebInitParam(name = "exclusions",value = "*.js,*.css,*.jpg,*.png,*.gif,*.bmp,/druid/*")
})
public class DruidWebStatFilter extends WebStatFilter {
}
