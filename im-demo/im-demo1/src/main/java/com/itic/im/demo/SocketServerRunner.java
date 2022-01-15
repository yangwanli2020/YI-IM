package com.itic.im.demo;

import com.corundumstudio.socketio.SocketIOServer;
import com.itic.im.core.ImManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import javax.annotation.PreDestroy;

/**
 * SocketIOServer启动器
 * @author wanli.yang
 */
@Component
public class SocketServerRunner implements CommandLineRunner {

    private static final Logger logger = LoggerFactory.getLogger(SocketServerRunner.class);

//    @Autowired
//    private RedisMessageListenerContainer redisMessageListenerContainer;


    @Override
    public void run(String... args) {
        //  添加自定义监听
        // ....
        //  启动
        ImManager.start();
        SocketIOServer socketIOServer = ImManager.getSocketIOServer();

        logger.warn("IM实时通讯系统，启动成功.");
    }

    @PreDestroy
    public void stop() {
        ImManager.stop();
    }
}
