package com.douglei.orm.spring;

import java.lang.reflect.Field;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
	private static final Logger logger = LoggerFactory.getLogger(TransactionComponentProxyBeanFactory.class);
	
	private ApplicationContext applicationContext;
	
	private TransactionComponentEntity transactionComponentEntity;
	private Class<?> transactionComponentClass;
	private Class<?>[] transactionComponentInterfaces;
	public TransactionComponentProxyBeanFactory(TransactionComponentEntity transactionComponentEntity) {
		this.transactionComponentEntity = transactionComponentEntity;
		this.transactionComponentClass = transactionComponentEntity.getTransactionComponentClass();
		this.transactionComponentInterfaces = this.transactionComponentClass.getInterfaces();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public T getObject() throws Exception {
		ProxyBean proxyBean = ProxyBeanContext.getProxyBean(transactionComponentClass);
		if(proxyBean == null) {
			proxyBean = ProxyBeanContext.createAndAddProxy(transactionComponentClass, new TransactionProxyInterceptor(transactionComponentClass, transactionComponentEntity.getTransactionMethods()));
		}
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
		// TODO TEST!!!!!
		System.out.println(proxyBean.getProxy());
		Object originObject = proxyBean.getOriginObject();
		Field[] fields = transactionComponentClass.getDeclaredFields();
		for (Field field : fields) {
			if(field.isAnnotationPresent(Autowired.class)) {
				if(field.getType().isInterface()) { // 如果属性是接口
					if(implementInterface(field.getType())) { // 判断被代理对象是否实现该接口, 如果实现, 则将代理对象注入, 否则放弃注入, 记录日志
						if(logger.isDebugEnabled()) {
							logger.debug("[{}]类实现了[{}]接口, 将代理对象注入该接口属性", transactionComponentClass.getName(), field.getType().getName());
						}
						setValue(field, originObject, proxyBean.getProxy());
						continue;
					}
					if(logger.isDebugEnabled()) {
						logger.debug("[{}]类没有实现[{}]接口, 无法给该接口属性注入实例", transactionComponentClass.getName(), field.getType().getName());
					}
				}else { // 否则属性是类
					if(field.getType() == transactionComponentClass) { // 如果属性类型与被代理对象类型一致, 则将代理对象注入
						if(logger.isDebugEnabled()) {
							logger.debug("属性[{}]的类型[{}]与被代理对象[{}]的类型一致, 将代理对象注入该属性", field.getName(), field.getType().getName(), transactionComponentClass.getName());
						}
						setValue(field, originObject, proxyBean.getProxy());
					}else { // 否则是其他对象, 则去spring容器中寻找并注入
						if(logger.isDebugEnabled()) {
							logger.debug("属性[{}]的类型为[{}], 在spring容器中寻找相应的实例并注入该属性", field.getName(), field.getType().getName());
						}
						setValue(field, originObject, applicationContext.getBean(field.getType()));
					}
				}
			}
		}
	}

	/**
	 * 是否实现了接口
	 * @param interfaceClass
	 * @return
	 */
	private boolean implementInterface(Class<?> interfaceClass) {
		if(transactionComponentInterfaces.length > 0) {
			for (Class<?> transactionComponentInterface : transactionComponentInterfaces) {
				if(interfaceClass == transactionComponentInterface) {
					return true;
				}
			}
		}
		return false;
	}
	
	/**
	 * 给属性赋值
	 * @param field
	 * @param obj
	 * @param value
	 * @throws IllegalAccessException 
	 * @throws IllegalArgumentException 
	 */
	private void setValue(Field field, Object obj, Object value) throws IllegalArgumentException, IllegalAccessException {
		field.setAccessible(true);
		field.set(obj, value);
		field.setAccessible(false);
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
