package com.douglei.orm.spring;

import java.lang.reflect.Method;
import java.util.List;

import org.springframework.beans.factory.FactoryBean;

import com.douglei.aop.ProxyBeanContext;
import com.douglei.orm.context.TransactionProxyInterceptor;

/**
 * 
 * @author DougLei
 */
public class TransactionBeanFactory<T> implements FactoryBean<T> {
	
	private Class<T> transactionProxyBeanClass;
	private List<Method> transactionAnnotationMethods;
	
	public TransactionBeanFactory(Class<T> transactionProxyBeanClass, List<Method> transactionAnnotationMethods) {
		this.transactionProxyBeanClass = transactionProxyBeanClass;
		this.transactionAnnotationMethods = transactionAnnotationMethods;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public T getObject() throws Exception {
		return (T) ProxyBeanContext.createProxy(transactionProxyBeanClass, new TransactionProxyInterceptor(transactionProxyBeanClass, transactionAnnotationMethods));
	}

	@Override
	public Class<?> getObjectType() {
		return transactionProxyBeanClass;
	}

	@Override
	public boolean isSingleton() {
		return true;
	}
}
