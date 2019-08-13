package com.douglei.orm.spring;

import com.douglei.orm.configuration.ExternalDataSource;
import com.douglei.orm.configuration.environment.mapping.store.MappingStore;

/**
 * 
 * @author DougLei
 */
public class ConfigurationWrapper {
	
	protected String configurationFile;
	protected ExternalDataSource dataSource;
	protected MappingStore mappingStore;
	
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
	public MappingStore getMappingStore() {
		return mappingStore;
	}
	public void setMappingStore(MappingStore mappingStore) {
		this.mappingStore = mappingStore;
	}
}
