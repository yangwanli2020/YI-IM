package com.itic.im.core.handler;

import cn.hutool.core.util.StrUtil;
import com.corundumstudio.socketio.AckRequest;
import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.annotation.OnEvent;
import com.itic.im.core.ImManager;
import com.itic.im.core.constants.IMConstants;
import com.itic.im.core.model.DefaultPushMessage;
import com.itic.im.core.util.SoMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Set;

/**
 * 抽象消息事件处理器
 * date: 2022/1/12 12:28
 * author: wanli.yang
 */
public class DefaultEventHandler {
    private static final Logger logger = LoggerFactory.getLogger(DefaultEventHandler.class);

    /**
     * 消息推送 自定义事件
     *
     * @param client
     * @param request
     * @param data
     */
    @OnEvent(IMConstants.SEND_MSG_EVENT)
    public void onSendMsgEvent(SocketIOClient client, AckRequest request, SoMap data) {
        // 事件名称
        data.put("eventName", IMConstants.SEND_MSG_EVENT);
        // 排除自己
        data.put("excludedClientId", data.getString("clientId"));
        ImManager.getImTemplate().handler(data);
        if (request.isAckRequested()) {
            request.sendAckData("200");
        }
    }

    /**
     * 加入房间事件
     *
     * @param client
     * @param request
     * @param data
     */
    @OnEvent(IMConstants.JOIN_ROOM_EVENT)
    public void onJoinRoomEvent(SocketIOClient client, AckRequest request, SoMap data) {
        // 加入指定的房间
        if(StrUtil.isNotEmpty(data.getString("roomId"))) {
            Set<String> allRooms = client.getAllRooms();
            if(!ifJoinRoom(allRooms, data.getString("roomId"))) {
                client.joinRoom(data.getString("roomId"));
            }
        }
        if (request.isAckRequested()) {
            request.sendAckData( "200");
        }
    }

    /**
     * 离开房间事件
     *
     * @param client
     * @param request
     * @param data
     */
    @OnEvent(IMConstants.LEAVE_ROOM_EVENT)
    public void onLeaveRoomEvent(SocketIOClient client, AckRequest request, DefaultPushMessage data) {
        client.leaveRoom(data.getRoomId());
    }


    private boolean ifJoinRoom(Set<String> allRooms, String roomId) {
        boolean flag = false;
        for(String e : allRooms) {
            if(StrUtil.isNotBlank(e)) {
                if(e.equals(roomId)) {
                    return true;
                }
            }
        }

        return flag;
    }
}
