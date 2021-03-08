package com.douglei.orm.spring;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.GenericBeanDefinition;

import com.douglei.orm.context.TransactionComponentEntity;
import com.douglei.orm.context.TransactionScanner;

/**
 * 事物组件注册到Spring
 */
public class TransactionComponentRegister2Spring {
	private static final Logger logger = LoggerFactory.getLogger(TransactionComponentRegister2Spring.class);
	
	/**
	 * 
	 * @param registry
	 * @param scanAll
	 * @param transactionComponentPackages
	 */
	protected void register2Spring(BeanDefinitionRegistry registry, boolean scanAll, String[] packages) {
		GenericBeanDefinition definition = null;
		for (TransactionComponentEntity entity : TransactionScanner.scan(scanAll, packages)) {
			logger.debug("注册事物组件代理实体: {}", entity);
			
			definition = new GenericBeanDefinition();
			
			// 设置该bean的class为TransactionBeanFactory类
			definition.setBeanClass(TransactionComponentProxyBeanFactory.class);
			
			// 将参数传递给TransactionBeanFactory类的构造函数
			definition.getConstructorArgumentValues().addGenericArgumentValue(entity);
			
			// 设置根据类型注入
			definition.setAutowireMode(GenericBeanDefinition.AUTOWIRE_BY_TYPE);
			
			// 将bean注入到spring容器中
			registry.registerBeanDefinition(entity.getName(), definition);
		}
	}
}
