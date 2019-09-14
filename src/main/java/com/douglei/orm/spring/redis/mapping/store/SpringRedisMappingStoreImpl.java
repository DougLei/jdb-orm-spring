package com.douglei.orm.spring.redis.mapping.store;

import java.util.Collection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;

import com.douglei.orm.configuration.DestroyException;
import com.douglei.orm.configuration.environment.mapping.Mapping;
import com.douglei.orm.configuration.environment.mapping.store.NotExistsMappingException;
import com.douglei.orm.configuration.environment.mapping.store.RepeatedMappingException;
import com.douglei.tools.utils.Collections;

/**
 * 
 * @author DougLei
 */
public class SpringRedisMappingStoreImpl extends SpringRedisMappingStore {
	private static final Logger logger = LoggerFactory.getLogger(SpringRedisMappingStoreImpl.class);

	@Override
	public void clearStore() {
		removeMapping(template.keys(getPrefix() + "*"));
	}
	
	@Override
	public void addMapping(Mapping mapping) throws RepeatedMappingException{
		String code = getCode(mapping.getCode());
		if(mappingExists(code)) {
			throw new RepeatedMappingException("已经存在相同code为["+mapping.getCode()+"]的映射对象: " + getMapping(mapping.getCode()));
		}
		template.opsForValue().set(code, mapping);
	}
	
	@Override
	public void addMapping(Collection<Mapping> mappings) throws RepeatedMappingException {
		if(Collections.unEmpty(mappings)) {
			mappings.forEach(mapping -> addMapping(mapping));
		}
	}

	@Override
	public void addOrCoverMapping(Mapping mapping) {
		String code = getCode(mapping.getCode());
		if(logger.isDebugEnabled() && mappingExists(code)) {
			logger.debug("覆盖已经存在code为[{}]的映射对象: {}", mapping.getCode(), getMapping(mapping.getCode()));
		}
		template.opsForValue().set(code, mapping);
	}
	
	@Override
	public Mapping removeMapping(String code) {
		code = getCode(code);
		if(mappingExists(code)) {
			Mapping mapping = (Mapping) template.opsForValue().get(code);
			template.delete(code);
			return mapping;
		}
		return null;
	}
	
	@Override
	public void removeMapping(Collection<String> codes) {
		if(Collections.unEmpty(codes)) {
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
	public Mapping getMapping(String code) throws NotExistsMappingException {
		Object mapping = template.opsForValue().get(getCode(code));
		if(mapping == null) {
			throw new NotExistsMappingException("不存在code为["+code+"]的映射对象");
		}
		return (Mapping) mapping;
	}
	
	@Override
	public boolean mappingExists(String code) {
		return template.hasKey(code);
	}
	
	@Override
	public void destroy() throws DestroyException {
		if(logger.isDebugEnabled()) logger.debug("{} 开始 destroy", getClass().getName());
		clearStore();
		template = null;
		if(logger.isDebugEnabled()) logger.debug("{} 结束 destroy", getClass().getName());
	}
}
