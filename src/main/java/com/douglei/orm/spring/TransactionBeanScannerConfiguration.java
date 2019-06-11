package com.douglei.orm.spring;

import java.util.List;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.beans.factory.support.GenericBeanDefinition;

import com.douglei.orm.context.TransactionAnnotationMemoryUsage;
import com.douglei.orm.context.TransactionClass;
import com.douglei.tools.utils.StringUtil;

/**
 * Transaction Bean的扫描器配置对象
 * @author DougLei
 */
public class TransactionBeanScannerConfiguration implements BeanDefinitionRegistryPostProcessor, InitializingBean{
	private String transactionPackage;
	 
	@Override
	public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
		// 不用实现
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		if(StringUtil.isEmpty(transactionPackage)) {
			throw new NullPointerException(getClass().getName() + " 中的transactionPackage属性值不能为空");
		}
	}

	@Override
	public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry) throws BeansException {
		List<TransactionClass> transactionClasses = TransactionAnnotationMemoryUsage.scanTransactionAnnotation(transactionPackage);
		
		Class<?> transactionClass = null;
		GenericBeanDefinition definition = null;
		for (TransactionClass tc : transactionClasses) {
			transactionClass = tc.getTransactionClass();
			definition = (GenericBeanDefinition) BeanDefinitionBuilder.genericBeanDefinition(transactionClass).getRawBeanDefinition();
			
			definition.getConstructorArgumentValues().addGenericArgumentValue(transactionClass);
			definition.getConstructorArgumentValues().addGenericArgumentValue(tc.getTransactionAnnotationMethods());
			
			definition.setBeanClass(TransactionBeanFactory.class);
			
			definition.setAutowireMode(GenericBeanDefinition.AUTOWIRE_BY_TYPE);
			registry.registerBeanDefinition(transactionClass.getSimpleName(), definition);
		}
	}
	
	public void setTransactionPackage(String transactionPackage) {
		this.transactionPackage = transactionPackage;
	}
}
