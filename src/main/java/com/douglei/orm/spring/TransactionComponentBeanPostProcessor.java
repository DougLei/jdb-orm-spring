package com.douglei.orm.spring;

import org.springframework.beans.factory.config.InstantiationAwareBeanPostProcessor;

/**
 * 事务组件bean的处理器
 * 主要用来处理事务组件中的属性注入
 * @author DougLei
 */
public class TransactionComponentBeanPostProcessor implements InstantiationAwareBeanPostProcessor {
//	private ApplicationContext applicationContext;
	
	
	

//	@Override//	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {//		this.applicationContext = applicationContext;//		System.out.println(applicationContext.getBean("otherService"));//		System.out.println(applicationContext.getBean("UserService"));//	}
}
