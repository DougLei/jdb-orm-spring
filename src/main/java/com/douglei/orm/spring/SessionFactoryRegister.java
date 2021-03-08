package com.douglei.orm.spring;

import com.douglei.orm.context.IdRepeatedException;
import com.douglei.orm.context.SessionFactoryContainer;


/**
 * 
 * @author DougLei
 */
public class SessionFactoryRegister {
	
	/**
	 * 设置默认的SessionFactory
	 * @param entity
	 * @throws IdRepeatedException 
	 */
	public void setDefaultSessionFactory(ConfigurationEntity entity) throws IdRepeatedException {
		SessionFactoryContainer.getSingleton().registerByFile(entity.getFilepath(), entity.getDataSource(), entity.getMappingContainer());
	}
	
	/**
	 * 【多数据源】设置SessionFactory
	 * @param configurations
	 * @throws IdRepeatedException 
	 */
	public void setSessionFactories(ConfigurationEntity... configurations) throws IdRepeatedException {
		if(configurations.length > 0) {
			for (ConfigurationEntity configuration : configurations) 
				SessionFactoryContainer.getSingleton().registerByFile(configuration.getFilepath(), configuration.getDataSource(), configuration.getMappingContainer());
		}
	}
}
