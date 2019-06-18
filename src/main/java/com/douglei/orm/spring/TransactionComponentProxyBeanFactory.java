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
public class TransactionComponentProxyBeanFactory<T> implements FactoryBean<T> {
	
	private Class<T> transactionComponentProxyBeanClass;
	private List<Method> transactionMethods;
	
	public TransactionComponentProxyBeanFactory(Class<T> transactionComponentProxyBeanClass, List<Method> transactionMethods) {
		this.transactionComponentProxyBeanClass = transactionComponentProxyBeanClass;
		this.transactionMethods = transactionMethods;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public T getObject() throws Exception {
		return (T) ProxyBeanContext.createProxy(transactionComponentProxyBeanClass, new TransactionProxyInterceptor(transactionComponentProxyBeanClass, transactionMethods));
	}

	@Override
	public Class<?> getObjectType() {
		return transactionComponentProxyBeanClass;
	}

	@Override
	public boolean isSingleton() {
		return true;
	}
}
