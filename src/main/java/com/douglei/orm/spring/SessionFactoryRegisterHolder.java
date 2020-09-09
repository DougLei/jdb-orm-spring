package com.douglei.orm.spring;

import com.douglei.orm.context.IdDuplicateException;
import com.douglei.orm.context.SessionFactoryRegister;


/**
 * sessionFactoryRegister实例的持有对象
 * @author DougLei
 */
public class SessionFactoryRegisterHolder {
	
	private SessionFactoryRegister sessionFactoryRegister;
	public void setSessionFactoryRegister(SessionFactoryRegister sessionFactoryRegister) {
		this.sessionFactoryRegister = sessionFactoryRegister;
	}
	
	/**
	 * 设置默认的SessionFactory
	 * @param defaultConfiguration
	 * @throws IdDuplicateException 
	 */
	public void setDefaultSessionFactory(ConfigurationWrapper defaultConfiguration) throws IdDuplicateException {
		sessionFactoryRegister.registerByFile(defaultConfiguration.getConfigurationFile(), defaultConfiguration.getDataSource(), defaultConfiguration.getMappingContainer());
	}
	
	/**
	 * 【多数据源】设置SessionFactory
	 * @param configurations
	 * @throws IdDuplicateException 
	 */
	public void setSessionFactories(ConfigurationWrapper... configurations) throws IdDuplicateException {
		if(configurations.length > 0) {
			for (ConfigurationWrapper configuration : configurations) {
				sessionFactoryRegister.registerByFile(configuration.getConfigurationFile(), configuration.getDataSource(), configuration.getMappingContainer());
			}
		}
	}
}
