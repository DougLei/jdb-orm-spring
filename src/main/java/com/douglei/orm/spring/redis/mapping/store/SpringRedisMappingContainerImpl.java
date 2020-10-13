package com.douglei.orm.spring.redis.mapping.store;

import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;

import com.douglei.orm.mapping.Mapping;
import com.douglei.orm.mapping.MappingFeature;

/**
 * 
 * @author DougLei
 */
public class SpringRedisMappingContainerImpl extends SpringRedisMappingContainer {
	private static final Logger logger = LoggerFactory.getLogger(SpringRedisMappingContainerImpl.class);

	@Override
	public void clear() {
		Set<String> codes = template.keys(getPrefix() + "*");
		if(codes != null && !codes.isEmpty()) {
			template.execute(new RedisCallback<Object>() {
				@Override
				public Object doInRedis(RedisConnection connection) throws DataAccessException {
					connection.del(getCodeByteArray(codes));
					return null;
				}
			});
		}
	}
	
	@Override
	public MappingFeature addMappingFeature(MappingFeature mappingFeature) {
		MappingFeature exMappingFeature = getMappingFeature(mappingFeature.getCode());
		if(logger.isDebugEnabled() && exMappingFeature != null) 
			logger.debug("覆盖code为[{}]的映射特性: {}", mappingFeature.getCode(), exMappingFeature);
		
		template.opsForValue().set(getCode4Feature(mappingFeature.getCode()), mappingFeature);
		return exMappingFeature;
	}

	@Override
	public MappingFeature deleteMappingFeature(String code) {
		if(!exists(code)) 
			return null;
		
		code = getCode4Feature(code);
		MappingFeature mappingFeature = (MappingFeature) template.opsForValue().get(code);
		template.delete(code);
		return mappingFeature;
	}

	@Override
	public MappingFeature getMappingFeature(String code) {
		return (MappingFeature) template.opsForValue().get(getCode4Feature(code));
	}
	
	@Override
	public Mapping addMapping(Mapping mapping) {
		Mapping exMapping = getMapping(mapping.getCode());
		if(logger.isDebugEnabled() && exMapping != null) 
			logger.debug("覆盖code为[{}]的映射: {}", mapping.getCode(), exMapping);
		
		template.opsForValue().set(getCode(mapping.getCode()), mapping);
		return exMapping;
	}
	
	@Override
	public Mapping deleteMapping(String code) {
		if(!exists(code)) 
			return null;
		
		code = getCode(code);
		Mapping mapping = (Mapping) template.opsForValue().get(code);
		template.delete(code);
		return mapping;
	}
	
	@Override
	public Mapping getMapping(String code) {
		return (Mapping) template.opsForValue().get(getCode(code));
	}
	
	@Override
	public boolean exists(String code) {
		return template.hasKey(getCode(code));
	}
}
