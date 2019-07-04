package com.douglei.orm.spring;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.GenericBeanDefinition;

import com.douglei.orm.context.transaction.component.TransactionAnnotationMemoryUsage;
import com.douglei.orm.context.transaction.component.TransactionComponentProxyEntity;

/**
 * 事物组件注册到Spring
 */
public class TransactionComponentRegister2Spring {
	private static final Logger logger = LoggerFactory.getLogger(TransactionComponentRegister2Spring.class);
	
	/**
	 * 
	 * @param registry
	 * @param searchAllPath
	 * @param transactionComponentPackages
	 */
	protected void register2Spring(BeanDefinitionRegistry registry, boolean searchAllPath, String[] transactionComponentPackages) {
		List<TransactionComponentProxyEntity> transactionComponentProxyEntities = TransactionAnnotationMemoryUsage.scanTransactionComponent(searchAllPath, transactionComponentPackages);
		
		Class<?> transactionComponentProxyBeanFactoryClass = TransactionComponentProxyBeanFactory.class;
		Class<?> transactionClass = null;
		GenericBeanDefinition definition = null;
		for (TransactionComponentProxyEntity transactionComponentProxyEntity : transactionComponentProxyEntities) {
			logger.debug("注册事物组件代理实体: {}", transactionComponentProxyEntity);
			
			transactionClass = transactionComponentProxyEntity.getTransactionComponentProxyBeanClass();
			definition = new GenericBeanDefinition();
			
			// 设置该bean的class为TransactionBeanFactory类
			definition.setBeanClass(transactionComponentProxyBeanFactoryClass);
			
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
