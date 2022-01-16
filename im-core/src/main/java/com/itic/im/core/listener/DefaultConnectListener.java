package com.itic.im.core.listener;

import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.listener.ConnectListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * 默认连接事件
 * date: 2022/1/12 12:39
 * author: wanli.yang
 */
public class DefaultConnectListener implements ConnectListener {

    private static final Logger logger = LoggerFactory.getLogger(DefaultConnectListener.class);



    @Override
    public void onConnect(SocketIOClient client) {
        logger.info("{} connected.", client.getSessionId());
    }
}
