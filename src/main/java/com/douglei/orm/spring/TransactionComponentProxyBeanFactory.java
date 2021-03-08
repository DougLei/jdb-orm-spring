package com.douglei.orm.spring;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import com.douglei.aop.ProxyBean;
import com.douglei.aop.ProxyBeanContainer;
import com.douglei.orm.context.TransactionComponentEntity;
import com.douglei.orm.context.TransactionProxyInterceptor;

/**
 * 事物组件代理bean工厂
 * @author DougLei
 */
public class TransactionComponentProxyBeanFactory<T> implements FactoryBean<T>, ApplicationContextAware {
	private AutowireCapableBeanFactory autowireCapableBeanFactory;
	
	private TransactionComponentEntity entity;
	private Class<?> transactionComponentClass;
	public TransactionComponentProxyBeanFactory(TransactionComponentEntity entity) {
		this.entity = entity;
		this.transactionComponentClass = entity.getClazz();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public T getObject() throws Exception {
		ProxyBean proxyBean = ProxyBeanContainer.getProxyBean(transactionComponentClass);
		if(proxyBean == null) {
			proxyBean = ProxyBeanContainer.createAndAddProxy(transactionComponentClass, new TransactionProxyInterceptor(entity.getMethods()));
			autowireCapableBeanFactory.autowireBean(proxyBean.getOriginObject());
		}
		return (T) proxyBean.getProxyObject();
	}
	
	@Override
	public Class<?> getObjectType() {
		return transactionComponentClass;
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.autowireCapableBeanFactory = applicationContext.getAutowireCapableBeanFactory();
	}
}
