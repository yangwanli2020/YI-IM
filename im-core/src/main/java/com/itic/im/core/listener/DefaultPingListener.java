package com.itic.im.core.listener;

import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.listener.PingListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * 默认Ping事件
 * date: 2022/1/12 12:39
 * author: wanli.yang
 */
public class DefaultPingListener implements PingListener {

    private static final Logger logger = LoggerFactory.getLogger(DefaultPingListener.class);

    @Override
    public void onPing(SocketIOClient client) {
        logger.info("{} ping...", client.getSessionId());
    }
}
