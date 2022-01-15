package com.itic.im.core.listener;

import com.corundumstudio.socketio.AuthorizationListener;
import com.corundumstudio.socketio.HandshakeData;

/**
 * 默认的认证监听， 可以继承实现认证
 *
 * @author wanli.yang
 * @version 1.0
 * @date 2022/1/13 9:48
 */
public class DefaultAuthorizationListener implements AuthorizationListener {
    @Override
    public boolean isAuthorized(HandshakeData data) {
        return true;
    }
}
