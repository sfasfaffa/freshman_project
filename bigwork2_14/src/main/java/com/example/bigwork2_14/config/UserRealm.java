package com.example.bigwork2_14.config;



import com.example.bigwork2_14.dao.UserDao;
import com.example.bigwork2_14.pojo.User;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;

public class UserRealm extends AuthorizingRealm {

    @Autowired
    UserDao userDao;
    //授权
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        System.out.println("执行了=》授权doGetAuthorizationInfo");
        //SimpleAuthenticationInfo
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        //拿到当前用户对象
        Subject subject = SecurityUtils.getSubject();
        User currentUser = (User)subject.getPrincipal();
        //设置当前用户权限
        info.addStringPermission(currentUser.getPerms());
        return info;
    }
    //认证
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        System.out.println("执行了=》授权doGetAuthenticationInfo");
        //用户名，密码：数据中取
        UsernamePasswordToken userToken = (UsernamePasswordToken)token;
        User user=userDao.findByName(userToken.getUsername());
        if(user==null){//没有这个人
            return null;//
        }
        //密码认证，shiro做
        return new SimpleAuthenticationInfo(user,user.getPwd(),"");
    }
}
