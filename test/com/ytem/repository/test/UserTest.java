package com.ytem.repository.test;

import java.util.List;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.ytem.repository.bean.Menu;
import com.ytem.repository.bean.Role;
import com.ytem.repository.bean.User;
import com.ytem.repository.dao.UserMapper;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:applicationContext.xml"})
public class UserTest {
	@Resource
	private UserMapper userMapper;

	@Test
	public void testSelectUser() {
		User user = userMapper.selectByPrimaryKey(1);
		Role role = user.getRole();
		List<Menu> menuList = role.getMenuList();
		for (Menu menu : menuList) {
			System.out.println(menu.getMenuName());
		}
	}
}
