package com.itic.im.spring;

import com.corundumstudio.socketio.AckMode;
import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.annotation.SpringAnnotationScanner;
import com.itic.im.core.ImManager;
import com.itic.im.core.config.ImConfig;
import com.itic.im.core.listener.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.core.annotation.Order;

/**
 * 注册所需要的Bean
 *
 * @author wanli.yang
 *
 */
public class ImBeanRegister {
	private static final Logger logger = LoggerFactory.getLogger(ImBeanRegister.class);
	/**
	 * 获取配置Bean
	 *
	 * @return 配置对象
	 */
	@Bean
	@ConfigurationProperties(prefix = "im.socket")
	public ImConfig getSaTokenConfig() {
		return new ImConfig();
	}

	/**
	 * 注册SocketIOserver bean
	 *
	 * @return 配置对象
	 */
	@Bean
	@ConditionalOnBean(ImConfig.class)
	@Order(1)
	public SocketIOServer socketIOServer(ImConfig imConfig) {
		com.corundumstudio.socketio.Configuration config = new com.corundumstudio.socketio.Configuration();
		config.setHostname(imConfig.getHost());
		config.setPort(imConfig.getPort());
		config.setBossThreads(imConfig.getBossCount());
		config.setWorkerThreads(imConfig.getWorkCount());
		config.setAllowCustomRequests(imConfig.getAllowCustomRequests());
		config.setUpgradeTimeout(imConfig.getUpgradeTimeout());
		config.setMaxFramePayloadLength(imConfig.getMaxFramePayloadLength());
		config.setMaxHttpContentLength(imConfig.getMaxHttpContentLength());
		config.setPingInterval(imConfig.getPingInterval());
		config.setPingTimeout(imConfig.getPingTimeout());
		//默认异常处理
		config.setExceptionListener(new DefaultExceptionListener());
		//手动确认
		config.setAckMode(AckMode.MANUAL);
		// 默认握手协议参数使用认证
		config.setAuthorizationListener(new DefaultAuthorizationListener());
		SocketIOServer socketIOServer = new SocketIOServer(config);
		//  默认命名空间 默认监听
		socketIOServer.addNamespace(ImManager.getConfig().getNamespace());
		socketIOServer.getNamespace(ImManager.getConfig().getNamespace()).addConnectListener(new DefaultConnectListener());
		socketIOServer.getNamespace(ImManager.getConfig().getNamespace()).addDisconnectListener(new DefaultDisConnectListener());
		socketIOServer.getNamespace(ImManager.getConfig().getNamespace()).addPingListener(new DefaultPingListener());// 添加PingListener
		return socketIOServer;
	}

	@Bean
	@ConditionalOnBean(SocketIOServer.class)
	public SpringAnnotationScanner springAnnotationScanner(SocketIOServer socketServer) {
		return new SpringAnnotationScanner(socketServer);
	}
}
