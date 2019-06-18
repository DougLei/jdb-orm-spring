package com.douglei.orm.spring;

import com.douglei.orm.context.SessionFactoryRegister;


/**
 * sessionFactoryRegister实例的持有对象
 * @author DougLei
 */
public class SessionFactoryHandlerHolder {
	private static final SessionFactoryRegister sessionFactoryRegister = new SessionFactoryRegister();
	
	/**
	 * 【必须配置】设置默认的SessionFactory
	 * @param defaultSessionFactoryConfigurationFile
	 */
	public void setDefaultSessionFactoryConfigurationFile(String defaultSessionFactoryConfigurationFile) {
		sessionFactoryRegister.registerDefaultSessionFactoryByConfigurationFile(defaultSessionFactoryConfigurationFile);
	}
	
	/**
	 * 【多数据源】设置SessionFactory
	 * @param sessionFactoryConfigurationFiles
	 */
	public void setSessionFactoryConfigurationFile(String... sessionFactoryConfigurationFiles) {
		if(sessionFactoryConfigurationFiles != null && sessionFactoryConfigurationFiles.length > 0) {
			for (String configurationFile : sessionFactoryConfigurationFiles) {
				sessionFactoryRegister.registerSessionFactoryByConfigurationFile(configurationFile);
			}
		}
	}
	
	/**
	 * 获取SessionFactoryRegister实例
	 * @return
	 */
	public static SessionFactoryRegister getSessionFactoryRegister() {
		return sessionFactoryRegister;
	}
}
