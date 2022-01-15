package com.itic.im.core.config;

import com.itic.im.core.constants.IMConstants;

import java.io.Serializable;

/**
 * socket 配置类 Model
 *
 * @author wanli.yang
 * @version 1.0
 * @date 2022/1/12 12:44
 */
public class ImConfig implements Serializable {
    private static final long serialVersionUID = -6541180061782004705L;

    /** 主机 */
    private String host = "localhost";

    /** 端口 */
    private int port = 9901;

    /** maximum websocket frame content length limit */
    private int maxFramePayloadLength = 64 * 1024;

    /** maximum http content length limit */
    private int  maxHttpContentLength = 64 * 1024;

    /** netty 主线程数量 */
    private int bossCount = 1;

    /** netty工作线程数量*/
    private int workCount = 20;

    /** 是否允许自定义请求 */
    private Boolean allowCustomRequests = false;

    /** 协议升级超时时间（毫秒），默认10000。HTTP握手升级为ws协议超时时间 */
    private int upgradeTimeout = 10000;

    /** Ping timeout */
    private int pingTimeout = 60000;

    /** Ping interval */
    private int pingInterval = 25000;

    /** 默认命名空间 */
    private String namespace = IMConstants.IM_DEFAULT_NAMESPACE;

    /** 默认房间 */
    private String roomId = IMConstants.IM_DEFAULT_ROOM;
    /** 默认频道 */
    private String channel = IMConstants.IM_DEFAULT_CHANNEL;

    /** 消息发送失败时，重试次数 */
    private int retry = 3;

    /** 定时清理数据周期 */
    private long dataRefreshPeriod = 30;

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public int getMaxFramePayloadLength() {
        return maxFramePayloadLength;
    }

    public void setMaxFramePayloadLength(int maxFramePayloadLength) {
        this.maxFramePayloadLength = maxFramePayloadLength;
    }

    public int getMaxHttpContentLength() {
        return maxHttpContentLength;
    }

    public void setMaxHttpContentLength(int maxHttpContentLength) {
        this.maxHttpContentLength = maxHttpContentLength;
    }

    public int getBossCount() {
        return bossCount;
    }

    public void setBossCount(int bossCount) {
        this.bossCount = bossCount;
    }

    public int getWorkCount() {
        return workCount;
    }

    public void setWorkCount(int workCount) {
        this.workCount = workCount;
    }

    public Boolean getAllowCustomRequests() {
        return allowCustomRequests;
    }

    public void setAllowCustomRequests(Boolean allowCustomRequests) {
        this.allowCustomRequests = allowCustomRequests;
    }

    public int getUpgradeTimeout() {
        return upgradeTimeout;
    }

    public void setUpgradeTimeout(int upgradeTimeout) {
        this.upgradeTimeout = upgradeTimeout;
    }

    public int getPingTimeout() {
        return pingTimeout;
    }

    public void setPingTimeout(int pingTimeout) {
        this.pingTimeout = pingTimeout;
    }

    public int getPingInterval() {
        return pingInterval;
    }

    public void setPingInterval(int pingInterval) {
        this.pingInterval = pingInterval;
    }

    public String getNamespace() {
        return namespace;
    }

    public void setNamespace(String namespace) {
        this.namespace = namespace;
    }

    public int getRetry() {
        return retry;
    }

    public void setRetry(int retry) {
        this.retry = retry;
    }


    public String getRoomId() {
        return roomId;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }

    public long getDataRefreshPeriod() {
        return dataRefreshPeriod;
    }

    public void setDataRefreshPeriod(long dataRefreshPeriod) {
        this.dataRefreshPeriod = dataRefreshPeriod;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }
}
