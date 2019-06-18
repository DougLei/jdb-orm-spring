package com.douglei.orm.spring;

import com.douglei.orm.context.SessionFactoryRegister;


/**
 * sessionFactoryRegister实例的持有对象
 * @author DougLei
 */
public class SessionFactoryRegisterHolder {
	private static final SessionFactoryRegister sessionFactoryRegister = new SessionFactoryRegister();
	
	/**
	 * 设置默认的SessionFactoryFile
	 * 如果不配置, 则使用默认值 jdb-orm.conf.xml
	 * @param defaultSessionFactoryConfigurationFile
	 */
	public void setDefaultSessionFactoryConfigurationFile(String defaultSessionFactoryConfigurationFile) {
		sessionFactoryRegister.registerDefaultSessionFactoryByConfigurationFile(defaultSessionFactoryConfigurationFile);
	}
	
	/**
	 * 【多数据源】设置SessionFactory
	 * @param sessionFactoryConfigurationFiles
	 */
	public void setSessionFactoryConfigurationFiles(String... sessionFactoryConfigurationFiles) {
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
