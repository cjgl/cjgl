package cn.fy.cjgl.configuration;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.credential.SimpleCredentialsMatcher;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.session.InvalidSessionException;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import cn.fy.cjgl.entity.Rolepermission;
import cn.fy.cjgl.entity.User;
import cn.fy.cjgl.entity.Userrole;
import cn.fy.cjgl.service.IRolepermissionService;
import cn.fy.cjgl.service.IUserService;
import cn.fy.cjgl.service.IUserroleService;
import cn.fy.cjgl.util.Consts;

public class ShiroRealm extends AuthorizingRealm {
	private static final Logger log = LoggerFactory.getLogger(ShiroRealm.class);
	
	@Autowired
    private IUserService userService;
	
	@Autowired
    private IUserroleService userroleService;
	
	@Autowired
    private IRolepermissionService rolepermissionService;

	public ShiroRealm() {
		setCredentialsMatcher(new SimpleCredentialsMatcher());
	}
	
    @Override
    public String getName() {
        return "MyRealm";
    }

    //授权方法
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {

        String loginname = (String) principalCollection.getPrimaryPrincipal();
        log.debug(loginname);
        
        User user = new User();
        user.setLoginname(loginname);
        user = this.userService.getUserByLoginname(user);
        
        Userrole userrole = new Userrole();
        userrole.setUserid(user.getUserid());
        userrole.setDelflag("0");
        List<Userrole> userroleList = this.userroleService.queryUserroleListByUserid(userrole);
        
        Set<String> roleSet = new HashSet<String>();
        Set<String> permissionSet = new HashSet<String>();
        
        if(userroleList.size() > 0) {
        	List<Integer> roleidList = new ArrayList<Integer>();
            for(Userrole e : userroleList) {
            	roleidList.add(e.getRoleid());
            }
            
            Rolepermission rolepermission = new Rolepermission();
            rolepermission.setRoleidList(roleidList);
            List<Rolepermission> rolepermissionList = this.rolepermissionService.queryRolepermissionListByRoleidList(rolepermission);
            
            for(Rolepermission e : rolepermissionList) {
            	permissionSet.add(e.getPermissiontag());
            }
        } 
        
        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
        simpleAuthorizationInfo.addRoles(roleSet);
        simpleAuthorizationInfo.addStringPermissions(permissionSet);
        return simpleAuthorizationInfo;
    }

    //认证方法
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {

        //通过token获取用户账号
        String loginname = (String)authenticationToken.getPrincipal();
        log.info(loginname);
        
        User user = new User();
        user.setLoginname(loginname);
        user = this.userService.getUserByLoginname(user);
        
        if(user == null) {
        	throw new IncorrectCredentialsException("登录名不存在");
        }
        
        SimpleAuthenticationInfo simpleAuthenticationInfo = new SimpleAuthenticationInfo(loginname, user.getPwd().toCharArray(), getName());
            
        return simpleAuthenticationInfo;
    }
    
    @Override
	protected void assertCredentialsMatch(AuthenticationToken token, AuthenticationInfo info) throws AuthenticationException {

    	String submittedPassword = new String((char[]) token.getCredentials());
		String storedCredentials = new String((char[]) info.getCredentials());
		
		if (!submittedPassword.equals(storedCredentials)) {
			throw new IncorrectCredentialsException("登录名或密码错误");
		}
		
		String loginname = (String) token.getPrincipal();
		User user = new User();
        user.setLoginname(loginname);
        user = this.userService.getUserByLoginname(user);
        
        if("1".equals(user.getSso())) {
        	clearSameUserSession(loginname);
        }
        
		Session session = getSession();
		session.setAttribute(Consts.S_USER, user);

	}
    
    /**
                *  单一登陆处理 相同用户只能登陆一次
     */
	public void clearSameUserSession(String loginname) {

		// 处理session
		DefaultWebSecurityManager securityManager = (DefaultWebSecurityManager) SecurityUtils.getSecurityManager();
		DefaultWebSessionManager sessionManager = (DefaultWebSessionManager) securityManager.getSessionManager();
		// 获取当前已登录的用户session列表
		Collection<Session> sessions = sessionManager.getSessionDAO().getActiveSessions();

		Session session = getSession();
		
		User user = null;
		for (Session s : sessions) {
			// 清除该用户以前登录时保存的session
			user = (User) s.getAttribute(Consts.S_USER);
			// 用户名相同并且sessionId不同时 清除
			if (user != null && loginname.equals(user.getLoginname()) && !(session.getId().equals(s.getId()))) {
				sessionManager.getSessionDAO().delete(s);
			}
		}
	}
	
	/**
	 * 获取session
	 */
	private Session getSession() {
		try {
			Subject subject = SecurityUtils.getSubject();
			Session session = subject.getSession(false);
			if (session == null) {
				session = subject.getSession();
			}
			if (session != null) {
				return session;
			}
		} catch (InvalidSessionException e) {

		}
		return null;
	}
    
}
