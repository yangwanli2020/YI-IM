package com.itic.im.demo.listener;

import com.corundumstudio.socketio.AckRequest;
import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.annotation.OnEvent;
import com.itic.im.core.ImManager;
import com.itic.im.core.constants.IMConstants;
import com.itic.im.core.handler.DefaultEventHandler;
import com.itic.im.core.util.SoMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * 消息事件处理器
 * date: 2022/1/12 12:28
 * author: wanli.yang
 */
@Component
public class ImEventHandler extends DefaultEventHandler {
    private static final Logger logger = LoggerFactory.getLogger(ImEventHandler.class);
}
