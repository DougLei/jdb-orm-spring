package com.douglei.orm.spring;

import com.douglei.orm.context.SessionFactoryRegister;


/**
 * sessionFactoryRegister实例的持有对象
 * @author DougLei
 */
public class SessionFactoryRegisterHolder {
	
	private SessionFactoryRegister sessionFactoryRegister;
	
	/**
	 * 设置默认的SessionFactory
	 * @param defaultConfiguration
	 */
	public void setDefaultSessionFactory(ConfigurationWrapper defaultConfiguration) {
		sessionFactoryRegister.registerDefaultSessionFactory(defaultConfiguration.getConfigurationFile(), defaultConfiguration.getDataSource(), defaultConfiguration.getMappingCacheStore(), false);
	}
	
	/**
	 * 【多数据源】设置SessionFactory
	 * @param configurations
	 */
	public void setSessionFactorys(ConfigurationWrapper... configurations) {
		if(configurations.length > 0) {
			for (ConfigurationWrapper configuration : configurations) {
				sessionFactoryRegister.registerSessionFactoryByConfigurationFile(configuration.getConfigurationFile(), configuration.getDataSource(), configuration.getMappingCacheStore());
			}
		}
	}

	public void setSessionFactoryRegister(SessionFactoryRegister sessionFactoryRegister) {
		this.sessionFactoryRegister = sessionFactoryRegister;
	}
}
