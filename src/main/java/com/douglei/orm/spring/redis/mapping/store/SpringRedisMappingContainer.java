package com.douglei.orm.spring.redis.mapping.store;

import org.springframework.data.redis.core.RedisTemplate;

import com.douglei.orm.mapping.MappingContainer;
import com.douglei.orm.mapping.container.impl.redis.RedisHandler;


/**
 * 
 * @author DougLei
 */
public abstract class SpringRedisMappingContainer extends RedisHandler implements MappingContainer {

	protected RedisTemplate<String, Object> template;
	
	public void setTemplate(RedisTemplate<String, Object> template) {
		this.template = template;
	}
}
