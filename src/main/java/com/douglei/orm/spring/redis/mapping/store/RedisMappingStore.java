package com.douglei.orm.spring.redis.mapping.store;

import java.util.Collection;

import com.douglei.orm.configuration.DestroyException;
import com.douglei.orm.configuration.environment.mapping.Mapping;
import com.douglei.orm.configuration.environment.mapping.store.MappingStore;
import com.douglei.orm.configuration.environment.mapping.store.NotExistsMappingException;
import com.douglei.orm.configuration.environment.mapping.store.RepeatedMappingException;

public class RedisMappingStore implements MappingStore {

	@Override
	public void destroy() throws DestroyException {
		// TODO Auto-generated method stub

	}

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

}
