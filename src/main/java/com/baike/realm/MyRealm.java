//package com.baike.realm;
//
//import com.baike.model.User;
//import com.baike.service.UserService;
//import org.apache.shiro.SecurityUtils;
//import org.apache.shiro.authc.AuthenticationException;
//import org.apache.shiro.authc.AuthenticationInfo;
//import org.apache.shiro.authc.AuthenticationToken;
//import org.apache.shiro.authc.SimpleAuthenticationInfo;
//import org.apache.shiro.authz.AuthorizationInfo;
//import org.apache.shiro.realm.AuthorizingRealm;
//import org.apache.shiro.subject.PrincipalCollection;
//
//import javax.annotation.Resource;
//
//public class MyRealm extends AuthorizingRealm{
//
//	@Resource
//	private UserService userService;
//
//	/**
//	 * 为当限前登录的用户授予角色和权
//	 */
//	@Override
//	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
//		return null;
//	}
//
//	/**
//	 * 验证当前登录的用户
//	 */
//	@Override
//	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
//		String userName=(String)token.getPrincipal();
//		User  user=userService.findByName(userName);
//		if(user!=null){
//			SecurityUtils.getSubject().getSession().setAttribute("currentUser", user); // 当前用户信息存到session中
//
//			AuthenticationInfo authcInfo=new SimpleAuthenticationInfo(user.getUserName(),user.getPassword(),"xx");
//			return authcInfo;
//		}else{
//			return null;
//		}
//	}
//
//}
