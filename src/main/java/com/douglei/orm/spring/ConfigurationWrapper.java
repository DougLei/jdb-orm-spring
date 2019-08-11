package com.douglei.orm.spring;

import com.douglei.orm.configuration.ExternalDataSource;
import com.douglei.orm.configuration.environment.mapping.cache.store.MappingCacheStore;

/**
 * 
 * @author DougLei
 */
public class ConfigurationWrapper {
	
	protected String configurationFile;
	protected ExternalDataSource dataSource;
	protected MappingCacheStore mappingCacheStore;
	
	public String getConfigurationFile() {
		return configurationFile;
	}
	public void setConfigurationFile(String configurationFile) {
		this.configurationFile = configurationFile;
	}
	public ExternalDataSource getDataSource() {
		return dataSource;
	}
	public void setDataSource(ExternalDataSource dataSource) {
		this.dataSource = dataSource;
	}
	public MappingCacheStore getMappingCacheStore() {
		return mappingCacheStore;
	}
	public void setMappingCacheStore(MappingCacheStore mappingCacheStore) {
		this.mappingCacheStore = mappingCacheStore;
	}
}
