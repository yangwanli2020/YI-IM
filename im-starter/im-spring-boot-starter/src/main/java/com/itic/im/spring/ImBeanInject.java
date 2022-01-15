package com.itic.im.spring;


import com.corundumstudio.socketio.AuthorizationListener;
import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.listener.ConnectListener;
import com.corundumstudio.socketio.listener.DisconnectListener;
import com.corundumstudio.socketio.listener.ExceptionListener;
import com.corundumstudio.socketio.listener.PingListener;
import com.itic.im.core.ImManager;
import com.itic.im.core.config.ImConfig;
import com.itic.im.core.dao.Dao;
import com.itic.im.core.handler.DefaultEventHandler;
import com.itic.im.core.handler.Handler;
import com.itic.im.core.listener.DefaultRegisterListener;
import com.itic.im.core.template.ImTemplate;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 注入所需要的Bean
 *
 * @author wanli.yang
 */
public class ImBeanInject {

	/**
	 * 注入配置Bean
	 *
	 * @param config 配置对象
	 */
	@Autowired(required = false)
	public void setConfig(ImConfig config) {
		ImManager.setConfig(config);
	}

	/**
	 * 注入持久化Bean
	 *
	 * @param dao Dao对象
	 */
	@Autowired(required = false)
	public void setDao(Dao dao) {
		ImManager.setDao(dao);
	}

	/**
	 * 注入消息处理模板Bean
	 *
	 * @param imTemplate Dao对象
	 */
	@Autowired(required = false)
	public void setImTemplate(ImTemplate imTemplate) {
		ImManager.setImTemplate(imTemplate);
	}

	/**
	 * 注入socketIOServerBean
	 *
	 * @param socketIOServer 配置对象
	 */
	@Autowired(required = false)
	public void setSocketIOServer(SocketIOServer socketIOServer) {
		ImManager.setSocketIOServer(socketIOServer);
	}

	/**
	 * 注入一些监听
	 *
	 * @param connectListener
	 */
	@Autowired(required = false)
	public void connectListener(ConnectListener connectListener) {
		ImManager.setConnectListener(connectListener);
	}

	@Autowired(required = false)
	public void disConnectListener(DisconnectListener disconnectListener) {
		ImManager.setDisconnectListener(disconnectListener);
	}

	@Autowired(required = false)
	public void registerListener(DefaultRegisterListener dataListener) {
		ImManager.setRegisterListener(dataListener);
	}

	@Autowired(required = false)
	public void pingListener(PingListener pingListener) {
		ImManager.setPingListener(pingListener);
	}

	@Autowired(required = false)
	public void exceptionListener(ExceptionListener exceptionListener) {
		ImManager.setExceptionListener(exceptionListener);
	}

	@Autowired(required = false)
	public void authorizationListener(AuthorizationListener authorizationListener) {
		ImManager.setAuthorizationListener(authorizationListener);
	}

	@Autowired(required = false)
	public void eventHandler(DefaultEventHandler eventHandler) {
		ImManager.setDefaultEventHandler(eventHandler);
	}

	@Autowired(required = false)
	public void handler(Handler handler) {
		ImManager.setHandler(handler);
	}
}
