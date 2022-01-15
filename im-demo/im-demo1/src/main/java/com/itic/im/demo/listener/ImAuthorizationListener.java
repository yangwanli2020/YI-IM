package com.itic.im.demo.listener;

import com.corundumstudio.socketio.AuthorizationListener;
import com.corundumstudio.socketio.HandshakeData;
import org.springframework.stereotype.Component;

/**
 * 认证监听
 * @author wanli.yang
 * @version 1.0
 * @date 2022/1/13 9:55
 */
@Component
public class ImAuthorizationListener implements AuthorizationListener {
    @Override
    public boolean isAuthorized(HandshakeData data) {
        // 实现自己得监听逻辑
        return true;
    }
}
