package com.itic.im.core.template;

import com.corundumstudio.socketio.AckCallback;
import com.corundumstudio.socketio.SocketIOClient;
import com.itic.im.core.ImManager;
import com.itic.im.core.constants.IMConstants;
import com.itic.im.core.util.SoMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 消息通讯模板
 * date: 2022/1/13 13:55
 * author: yangwl
 */
public abstract class ImTemplate {
    private static final Logger logger = LoggerFactory.getLogger(ImTemplate.class);

    /**
     * 发送消息
     * @param o
     * @return void
     */
    public void handler(Object o) {
        ImManager.getHandler().pre(o);
        send(o);
        ImManager.getHandler().after(o);
    }

    /**
     * 发送消息
     * @param o
     * @return void
     */
    public abstract void send(Object o);

    /**
     * 消息发送重试
     * @param client
     * @param data
     * @param time
     * @return void
     */
    public void retry(SocketIOClient client, SoMap data, int time) {
        if(time >= ImManager.getConfig().getRetry()) return;
        client.sendEvent(IMConstants.SEND_MSG_EVENT, new AckCallback<String>(String.class) {
            @Override
            public void onSuccess(String result) {
                logger.info("客户端{}发送成功，返回值={}", result);
            }
            @Override
            public void onTimeout() {
                logger.info("客户端{}, 发送超时，第{}次重试...", client.getSessionId(), time);
                retry(client, data, time + 1);
            }
        }, data);
    }
}
