package com.douglei.orm.spring;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import com.douglei.tools.utils.StringUtil;

/**
 * Transaction Bean的扫描器
 * @author DougLei
 */
public class TransactionBeanScanner implements BeanDefinitionRegistryPostProcessor, InitializingBean, ApplicationContextAware{
	private String transactionPackage;
	private ApplicationContext applicationContext;
	 
	@Override
	public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
		// 不用实现
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.applicationContext = applicationContext;
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		if(StringUtil.isEmpty(transactionPackage)) {
			throw new NullPointerException(getClass().getName() + " 中的transactionPackage属性值不能为空");
		}
	}

	@Override
	public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry) throws BeansException {
		// TODO Auto-generated method stub
		
	}
	
	public void setTransactionPackage(String transactionPackage) {
		this.transactionPackage = transactionPackage;
	}
}
