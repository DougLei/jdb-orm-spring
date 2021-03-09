package com.douglei.orm.spring;

import com.douglei.orm.context.OrmContextException;
import com.douglei.orm.context.SessionFactoryContainer;


/**
 * 
 * @author DougLei
 */
public class SessionFactoryRegister {
	
	/**
	 * 设置默认的SessionFactory
	 * @param entity
	 * @throws OrmContextException 
	 */
	public void setDefaultSessionFactory(ConfigurationEntity entity) throws OrmContextException {
		SessionFactoryContainer.getSingleton().registerByFile(entity.getFilepath(), entity.getDataSource(), entity.getMappingContainer());
	}
	
	/**
	 * 【多数据源】设置SessionFactory
	 * @param configurations
	 * @throws OrmContextException 
	 */
	public void setSessionFactories(ConfigurationEntity... configurations) {
		if(configurations.length > 0) {
			for (ConfigurationEntity configuration : configurations) 
				SessionFactoryContainer.getSingleton().registerByFile(configuration.getFilepath(), configuration.getDataSource(), configuration.getMappingContainer());
		}
	}
}
