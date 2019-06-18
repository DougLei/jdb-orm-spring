package com.douglei.orm.spring;

import com.douglei.orm.context.SessionFactoryRegister;

/**
 * SessionFactoryRegister的上下文
 * 可以通过该类, 直接获取SessionFactoryRegister实例
 * @author DougLei
 */
public class SessionFactoryRegisterContext {
	private static SessionFactoryRegisterHolder sessionFactoryRegisterHolder;
	public void setSessionFactoryRegisterHolder(SessionFactoryRegisterHolder sessionFactoryRegisterHolder) {
		SessionFactoryRegisterContext.sessionFactoryRegisterHolder = sessionFactoryRegisterHolder;
	}
	
	/**
	 * 获取SessionFactoryRegister实例
	 * @return
	 */
	public static final SessionFactoryRegister getSessionFactoryRegister() {
		return sessionFactoryRegisterHolder.getSessionFactoryRegister();
	}
}
