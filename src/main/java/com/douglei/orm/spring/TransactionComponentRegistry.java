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
	 * 要扫描的事务组件包路径
	 */
	private String[] transactionComponentPackages;
	 
	@Override
	public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
		// 不用实现
		// 这个beanFactory参数, 可以获得下面register的bean
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		if(transactionComponentPackages == null || transactionComponentPackages.length == 0) {
			throw new NullPointerException(getClass().getName() + " 中的transactionComponentPackages属性值不能为空");
		}
	}

	@Override
	public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry) throws BeansException {
		register2Spring(registry, transactionComponentPackages);
	}
	
	public void setTransactionComponentPackages(String[] transactionComponentPackages) {
		this.transactionComponentPackages = transactionComponentPackages;
	}
}