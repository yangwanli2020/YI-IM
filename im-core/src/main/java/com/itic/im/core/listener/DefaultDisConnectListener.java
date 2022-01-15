package com.itic.im.core.listener;

import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.listener.DisconnectListener;
import com.itic.im.core.ImManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 默认断开连接事件
 * date: 2022/1/12 12:39
 * author: wanli.yang
 */
public class DefaultDisConnectListener implements DisconnectListener {

    private static final Logger logger = LoggerFactory.getLogger(DefaultDisConnectListener.class);

    @Override
    public void onDisconnect(SocketIOClient client) {
        //断开连接
        client.disconnect();
        String clientId = client.getHandshakeData().getSingleUrlParam("clientId");
        ImManager.getDao().removeOnline(clientId);
        logger.warn("clientId:{}, sessionId:{} disconnected.", clientId, client.getSessionId());
    }
}
