package com.cp.shiro.shirobasic;

import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.spring.web.config.DefaultShiroFilterChainDefinition;
import org.apache.shiro.spring.web.config.ShiroFilterChainDefinition;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author ChengPeng
 * @create 2020-03-30 15:25
 * 在这里进行 Shiro 的配置
 * Shiro 的配置主要配置 3 个 Bean 。
 *
 *  1. 首先需要提供一个 Realm 的实例
 *  2. 需要配置一个 SecurityManager，在 SecurityManager 中配置 Realm
 *  3. 配置一个 ShiroFilterFactoryBean ，在 ShiroFilterFactoryBean 中指定路径拦截规则等
 */
@Configuration
public class ShiroConfig {
    @Bean
    MyRealm myRealm(){
        return new MyRealm();
    }

    @Bean
    SecurityManager securityManager(){
        DefaultWebSecurityManager manager = new DefaultWebSecurityManager();
        manager.setRealm(myRealm());
        return manager;
    }

    @Bean
    ShiroFilterFactoryBean shiroFilterFactoryBean(){
        ShiroFilterFactoryBean bean = new ShiroFilterFactoryBean();
        //指定SecurutyManager
        bean.setSecurityManager(securityManager());
        //登录页面
        bean.setLoginUrl("/login");
        //登录成功页面
        bean.setSuccessUrl("/index");
        //访问未授权路径时跳转的页面
        bean.setUnauthorizedUrl("/unauthorizedurl");
        //配置路径拦截规则，注意要有序
        Map<String,String> map = new LinkedHashMap<>();
        map.put("/doLogin","anon");
        map.put("/**","authc");
        bean.setFilterChainDefinitionMap(map);
        return bean;
    }

    /*@Bean
    ShiroFilterChainDefinition shiroFilterChainDefinition() {
        DefaultShiroFilterChainDefinition definition = new DefaultShiroFilterChainDefinition();
        definition.addPathDefinition("/doLogin", "anon");
        definition.addPathDefinition("/**", "authc");
        return definition;
    }*/
}
