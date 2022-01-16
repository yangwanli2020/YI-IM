package com.itic.im.core.model;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.UUID;

/**
 * 消息实体
 * date: 2022/1/12 12:28
 * author: wanli.yang
 */
public class DefaultPushMessage implements Serializable {

    private static final long serialVersionUID = -6313420078506387543L;
    /** 客户端ID */
    @ApiModelProperty(value = "客户端ID", required = false)
    private String clientId;
    /** 房间ID */
    @ApiModelProperty(value = "房间ID", required = false)
    private String roomId;
    /** 消息 */
    @ApiModelProperty(value = "消息", required = false)
    private String msg;
    /** 消息类型 */
    @ApiModelProperty(value = "消息类型", required = false)
    private String msgType;
    /** sessionID */
    @ApiModelProperty(hidden = true)
    private UUID sessionId;

    /** 事件名称 */
    @ApiModelProperty(value = "事件名称", required = false)
    private String eventName;

    @ApiModelProperty(value = "排除某个客户端Id")
    private String excludedClientId;

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getRoomId() {
        return roomId;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getMsgType() {
        return msgType;
    }

    public void setMsgType(String msgType) {
        this.msgType = msgType;
    }

    public UUID getSessionId() {
        return sessionId;
    }

    public void setSessionId(UUID sessionId) {
        this.sessionId = sessionId;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public String getExcludedClientId() {
        return excludedClientId;
    }

    public void setExcludedClientId(String excludedClientId) {
        this.excludedClientId = excludedClientId;
    }
}
