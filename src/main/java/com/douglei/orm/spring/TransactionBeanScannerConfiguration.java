package com.douglei.orm.spring;

import java.util.List;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.beans.factory.support.GenericBeanDefinition;

import com.douglei.orm.context.TransactionAnnotationMemoryUsage;
import com.douglei.orm.context.TransactionProxyEntity;

/**
 * Transaction Bean的扫描器配置对象
 * @author DougLei
 */
public class TransactionBeanScannerConfiguration implements BeanDefinitionRegistryPostProcessor, InitializingBean{
	
	/**
	 * 要扫描@Transaction的根包路径
	 */
	private String[] transactionBasePackages;
	 
	@Override
	public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
		// 不用实现
		// 这个beanFactory参数, 可以获得下面register的bean
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		if(transactionBasePackages == null || transactionBasePackages.length == 0) {
			throw new NullPointerException(getClass().getName() + " 中的transactionBasePackage属性值不能为空");
		}
	}

	@Override
	public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry) throws BeansException {
		List<TransactionProxyEntity> transactionProxyEntities = TransactionAnnotationMemoryUsage.scanTransactionAnnotation(transactionBasePackages);
		
		Class<?> transactionClass = null;
		GenericBeanDefinition definition = null;
		for (TransactionProxyEntity transactionProxyEntity : transactionProxyEntities) {
			transactionClass = transactionProxyEntity.getTransactionClass();
			definition = new GenericBeanDefinition();
			
			// 设置该bean的class为TransactionBeanFactory类
			definition.setBeanClass(TransactionBeanFactory.class);
			
			// 将参数传递给TransactionBeanFactory类的构造函数
			definition.getConstructorArgumentValues().addGenericArgumentValue(transactionClass);
			definition.getConstructorArgumentValues().addGenericArgumentValue(transactionProxyEntity.getTransactionAnnotationMethods());
			
			// 设置根据类型注入
			definition.setAutowireMode(GenericBeanDefinition.AUTOWIRE_BY_TYPE);
			
			// 将bean注入到spring容器中
			registry.registerBeanDefinition(transactionClass.getSimpleName(), definition);
		}
	}
	
	public void setTransactionBasePackages(String[] transactionBasePackages) {
		this.transactionBasePackages = transactionBasePackages;
	}
}
