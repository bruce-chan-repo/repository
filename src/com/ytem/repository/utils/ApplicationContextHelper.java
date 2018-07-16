package com.ytem.repository.utils;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

public class ApplicationContextHelper implements ApplicationContextAware {  
	private static ApplicationContext applicationContext;
	 
    /**
     * 实现ApplicationContextAware接口的回调方法，设置上下文环境
     */
	@Override
    public void setApplicationContext(ApplicationContext applicationContext){
    	ApplicationContextHelper.applicationContext = applicationContext;
    }
 
    public static ApplicationContext getApplicationContext(){
        return applicationContext;
    }
 
    /**
     * 获取对象
     */
    public static Object getBean(String name) throws BeansException{
        return applicationContext.getBean(name);
    }
} 