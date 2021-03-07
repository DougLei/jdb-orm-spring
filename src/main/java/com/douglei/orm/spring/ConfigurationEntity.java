package com.douglei.orm.spring;

import com.douglei.orm.configuration.ExternalDataSource;
import com.douglei.orm.mapping.MappingContainer;

/**
 * 
 * @author DougLei
 */
public class ConfigurationEntity {
	private String configurationFile;
	private ExternalDataSource dataSource;
	private MappingContainer mappingContainer;
	
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
	public MappingContainer getMappingContainer() {
		return mappingContainer;
	}
	public void setMappingContainer(MappingContainer mappingContainer) {
		this.mappingContainer = mappingContainer;
	}
}
