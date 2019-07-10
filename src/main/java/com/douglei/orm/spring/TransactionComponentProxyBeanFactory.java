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
	
	private Class<T> transactionComponentClass;
	private List<Method> transactionMethods;
	
	public TransactionComponentProxyBeanFactory(Class<T> transactionComponentClass, List<Method> transactionMethods) {
		this.transactionComponentClass = transactionComponentClass;
		this.transactionMethods = transactionMethods;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public T getObject() throws Exception {
		return (T) ProxyBeanContext.createProxy(transactionComponentClass, new TransactionProxyInterceptor(transactionComponentClass, transactionMethods));
	}

	@Override
	public Class<?> getObjectType() {
		return transactionComponentClass;
	}

	@Override
	public boolean isSingleton() {
		return true;
	}
}
