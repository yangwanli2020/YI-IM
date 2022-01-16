package com.itic.im.core.listener;

import cn.hutool.core.util.StrUtil;
import com.corundumstudio.socketio.AckRequest;
import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.listener.DataListener;
import com.itic.im.core.ImManager;
import com.itic.im.core.constants.IMConstants;
import com.itic.im.core.model.Online;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Set;

/**
 * 默认注册事件
 * 1. 缓存当前客户端
 * date: 2022/1/12 12:39
 * author: wanli.yang
 */
public class DefaultRegisterListener implements DataListener {
    private static final Logger logger = LoggerFactory.getLogger(DefaultRegisterListener.class);
    @Override
    public void onData(SocketIOClient client, Object data, AckRequest ackRequest) throws Exception {
        Online online = null;
        if(null != data) {
            online = (Online) data;
        }

        // 标记当前客户端在线
        online.setState(Online.ONLINE);

        // 设置sessionid
        online.setSessionId(client.getSessionId());

        // 缓存当前用户, 根据businessId维护
        ImManager.getDao().setOnline(online.getClientId(), online);

        // 加入全局room
        client.joinRoom(ImManager.getConfig().getRoomId());

        if (ackRequest.isAckRequested()) {
            Online o = (Online) ImManager.getDao().getOnline(online.getClientId());
            ackRequest.sendAckData(client.getSessionId(), o.getClientId());
        }
        logger.info("{} registed.", client.getSessionId());
    }


}
