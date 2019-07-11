package com.douglei.orm.spring;

import java.lang.reflect.Field;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import com.douglei.aop.ProxyBean;
import com.douglei.aop.ProxyBeanContext;
import com.douglei.orm.context.TransactionProxyInterceptor;
import com.douglei.orm.context.transaction.component.TransactionComponentEntity;

/**
 * 事物组件代理bean工厂
 * @author DougLei
 */
public class TransactionComponentProxyBeanFactory<T> implements FactoryBean<T>, ApplicationContextAware {
	
	private ApplicationContext applicationContext;
	
	private TransactionComponentEntity transactionComponentEntity;
	private Class<?> transactionComponentClass;
	public TransactionComponentProxyBeanFactory(TransactionComponentEntity transactionComponentEntity) {
		this.transactionComponentEntity = transactionComponentEntity;
		this.transactionComponentClass = transactionComponentEntity.getTransactionComponentClass();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public T getObject() throws Exception {
		ProxyBean proxyBean = ProxyBeanContext.createProxy(transactionComponentClass, new TransactionProxyInterceptor(transactionComponentClass, transactionComponentEntity.getTransactionMethods()));
		doAutowired(proxyBean);
		return (T) proxyBean.getProxy();
	}
	
	/**
	 * 装配属性
	 * 因为该代理对象是自己create的, 所以被代理对象的属性无法注入, 这里手动注入
	 * @param proxyBean
	 * @throws IllegalAccessException 
	 * @throws IllegalArgumentException 
	 * @throws BeansException 
	 */
	private void doAutowired(ProxyBean proxyBean) throws BeansException, IllegalArgumentException, IllegalAccessException {
		Object originObject = proxyBean.getOriginObject();
		Field[] fields = originObject.getClass().getDeclaredFields();
		for (Field field : fields) {
			if(field.isAnnotationPresent(Autowired.class)) {
				field.setAccessible(true);
				if(field.getType() == transactionComponentClass) {// 如果属性是自身对象, 则将代理对象注入
					field.set(originObject, proxyBean.getProxy());
				}else {// 否则属性是其他对象, 则去spring容器中找寻并注入
					field.set(originObject, applicationContext.getBean(field.getType()));
				}
				field.setAccessible(false);
			}
		}
	}

	@Override
	public Class<?> getObjectType() {
		return transactionComponentClass;
	}

	@Override
	public boolean isSingleton() {
		return true;
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.applicationContext = applicationContext;
	}
}
