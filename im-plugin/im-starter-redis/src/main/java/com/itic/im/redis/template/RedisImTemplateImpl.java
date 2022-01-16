package com.itic.im.redis.template;

import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.extra.spring.SpringUtil;
import cn.hutool.json.JSONUtil;
import com.itic.im.core.ImManager;
import com.itic.im.core.exception.ImException;
import com.itic.im.core.template.ImTemplate;
import com.itic.im.core.util.SoMap;
import com.itic.im.redis.listener.ImMessageListener;
import com.itic.im.redis.util.RedisTemplateUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.listener.PatternTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 消息通讯模板 redis实现
 * date: 2022/1/13 13:55
 * author: yangwl
 */
public class RedisImTemplateImpl extends ImTemplate{

    private static final Logger logger = LoggerFactory.getLogger(RedisImTemplateImpl.class);
    public static ExecutorService fixedThreadPool = Executors.newFixedThreadPool(20);
    @Autowired
    private RedisTemplateUtil redisTemplateUtil;
    @Autowired
    private RedisMessageListenerContainer redisMessageListenerContainer;
    /**
     * room - channel
     */
    public static Map<String, String> channelMap = new ConcurrentHashMap<String, String>();

    @Override
    public void send(Object o) {
        if(null == o)
            throw new ImException("data is null");
        SoMap data = (SoMap) o;
        String roomId = data.getString("roomId");
        if(StrUtil.isEmpty(roomId))
            throw new ImException("roomId can not be null");

        fixedThreadPool.execute(new Runnable() {
            @Override
            public void run() {
                // 将消息广播到redis频道
                redisTemplateUtil.pub(getChannel(roomId), JSONUtil.toJsonStr(data));
            }
        });
    }

    public static void createChannel(String roomId, String channel) {
        channelMap.put(roomId, channel);
    }

    public static String getChannel(String roomId) {
        if(StrUtil.isEmpty(channelMap.get(roomId))) {
            synchronized (RedisImTemplateImpl.class) {
                if(StrUtil.isEmpty(channelMap.get(roomId))) {
                    // 如果是默认房间，就启用默认频道
                    String channel = roomId.equals(ImManager.getConfig().getRoomId())?ImManager.getConfig().getChannel(): RandomUtil.randomString(10);
                    createChannel(roomId, channel);
                    RedisMessageListenerContainer rmls = SpringUtil.getBean(RedisMessageListenerContainer.class);
                    RedisTemplateUtil rtu = SpringUtil.getBean(RedisTemplateUtil.class);
                    rmls.addMessageListener(new ImMessageListener(rtu), new PatternTopic(channel));
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    logger.warn("IM实时通讯Gate系统，房间{}监听频道{}成功.", roomId, channel);
                }
            }
        }
        return channelMap.get(roomId);
    }

    public static void removeChannel(String roomId) {
        channelMap.remove(roomId);
    }
}
