package com.douglei.orm.spring;

import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;

import com.douglei.aop.ProxyBeanContext;

/**
 * 销毁ProxyBeanContext
 * @author DougLei
 */
public class DestroyProxyBeanContext implements ApplicationListener<ContextRefreshedEvent>{

	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {
		ProxyBeanContext.destroy();
	}
}
