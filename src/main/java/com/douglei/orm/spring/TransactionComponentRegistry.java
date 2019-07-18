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
	private boolean searchAll;
	
	/**
	 * 要扫描的事务组件包路径
	 */
	private String[] transactionComponentPackages;
	 
	@Override
	public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		if(transactionComponentPackages == null || transactionComponentPackages.length == 0) {
			throw new NullPointerException(getClass().getName() + " 中的transactionComponentPackages属性值不能为空");
		}
	}

	@Override
	public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry) throws BeansException {
		register2Spring(registry, searchAll, transactionComponentPackages);
	}
	
	public void setSearchAll(boolean searchAll) {
		this.searchAll = searchAll;
	}
	public void setTransactionComponentPackages(String[] transactionComponentPackages) {
		this.transactionComponentPackages = transactionComponentPackages;
	}
}
