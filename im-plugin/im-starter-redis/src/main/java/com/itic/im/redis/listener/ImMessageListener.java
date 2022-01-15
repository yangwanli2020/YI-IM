package com.itic.im.redis.listener;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.corundumstudio.socketio.BroadcastAckCallback;
import com.corundumstudio.socketio.BroadcastOperations;
import com.corundumstudio.socketio.SocketIOClient;
import com.itic.im.core.ImManager;
import com.itic.im.core.model.Online;
import com.itic.im.core.util.SoMap;
import com.itic.im.redis.template.RedisImTemplateImpl;
import com.itic.im.redis.util.RedisTemplateUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;

/**
 * @author wanli.yang
 * @version 1.0
 * @date 2022/1/13 18:22
 */
public class ImMessageListener implements MessageListener {

    private static final Logger logger = LoggerFactory.getLogger(ImMessageListener.class);

    private RedisTemplateUtil redisTemplateUtil;
    public ImMessageListener(RedisTemplateUtil redisTemplateUtil) {
        this.redisTemplateUtil = redisTemplateUtil;
    }

    @Override
    public void onMessage(Message message, byte[] pattern) {
        SoMap map = JSONUtil.toBean((String) redisTemplateUtil.valueDeserialize(message.getBody()), SoMap.class);

        BroadcastOperations bo = ImManager.defaultNamespace().getRoomOperations(map.getString("roomId"));
        BroadcastAckCallback<String> broadcastAckCallback = new BroadcastAckCallback<String>(String.class) {
            @Override
            public void onClientSuccess(SocketIOClient client, String result) {
//                logger.info("客户端{}发送成功，返回值={}", client.getSessionId(), result);
            }
            @Override
            public void onClientTimeout(SocketIOClient client) {
                logger.info("客户端{}, 发送超时，第{}次重试...", client.getSessionId(), 1);
                ImManager.getImTemplate().retry(client, map, 1);
            }
        };
        String excludedClientId = map.getString("excludedClientId");
        if(StrUtil.isNotEmpty(excludedClientId)) {
            Online online = (Online) ImManager.getDao().getOnline(excludedClientId);
            SocketIOClient client = ImManager.defaultNamespace().getClient(online.getSessionId());
            bo.sendEvent(map.getString("eventName"), map, client, broadcastAckCallback);
        } else {
            bo.sendEvent(map.getString("eventName"), map, broadcastAckCallback);
        }
    }
}
