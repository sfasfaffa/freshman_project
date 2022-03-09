package com.example.bigwork2_14.config;


import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.LinkedHashMap;
import java.util.Map;
/*
<form th:action="@{/user/doadd}">
    <p>用户名:<input type="text" name="username"></p>
    <P>密码:<input type="text" name="password"></P>
    <P>身份:<input type="text" name="perms"></P>
    <p><input type="submit"></p>
</form>
 */


@Configuration//配置
public class ShiroConfig {
    //ShiroFilterFactoryBean：1
    @Bean
    public ShiroFilterFactoryBean getShiroFilterFactoryBean(@Qualifier("securityManager")DefaultWebSecurityManager defaultWebSecurityManager){
        ShiroFilterFactoryBean bean = new ShiroFilterFactoryBean();
        //设置安全管理器
        bean.setSecurityManager(defaultWebSecurityManager);
        //添加shiro的内置过滤器
        /*
            anon:无需认证就可以访问
            authc:必须认证了才可以访问
            user:必须拥有 记住我 功能才能用
            perms：拥有对某个资源的权限才可以访问
            role：拥有某个角色权限才可以访问
         */
        Map<String,String> filterMap=new LinkedHashMap<>();
        //授权，正常情况下，没有授权会跳转到未授权页面

        filterMap.put("/user/*","anon");//此为通配符
        bean.setFilterChainDefinitionMap(filterMap);//设置登录请求
        bean.setLoginUrl("/toLogin");//未授权页面
        bean.setUnauthorizedUrl("/noauth");
        filterMap.put("/logout","logout");

        //管理员的过滤器
        filterMap.put("/administrator","perms[administrator]");
        filterMap.put("/administrator/findallcalzz","perms[administrator]");

        //老师的过滤器
        filterMap.put("/teacher","perms[teacher]");
        filterMap.put("/teacher/addclazz","perms[teacher]");
        filterMap.put("/teacher/myclazzes","perms[teacher]");

        //学生的过滤器
        filterMap.put("/student","perms[student]");
        filterMap.put("/student/dropclazz","perms[student]");
        filterMap.put("/chooseclazz","perms[student]");
        filterMap.put("/student/lookover","perms[student]");
        return bean;
    }
    //DafaultWebSecurityManager：2
    @Bean(name = "securityManager")
    public DefaultWebSecurityManager getDefaultWebSecurityManager(@Qualifier("userRealm") UserRealm userRealm){
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();//关联UserRealm
        securityManager.setRealm(userRealm);
        return securityManager;
    }
    //ShiroFilterFactoryBean：3
    // 创建realm（领域）对象，需要自定义类，用户认证放在此处
    @Bean
    public UserRealm userRealm(){
        return new UserRealm();
    }
}
