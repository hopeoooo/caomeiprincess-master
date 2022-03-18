package com.caomei.common.config;

import com.caomei.Realm.AuthRealm;
import com.warrenstrange.googleauth.GoogleAuthenticator;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.session.mgt.SessionManager;
import org.apache.shiro.session.mgt.eis.SessionDAO;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @Author yunfd
 */
//@Configuration
public class ShiroConfiguration {



    //这里就是会话管理的操作类
    @Bean
    public SessionDAO sessionDAO() {
        return new RedisSessionDao();
    }

    //这里需要设置一个cookie的名称  原因就是会跟原来的session的id值重复的
    @Bean
    public SimpleCookie simpleCookie() {
        SimpleCookie simpleCookie = new SimpleCookie("REDISSESSION");
        return simpleCookie;
    }

    @Bean
    public JedisCacheManager jedisCacheManager() {
        return new JedisCacheManager();
    }


    @Bean(name = "sessionManager")
    public DefaultWebSessionManager configWebSessionManager(){
        CustomSessionManager manager = new CustomSessionManager();
        manager.setSessionIdCookie(simpleCookie());
        manager.setSessionDAO(sessionDAO());// 设置SessionDao
        manager.setDeleteInvalidSessions(true);// 删除过期的session
        manager.setSessionValidationSchedulerEnabled(true);// 是否定时检查session

        return manager;
    }

    @Bean
    public HashedCredentialsMatcher hashedCredentialsMatcher() {
        HashedCredentialsMatcher hashedCredentialsMatcher = new HashedCredentialsMatcher();
        hashedCredentialsMatcher.setHashAlgorithmName("MD5");
        hashedCredentialsMatcher.setHashIterations(2);
        return hashedCredentialsMatcher;
    }

    @Bean
    public Realm userRealm(HashedCredentialsMatcher hashedCredentialsMatcher) {
        AuthRealm authRealm = new AuthRealm();
        authRealm.setCredentialsMatcher(hashedCredentialsMatcher);
        return authRealm;
    }

    @Bean(name = "securityManager")
    public SecurityManager securityManager(Realm authRealm, SessionManager sessionManager) {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        securityManager.setRealm(authRealm);
        // 注入缓存管理器;
        securityManager.setCacheManager(jedisCacheManager());
        // 会话管理
        securityManager.setSessionManager(sessionManager);
        return securityManager;
    }

    @Bean(name = "lifecycleBeanPostProcessor")
    public LifecycleBeanPostProcessor lifecycleBeanPostProcessor() {
        return new LifecycleBeanPostProcessor();
    }

    @Bean
    public DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator() {
        DefaultAdvisorAutoProxyCreator proxyCreator = new DefaultAdvisorAutoProxyCreator();
        proxyCreator.setProxyTargetClass(true);
        return proxyCreator;
    }

    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(SecurityManager securityManager) {
        AuthorizationAttributeSourceAdvisor advisor = new AuthorizationAttributeSourceAdvisor();
        advisor.setSecurityManager(securityManager);
        return advisor;
    }

    @Bean
    public ShiroFilterFactoryBean shiroFilterFactoryBean(SecurityManager securityManager) {
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();

        shiroFilterFactoryBean.setSecurityManager(securityManager);

        shiroFilterFactoryBean.setLoginUrl("/login");

        Map<String, String> filterChainDefinitionMap = new LinkedHashMap<>();
        filterChainDefinitionMap.put("/admin/css/**", "anon");
        filterChainDefinitionMap.put("/admin/img/**", "anon");
        filterChainDefinitionMap.put("/admin/js/**", "anon");
        filterChainDefinitionMap.put("/lib/**", "anon");
        filterChainDefinitionMap.put("/public/admin/**", "anon");
        filterChainDefinitionMap.put("/public/site/**", "anon");
        filterChainDefinitionMap.put("/site/**", "anon");
        filterChainDefinitionMap.put("/live2d/**", "anon");

        filterChainDefinitionMap.put("/login", "anon");
        filterChainDefinitionMap.put("/admin/login", "anon");

        filterChainDefinitionMap.put("/admin/**", "user");

        filterChainDefinitionMap.put("/test/**", "anon");

        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);

        return shiroFilterFactoryBean;
    }

    @Bean
    public static GoogleAuthenticator getGoogleAuthenticator(){
        GoogleAuthenticator googleAuthenticator = new GoogleAuthenticator();
        return googleAuthenticator;
    }
}


