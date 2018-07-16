package com.ytem.repository.service.impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.ytem.repository.bean.User;
import com.ytem.repository.common.Const;
import com.ytem.repository.common.PageInfoExt;
import com.ytem.repository.dao.UserMapper;
import com.ytem.repository.service.UserService;


@Service("userService")
public class UserServiceImpl implements UserService {
	private final Logger logger = Logger.getLogger(UserServiceImpl.class);
	
	@Autowired
	private UserMapper userMapper;
	
	@Override
	public User selectUserByUsername(String username, Integer id) {
		String opreation = Const.LOGGER_PREFIX_DEBUG + "THREADID = " + Thread.currentThread().getId() + ".|根据用户名获取用户信息.|用户名==>" + username;
		logger.debug(opreation + ".|开始");
		User user = userMapper.selectByUsername(username, id);
		logger.debug(opreation + ".|结束");
		return user;
	}
	
	@Override
	public PageInfoExt<User> getUsers(Integer pageNum, Integer pageSize) {
		String opreation = Const.LOGGER_PREFIX_DEBUG + "THREADID = " + Thread.currentThread().getId() + ".|获取所有用户信息.|";
		logger.debug(opreation + ".|开始");
		
		PageHelper.startPage(pageNum, pageSize);
		List<User> userList = userMapper.selectUserList();
		
		PageInfoExt<User> pageResult = new PageInfoExt<>(userList);
		
		logger.debug(opreation + ".|结束");
		return pageResult;
	}

	@Override
	public int addUser(User user) {
		String opreation = Const.LOGGER_PREFIX_DEBUG + "THREADID = " + Thread.currentThread().getId() + ".|添加用户.|";
		logger.debug(opreation + ".|开始");
		
		// 密码加密的盐值.
		ByteSource salt = ByteSource.Util.bytes(user.getUsername());
		
		// 对密码进行加密.
		SimpleHash simpleHash = new SimpleHash(Const.HASH_ALGORITHMNAME, user.getPassword(), salt, Const.HASH_ITERATIONS);
		user.setPassword(simpleHash.toString());
		
		// 数据持久化操作.
		int result = userMapper.insertSelective(user);
		
		logger.debug(opreation + ".|结束");
		return result;
	}
	
	@Override
	public int deleteUsers(String ids) {
		String opreation = Const.LOGGER_PREFIX_DEBUG + "THREADID = " + Thread.currentThread().getId() + ".|添加用户.|";
		logger.debug(opreation + ".|开始");
		
		int result = userMapper.deleteUsers(ids);
		
		logger.debug(opreation + ".|结束");
		return result;
	}
	
	@Override
	public User selectUserById(Integer id) {
		String opreation = Const.LOGGER_PREFIX_DEBUG + "THREADID = " + Thread.currentThread().getId() + ".|根据用户编号获取用户信息.|";
		logger.debug(opreation + ".|开始");
		
		User user = userMapper.selectByPrimaryKey(id);
		
		logger.debug(opreation + ".|结束");
		return user;
	}
	
	@Override
	public int editUser(User user) {
		String opreation = Const.LOGGER_PREFIX_DEBUG + "THREADID = " + Thread.currentThread().getId() + ".|更新用户信息.|";
		logger.debug(opreation + ".|开始");
		
		int result = userMapper.updateByPrimaryKeySelective(user);
		
		logger.debug(opreation + ".|结束");
		return result;
	}

	@Override
	public List<User> getAllClients() {
		String opreation = Const.LOGGER_PREFIX_DEBUG + "THREADID = " + Thread.currentThread().getId() + ".|更新用户信息.|";
		logger.debug(opreation + ".|开始");
		
		List<User> clients = userMapper.selectAllClients();
		
		logger.debug(opreation + ".|结束");
		return clients;
	}
}
