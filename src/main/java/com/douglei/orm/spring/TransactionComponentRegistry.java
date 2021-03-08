package com.douglei.orm.spring;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;

/**
 * 
 * @author DougLei
 */
public class TransactionComponentRegistry extends TransactionComponentRegister2Spring implements BeanDefinitionRegistryPostProcessor, InitializingBean{
	
	/**
	 * 是否扫描所有符合添加的包, 不论是否在jar包中
	 */
	private boolean scanAll;
	
	/**
	 * 要扫描的事务组件包路径
	 */
	private String[] packages;
	 
	@Override
	public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		if(packages == null || packages.length == 0) 
			throw new NullPointerException(getClass().getName() + " 中的packages属性值不能为空");
	}

	@Override
	public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry) throws BeansException {
		register2Spring(registry, scanAll, packages);
	}
	
	public void setScanAll(boolean scanAll) {
		this.scanAll = scanAll;
	}
	public void setPackages(String[] packages) {
		this.packages = packages;
	}
}
