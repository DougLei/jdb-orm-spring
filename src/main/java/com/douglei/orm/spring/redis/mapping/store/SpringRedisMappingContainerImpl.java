package com.douglei.orm.spring.redis.mapping.store;

import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;

import com.douglei.orm.mapping.Mapping;

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
	public Mapping addMapping(Mapping mapping) {
		String code = getCode(mapping.getCode());
		Mapping exMapping = getMapping(code);
		if(logger.isDebugEnabled() && exMapping != null) {
			logger.debug("覆盖已经存在code为[{}]的映射对象: {}", mapping.getCode(), getMapping(mapping.getCode()));
		}
		template.opsForValue().set(code, mapping);
		return exMapping;
	}
	
	@Override
	public Mapping deleteMapping(String code) {
		code = getCode(code);
		if(exists(code)) {
			Mapping mapping = (Mapping) template.opsForValue().get(code);
			template.delete(code);
			return mapping;
		}
		return null;
	}
	
	@Override
	public Mapping getMapping(String code) {
		return (Mapping) template.opsForValue().get(getCode(code));
	}
	
	@Override
	public boolean exists(String code) {
		return template.hasKey(code);
	}
}
