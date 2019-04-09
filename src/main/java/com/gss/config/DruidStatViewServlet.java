package com.gss.config;

import com.alibaba.druid.support.http.StatViewServlet;

import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;

/**
 *      public static final String PARAM_NAME_USERNAME = "loginUsername";
 *     public static final String PARAM_NAME_PASSWORD = "loginPassword";
 *     public static final String PARAM_NAME_ALLOW = "allow";
 *     public static final String PARAM_NAME_DENY = "deny";
 */
//配置登录视图的用户名和密码
@WebServlet(name ="statViewServlet",urlPatterns = "/druid/*",
initParams = {
        @WebInitParam(name = "loginUsername",value="aaa"),
        @WebInitParam(name = "loginPassword",value="aaa"),
        @WebInitParam(name = "allow",value = "127.0.0.1")
})
public class DruidStatViewServlet extends StatViewServlet {
}