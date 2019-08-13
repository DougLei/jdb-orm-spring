package com.douglei.orm.spring.redis.mapping.store;

import java.util.Collection;
import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;

import com.douglei.orm.configuration.DestroyException;
import com.douglei.orm.configuration.environment.mapping.Mapping;
import com.douglei.orm.configuration.environment.mapping.store.MappingStore;
import com.douglei.orm.configuration.environment.mapping.store.NotExistsMappingException;
import com.douglei.orm.configuration.environment.mapping.store.RepeatedMappingException;
import com.douglei.tools.utils.Collections;

import redis.clients.jedis.JedisPool;

/**
 * 
 * @author DougLei
 */
public class RedisMappingStore implements MappingStore {
	private static final Logger logger = LoggerFactory.getLogger(RedisMappingStore.class);
	
	private RedisTemplate<String, Object> redisTemplate;
	public void setRedisTemplate(RedisTemplate<String, Object> redisTemplate) {
		this.redisTemplate = redisTemplate;
	}

	@Override
	public void initializeStore(int size) {
		if(mappings != null) {
			mappings.clear();
			mappings = null;
		}
	}
	
	@Override
	public void addMapping(Mapping mapping) throws RepeatedMappingException{
		String code = mapping.getCode();
		if(mappingExists(code)) {
			throw new RepeatedMappingException("已经存在code为["+code+"]的映射对象: " + mappings.get(code));
		}
		redisTemplate.opsForValue().set(code, mapping);
	}
	
	@Override
	public void addMapping(Collection<Mapping> mappings) throws RepeatedMappingException {
		if(Collections.unEmpty(mappings)) {
			for (Mapping mapping : mappings) {
				addMapping(mapping);
			}
		}
	}

	@Override
	public void addOrCoverMapping(Mapping mapping) {
		String code = mapping.getCode();
		if(logger.isDebugEnabled() && mappingExists(code)) {
			logger.debug("覆盖已经存在code为[{}]的映射对象: {}", code, mappings.get(code));
		}
		mappings.put(code, mapping);
	}
	
	@Override
	public void addOrCoverMapping(Collection<Mapping> mappings) {
		if(Collections.unEmpty(mappings)) {
			for (Mapping mapping : mappings) {
				addOrCoverMapping(mapping);
			}
		}
	}

	@Override
	public Mapping removeMapping(String mappingCode) throws NotExistsMappingException {
		Mapping mp = mappings.remove(mappingCode);
		if(mp == null) {
			throw new NotExistsMappingException("不存在code为["+mappingCode+"]的映射对象, 无法删除");
		}
		return mp;
	}
	
	@Override
	public void removeMapping(Collection<String> mappingCodes) throws NotExistsMappingException {
		if(Collections.unEmpty(mappingCodes)) {
			for (String mappingCode : mappingCodes) {
				removeMapping(mappingCode);
			}
		}
	}
	
	@Override
	public Mapping getMapping(String mappingCode) throws NotExistsMappingException {
		Mapping mp = redisTemplate.execute(new RedisCallback<Mapping>() {
			@Override
			public Mapping doInRedis(RedisConnection connection) throws DataAccessException {
				
				connection.get
				
				return null;
			}
		});
		if(mp == null) {
			throw new NotExistsMappingException("不存在code为["+mappingCode+"]的映射对象");
		}
		return mp;
	}
	
	@Override
	public boolean mappingExists(String mappingCode) {
		return redisTemplate.hasKey(mappingCode);
	}
	
	@Override
	public void destroy() throws DestroyException {
	}
}
