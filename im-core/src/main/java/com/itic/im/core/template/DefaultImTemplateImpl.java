package com.itic.im.core.template;

import cn.hutool.core.util.StrUtil;
import com.corundumstudio.socketio.BroadcastAckCallback;
import com.corundumstudio.socketio.BroadcastOperations;
import com.corundumstudio.socketio.SocketIOClient;
import com.itic.im.core.ImManager;
import com.itic.im.core.exception.ImException;
import com.itic.im.core.model.Online;
import com.itic.im.core.util.SoMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 消息通讯模板默认失效
 * date: 2022/1/13 13:55
 * author: yangwl
 */
public class DefaultImTemplateImpl extends ImTemplate {

    private static final Logger logger = LoggerFactory.getLogger(DefaultImTemplateImpl.class);

    @Override
    public void send(Object o) {
        if(null == o)
            throw new ImException("data is null");
        SoMap data = (SoMap) o;
        BroadcastOperations bo = ImManager.defaultNamespace().getRoomOperations(data.getString("roomId"));
        BroadcastAckCallback<String> broadcastAckCallback = new BroadcastAckCallback<String>(String.class) {
            @Override
            public void onClientSuccess(SocketIOClient client, String result) {
                logger.info("客户端{}发送成功，返回值={}", client.getSessionId(), result);
            }
            @Override
            public void onClientTimeout(SocketIOClient client) {
                logger.info("客户端{}, 发送超时，第{}次重试...", client.getSessionId(), 1);
                retry(client, data, 1);
            }
        };

        // 排除客户端
        String excludedClientId = data.getString("excludedClientId");
        if(StrUtil.isNotEmpty(excludedClientId)) {
            Online online = (Online) ImManager.getDao().getOnline(excludedClientId);
            SocketIOClient client = ImManager.defaultNamespace().getClient(online.getSessionId());
            bo.sendEvent(data.getString("eventName"), data, client, broadcastAckCallback);
        } else {
            bo.sendEvent(data.getString("eventName"), data, broadcastAckCallback);
        }
    }
}
