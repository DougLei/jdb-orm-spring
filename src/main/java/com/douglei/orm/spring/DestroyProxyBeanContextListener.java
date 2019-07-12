package com.douglei.orm.spring;

import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;

import com.douglei.aop.ProxyBeanContext;

/**
 * 销毁ProxyBeanContext监听器
 * 
 * 在spring容器加载完成后进行
 * 
 * @author DougLei
 */
public class DestroyProxyBeanContextListener implements ApplicationListener<ContextRefreshedEvent>{

	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {
		ProxyBeanContext.destroy();
	}
}
