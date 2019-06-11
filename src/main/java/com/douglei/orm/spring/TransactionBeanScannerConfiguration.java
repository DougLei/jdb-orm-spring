package com.douglei.orm.spring;

import java.util.List;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
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
		// 这个beanFactory参数, 可以获得下面register的bean
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
			definition = new GenericBeanDefinition();
			
			// 设置该bean的class为TransactionBeanFactory类
			definition.setBeanClass(TransactionBeanFactory.class);
			
			// 将参数传递给TransactionBeanFactory类的构造函数
			definition.getConstructorArgumentValues().addGenericArgumentValue(transactionClass);
			definition.getConstructorArgumentValues().addGenericArgumentValue(tc.getTransactionAnnotationMethods());
			
			// 设置根据类型注入
			definition.setAutowireMode(GenericBeanDefinition.AUTOWIRE_BY_TYPE);
			
			// 将bean注入到spring容器中
			registry.registerBeanDefinition(transactionClass.getSimpleName(), definition);
		}
	}
	
	public void setTransactionPackage(String transactionPackage) {
		this.transactionPackage = transactionPackage;
	}
}
