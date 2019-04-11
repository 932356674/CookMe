package com.gss.utils;


import com.gss.entity.User;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;

public class ShiroUtils {
    // SecurityUtils.getSubject().getSession().setAttribute("code",text);

    public static Session getSession(){
        return SecurityUtils.getSubject().getSession();
    }
    public static void setAttribute(String k,String v){
        getSession().setAttribute(k,v);
    }
    public static Object getAttribute(String k){
        return getSession().getAttribute(k);
    }
    public static String getCaptcha(){
        return getAttribute("code")+"";
    }
    public static User getCurrentUser(){
        return (User) SecurityUtils.getSubject().getPrincipal();
    }

    public static void logout(){
        SecurityUtils.getSubject().logout();
    }

    public static int getUserId(){
        return getCurrentUser().getUsId();
    }
}
