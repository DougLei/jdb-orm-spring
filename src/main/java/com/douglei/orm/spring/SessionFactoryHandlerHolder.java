package com.douglei.orm.spring;

import com.douglei.orm.context.SessionFactoryHandler;


/**
 * SessionFactoryHandler实例的持有对象
 * @author DougLei
 */
public class SessionFactoryHandlerHolder {
	private static final SessionFactoryHandler sessionFactoryHandler = new SessionFactoryHandler();
	
	/**
	 * 【必须配置】设置默认的SessionFactory
	 * @param defaultSessionFactoryConfigurationFile
	 */
	public void setDefaultSessionFactoryConfigurationFile(String defaultSessionFactoryConfigurationFile) {
		sessionFactoryHandler.registerDefaultSessionFactoryByConfigurationFile(defaultSessionFactoryConfigurationFile);
	}
	
	/**
	 * 【多数据源】设置SessionFactory
	 * @param sessionFactoryConfigurationFiles
	 */
	public void setSessionFactoryConfigurationFile(String... sessionFactoryConfigurationFiles) {
		if(sessionFactoryConfigurationFiles != null && sessionFactoryConfigurationFiles.length > 0) {
			for (String configurationFile : sessionFactoryConfigurationFiles) {
				sessionFactoryHandler.registerSessionFactoryByConfigurationFile(configurationFile);
			}
		}
	}
	
	/**
	 * 获取SessionFactoryHandler实例
	 * @return
	 */
	public static SessionFactoryHandler getSessionFactoryHandler() {
		return sessionFactoryHandler;
	}
}
