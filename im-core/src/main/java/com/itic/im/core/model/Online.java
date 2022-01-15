package com.itic.im.core.model;


import java.io.Serializable;
import java.util.UUID;

public class Online implements Serializable {

    private static final long serialVersionUID = 7140272311329240721L;

    public static final String ONLINE="online";
    public static final String OFFLINE="offline";


    private String clientId;
    private String roomId;
    private String state;
    private UUID sessionId;

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getRoomId() {
        return roomId;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }

    public UUID getSessionId() {
        return sessionId;
    }

    public void setSessionId(UUID sessionId) {
        this.sessionId = sessionId;
    }
}
