package com.douglei.orm.spring.redis.mapping.store;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;

import com.douglei.orm.configuration.environment.mapping.container.MappingContainer;
import com.douglei.orm.configuration.environment.mapping.container.impl.redis.RedisHandler;


/**
 * 
 * @author DougLei
 */
public abstract class SpringRedisMappingContainer extends RedisHandler implements MappingContainer {

	protected RedisTemplate<String, Object> template;
	protected RedisSerializer<String> keySerializer;
	protected RedisSerializer<Object> valueSerializer;
	
	@SuppressWarnings("unchecked")
	public void setTemplate(RedisTemplate<String, Object> template) {
		this.template = template;
		this.keySerializer = (RedisSerializer<String>) template.getKeySerializer();
		this.valueSerializer = (RedisSerializer<Object>) template.getValueSerializer();
	}
}
