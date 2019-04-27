package com.gss.realm;

import com.gss.entity.User;
import com.gss.service.SysUserService;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component(value = "userRealm")
public class UserRealm extends AuthorizingRealm {
    @Resource
    private SysUserService sysUserService;
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        System.out.println("---------------->授权");
        return null;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        System.out.println("--------------->认证");
        UsernamePasswordToken u= (UsernamePasswordToken) token;
        String uphone=u.getUsername();

        String upass=new String(u.getPassword());

        User user=sysUserService.login(Long.valueOf(uphone));

        if(user==null){
            throw new UnknownAccountException("手机号未注册");
        }

        SimpleAuthenticationInfo info=new SimpleAuthenticationInfo(user,upass,this.getName());

        return info;
    }
}
