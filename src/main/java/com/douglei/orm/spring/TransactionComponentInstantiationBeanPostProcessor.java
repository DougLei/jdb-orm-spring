package com.douglei.orm.spring;

import org.springframework.beans.BeansException;
import org.springframework.beans.PropertyValues;
import org.springframework.beans.factory.config.InstantiationAwareBeanPostProcessor;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import com.douglei.orm.context.transaction.component.TransactionComponent;

/**
 * 事务组件bean的处理器
 * 主要用来处理事务组件中的属性注入
 * @author DougLei
 */
public class TransactionComponentInstantiationBeanPostProcessor implements InstantiationAwareBeanPostProcessor, ApplicationContextAware {
	private ApplicationContext applicationContext;
	
	@Override
	public PropertyValues postProcessProperties(PropertyValues pvs, Object bean, String beanName) throws BeansException {
		return InstantiationAwareBeanPostProcessor.super.postProcessProperties(pvs, bean, beanName);
	}

	@Override
	public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
		if(bean.getClass().isAnnotationPresent(TransactionComponent.class)) {
			System.out.println(bean.getClass());
		}
		return InstantiationAwareBeanPostProcessor.super.postProcessAfterInitialization(bean, beanName);
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.applicationContext = applicationContext;
//		System.out.println(applicationContext.getBean("userService"));
		System.out.println(applicationContext.getBean("otherService"));
	}
}
