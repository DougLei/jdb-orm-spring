package com.douglei.orm.spring;

import com.douglei.orm.configuration.ExternalDataSource;
import com.douglei.orm.mapping.MappingContainer;

/**
 * 
 * @author DougLei
 */
public class ConfigurationEntity {
	private String filepath; // 基于java resource
	private ExternalDataSource dataSource;
	private MappingContainer mappingContainer;
	
	public String getFilepath() {
		return filepath;
	}
	public void setFilepath(String filepath) {
		this.filepath = filepath;
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
