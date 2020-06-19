package cn.fy.cjgl.configuration;

import java.util.LinkedHashMap;

import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ShiroConfiguration {

	@Bean(name = "shiroFilter")
	public ShiroFilterFactoryBean shiroFilter(@Qualifier("securityManager") DefaultWebSecurityManager manager) {
		ShiroFilterFactoryBean bean = new ShiroFilterFactoryBean();
		bean.setSecurityManager(manager);
		// 配置登录的url和登录成功的url以及验证失败的url
		bean.setLoginUrl("/relogin");
		bean.setSuccessUrl("/main");
		bean.setUnauthorizedUrl("/unauthorized");
		// 配置访问权限
		LinkedHashMap<String, String> filterChainDefinitionMap = new LinkedHashMap<String, String>();
		filterChainDefinitionMap.put("/validateCode", "anon");
		filterChainDefinitionMap.put("/index", "anon");
		filterChainDefinitionMap.put("/login", "anon");
		filterChainDefinitionMap.put("/css/**", "anon");
		filterChainDefinitionMap.put("/img/**", "anon");
		filterChainDefinitionMap.put("/js/**", "anon");
		filterChainDefinitionMap.put("/**", "authc");
		bean.setFilterChainDefinitionMap(filterChainDefinitionMap);
		return bean;
	}

	// 配置核心安全事务管理器
	@Bean(name = "securityManager")
	public DefaultWebSecurityManager securityManager() {
		DefaultWebSecurityManager manager = new DefaultWebSecurityManager(myRealm());
		manager.setSessionManager(sessionManager());
		return manager;
	}

	@Bean
	public ShiroRealm myRealm() {
		ShiroRealm myRealm = new ShiroRealm();
		return myRealm;
	}

	
	public DefaultWebSessionManager sessionManager() {
		// 配置默认的sesssion管理器
		DefaultWebSessionManager sessionManager = new DefaultWebSessionManager();
		sessionManager.setGlobalSessionTimeout(30 * 60 * 1000);
		return sessionManager;
	}

}
