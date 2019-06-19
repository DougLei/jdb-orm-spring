package com.douglei.orm.spring;

import java.util.List;

import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.GenericBeanDefinition;

import com.douglei.orm.context.TransactionAnnotationMemoryUsage;
import com.douglei.orm.context.TransactionComponentProxyEntity;

/**
 * 事物组件注册到Spring
 */
public class TransactionComponentRegister2Spring {
	
	/**
	 * 
	 * @param registry
	 * @param transactionComponentPackages
	 */
	protected void register2Spring(BeanDefinitionRegistry registry, String[] transactionComponentPackages) {
		List<TransactionComponentProxyEntity> transactionComponentProxyEntities = TransactionAnnotationMemoryUsage.scanTransactionComponent(transactionComponentPackages);
		
		Class<?> transactionClass = null;
		GenericBeanDefinition definition = null;
		for (TransactionComponentProxyEntity transactionComponentProxyEntity : transactionComponentProxyEntities) {
			transactionClass = transactionComponentProxyEntity.getTransactionComponentProxyBeanClass();
			definition = new GenericBeanDefinition();
			
			// 设置该bean的class为TransactionBeanFactory类
			definition.setBeanClass(TransactionComponentProxyBeanFactory.class);
			
			// 将参数传递给TransactionBeanFactory类的构造函数
			definition.getConstructorArgumentValues().addGenericArgumentValue(transactionClass);
			definition.getConstructorArgumentValues().addGenericArgumentValue(transactionComponentProxyEntity.getTransactionMethods());
			
			// 设置根据类型注入
			definition.setAutowireMode(GenericBeanDefinition.AUTOWIRE_BY_TYPE);
			
			// 将bean注入到spring容器中
			registry.registerBeanDefinition(transactionClass.getSimpleName(), definition);
		}
	}
}
