package com.douglei.orm.spring;

import com.douglei.orm.context.IdDuplicateException;
import com.douglei.orm.context.SessionFactoryContainer;


/**
 * sessionFactoryContainer实例的持有对象
 * @author DougLei
 */
public class SessionFactoryContainerHolder {
	
	private SessionFactoryContainer container;
	public void setSessionFactoryContainer() {
		this.container = SessionFactoryContainer.getSingleton();
	}
	
	/**
	 * 设置默认的SessionFactory
	 * @param defaultConfiguration
	 * @throws IdDuplicateException 
	 */
	public void setDefaultSessionFactory(ConfigurationWrapper defaultConfiguration) throws IdDuplicateException {
		container.registerByFile(defaultConfiguration.getConfigurationFile(), defaultConfiguration.getDataSource(), defaultConfiguration.getMappingContainer());
	}
	
	/**
	 * 【多数据源】设置SessionFactory
	 * @param configurations
	 * @throws IdDuplicateException 
	 */
	public void setSessionFactories(ConfigurationWrapper... configurations) throws IdDuplicateException {
		if(configurations.length > 0) {
			for (ConfigurationWrapper configuration : configurations) {
				container.registerByFile(configuration.getConfigurationFile(), configuration.getDataSource(), configuration.getMappingContainer());
			}
		}
	}
}
