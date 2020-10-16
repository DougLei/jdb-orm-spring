package com.douglei.orm.spring.redis.mapping.store;

import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;

import com.douglei.orm.mapping.Mapping;
import com.douglei.orm.mapping.MappingProperty;

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
	public MappingProperty addMappingProperty(MappingProperty mappingProperty) {
		MappingProperty exMappingProperty = getMappingProperty(mappingProperty.getCode());
		if(logger.isDebugEnabled() && exMappingProperty != null) 
			logger.debug("覆盖code为[{}]的映射属性: {}", mappingProperty.getCode(), exMappingProperty);
		
		template.opsForValue().set(getCode4Property(mappingProperty.getCode()), mappingProperty);
		return exMappingProperty;
	}

	@Override
	public MappingProperty deleteMappingProperty(String code) {
		if(!exists(code)) 
			return null;
		
		code = getCode4Property(code);
		MappingProperty mappingProperty = (MappingProperty) template.opsForValue().get(code);
		template.delete(code);
		return mappingProperty;
	}

	@Override
	public MappingProperty getMappingProperty(String code) {
		return (MappingProperty) template.opsForValue().get(getCode4Property(code));
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
