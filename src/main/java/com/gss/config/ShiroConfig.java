package com.gss.config;

import com.qf.realm.UserRealm;
import org.apache.shiro.cache.ehcache.EhCacheManager;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.session.mgt.SessionManager;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.CookieRememberMeManager;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.Cookie;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.LinkedHashMap;

@Configuration //标记这个类是spring的配置文件
//@SpringBootConfiguration
public class ShiroConfig {

    @Bean(name ="sessionManager")
    public SessionManager sessionManager(){
        DefaultWebSessionManager sessionManager = new DefaultWebSessionManager();
        //禁止url地址栏拼接sessionID
        sessionManager.setSessionIdUrlRewritingEnabled(false);

        sessionManager.setGlobalSessionTimeout(1000*60*60);//session默认过期时间半小时
        //定时清除过期会话
        sessionManager.setSessionValidationSchedulerEnabled(true);

        return sessionManager;
    }

    //方法的参数相当于传入spring容器中创建的对象
    @Bean(value = "securityManager")
    public SecurityManager securityManager(UserRealm userRealm,SessionManager sessionManager){
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        securityManager.setSessionManager(sessionManager);
        securityManager.setRealm(userRealm);
        //缓存管理
        EhCacheManager ehCacheManager = new EhCacheManager();
        ehCacheManager.setCacheManagerConfigFile("classpath:ehcache.xml");

        securityManager.setCacheManager(ehCacheManager);
        CookieRememberMeManager rememberMeManager = new CookieRememberMeManager();
        Cookie cookie = rememberMeManager.getCookie();
        cookie.setMaxAge(60*60*24*30);
        securityManager.setRememberMeManager(rememberMeManager);
        return securityManager;
    }

    //shiro注解在spring容器中生效
    @Bean("lifecycleBeanPostProcessor")
    public LifecycleBeanPostProcessor lifecycleBeanPostProcessor(){
        return new LifecycleBeanPostProcessor();
    }

    @Bean
    public DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator(){
        DefaultAdvisorAutoProxyCreator advisorAutoProxyCreator = new DefaultAdvisorAutoProxyCreator();
        advisorAutoProxyCreator.setProxyTargetClass(true); //ciglib
        return advisorAutoProxyCreator;
    }

    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(SecurityManager securityManager){
        AuthorizationAttributeSourceAdvisor advisor = new AuthorizationAttributeSourceAdvisor();
        advisor.setSecurityManager(securityManager);
        return advisor;
    }

    @Bean("shiroFilter")
    public ShiroFilterFactoryBean shiroFilterFactoryBean(SecurityManager securityManager){
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        shiroFilterFactoryBean.setSecurityManager(securityManager);

        //设置登录页面
        shiroFilterFactoryBean.setLoginUrl("/login.html");
        //设置成功页面
        shiroFilterFactoryBean.setSuccessUrl("/index.html");
        //没有权限页面
        shiroFilterFactoryBean.setUnauthorizedUrl("/unauthorized.html");
        //LinkedHashMap能保证存取顺序
        LinkedHashMap<String,String>map = new LinkedHashMap<>();
        map.put("/public/**","anon");//静态js css
        //map.put("/json/**","anon");//假数据
        map.put("/captcha.jpg","anon");//验证码
        map.put("/sys/login","anon");
       // map.put("/sys/menu/*","perms[\"sys:menu\"]");
        map.put("/**","user");//选中记住我能访问的资源
        map.put("/**","authc");//登录后才能访问

        shiroFilterFactoryBean.setFilterChainDefinitionMap(map);

        return shiroFilterFactoryBean;

    }




}
