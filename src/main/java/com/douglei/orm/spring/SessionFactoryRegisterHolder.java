package com.douglei.orm.spring;

import com.douglei.orm.configuration.Configuration;
import com.douglei.orm.context.SessionFactoryRegister;


/**
 * sessionFactoryRegister实例的持有对象
 * @author DougLei
 */
public class SessionFactoryRegisterHolder {
	private String defaultSessionFactoryConfigurationFile;// 默认数据源的配置文件
	private String[] sessionFactoryConfigurationFiles;// 多数据源的配置文件数组
	private SessionFactoryRegister sessionFactoryRegister;
	
	/**
	 * 设置默认的SessionFactoryFile
	 * 如果不配置, 则使用默认值 jdb-orm.conf.xml
	 * @param defaultSessionFactoryConfigurationFile
	 */
	public void setDefaultSessionFactoryConfigurationFile(String defaultSessionFactoryConfigurationFile) {
		this.defaultSessionFactoryConfigurationFile = defaultSessionFactoryConfigurationFile;
	}
	
	/**
	 * 【多数据源】设置SessionFactory
	 * @param sessionFactoryConfigurationFiles
	 */
	public void setSessionFactoryConfigurationFiles(String... sessionFactoryConfigurationFiles) {
		this.sessionFactoryConfigurationFiles = sessionFactoryConfigurationFiles;
	}
	
	/**
	 * 获取SessionFactoryRegister实例
	 * @return
	 */
	public SessionFactoryRegister getSessionFactoryRegister() {
		if(sessionFactoryRegister == null) {
			sessionFactoryRegister = new SessionFactoryRegister();
			registerDefaultSessionFactoryConfigurationFile();
			registerSessionFactoryConfigurationFiles();
		}
		return sessionFactoryRegister;
	}
	
	// 注册默认数据源
	private void registerDefaultSessionFactoryConfigurationFile() {
		if(defaultSessionFactoryConfigurationFile == null) {
			defaultSessionFactoryConfigurationFile = Configuration.DEFAULT_CONF_FILE;
		}
		sessionFactoryRegister.registerDefaultSessionFactoryByConfigurationFile(defaultSessionFactoryConfigurationFile);
	}
	
	// 注册多数据源
	private void registerSessionFactoryConfigurationFiles() {
		if(sessionFactoryConfigurationFiles != null && sessionFactoryConfigurationFiles.length > 0) {
			for (String configurationFile : sessionFactoryConfigurationFiles) {
				sessionFactoryRegister.registerSessionFactoryByConfigurationFile(configurationFile);
			}
		}
	}
}
