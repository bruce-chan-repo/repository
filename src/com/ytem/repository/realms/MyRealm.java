package com.ytem.repository.realms;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;

import com.ytem.repository.bean.User;
import com.ytem.repository.common.Const;
import com.ytem.repository.service.UserService;


/**
 * 自定义认证和授权的Realm
 * @author 陈康敬💪
 * @date 2018年5月6日下午7:40:19
 * @description
 */
public class MyRealm extends AuthorizingRealm {
	private final Logger logger = Logger.getLogger(MyRealm.class);
	
	@Autowired
	private UserService userService;
	
	
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection arg0) {
		
		return null;
	}

	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
		String opration = Const.LOGGER_PREFIX_DEBUG + "THREADID=" + Thread.currentThread().getId() + ".|用户认证.|";
		logger.debug(opration + ".|");
		
		UsernamePasswordToken userToken = (UsernamePasswordToken) token;
		
		// 获取用户名
		String username = userToken.getUsername();
		
		// 调用数据库查询
		User user = userService.selectUserByUsername(username, null);
		if (user == null) {
			throw new UnknownAccountException("该用户不存在");
		}
		
		// 加密盐值
		ByteSource salt = ByteSource.Util.bytes(user.getUsername());
		
		// 比对密码
		SimpleHash simpleHash = new SimpleHash(Const.HASH_ALGORITHMNAME, userToken.getCredentials(), salt, Const.HASH_ITERATIONS);
		
		if (!StringUtils.equals(user.getPassword(), simpleHash.toString())) {
			throw new IncorrectCredentialsException("用户密码错误");
		}
		
		// 根据用户信息构建AuthenticationInfo
		String realmName = this.getName();
		SimpleAuthenticationInfo userInfo = new SimpleAuthenticationInfo(username, user.getPassword(), salt, realmName);
		return userInfo;
	}
	
	public static void main(String[] args) {
		// 加密盐值
		ByteSource salt = ByteSource.Util.bytes("admin");
		SimpleHash simpleHash = new SimpleHash(Const.HASH_ALGORITHMNAME, "123456", salt, Const.HASH_ITERATIONS);
		System.out.println(simpleHash.toString());
	}
}
