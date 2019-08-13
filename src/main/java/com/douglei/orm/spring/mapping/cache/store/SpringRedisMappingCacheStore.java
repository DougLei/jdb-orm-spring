package com.douglei.orm.spring.mapping.cache.store;

import java.util.Collection;

import com.douglei.orm.configuration.DestroyException;
import com.douglei.orm.configuration.environment.mapping.Mapping;
import com.douglei.orm.configuration.environment.mapping.cache.store.MappingCacheStore;
import com.douglei.orm.configuration.environment.mapping.cache.store.NotExistsMappingException;
import com.douglei.orm.configuration.environment.mapping.cache.store.RepeatedMappingException;

/**
 * spring redis 映射缓存存储
 * @author DougLei
 */
public class SpringRedisMappingCacheStore implements MappingCacheStore {

	@Override
	public void initializeStore(int size) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addMapping(Mapping mapping) throws RepeatedMappingException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addMapping(Collection<Mapping> mappings) throws RepeatedMappingException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addOrCoverMapping(Mapping mapping) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addOrCoverMapping(Collection<Mapping> mappings) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Mapping removeMapping(String mappingCode) throws NotExistsMappingException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void removeMapping(Collection<String> mappingCodes) throws NotExistsMappingException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Mapping getMapping(String mappingCode) throws NotExistsMappingException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean mappingExists(String mappingCode) {
		// TODO Auto-generated method stub
		return false;
	}
	
	@Override
	public void destroy() throws DestroyException {
		// TODO Auto-generated method stub
		
	}
}
