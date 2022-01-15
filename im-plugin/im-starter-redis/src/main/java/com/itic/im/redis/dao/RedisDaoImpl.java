package com.itic.im.redis.dao;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.itic.im.core.ImManager;
import com.itic.im.core.constants.IMConstants;
import com.itic.im.core.dao.Dao;
import com.itic.im.core.model.Online;
import com.itic.im.redis.util.RedisTemplateUtil;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 持久层默认实现，内存模式
 *
 * @author wanli.yang
 * @version 1.0
 * @date 2022/1/13 11:55
 */
public class RedisDaoImpl implements Dao {

    @Autowired
    private RedisTemplateUtil redisTemplateUtil;

//    /**
//     * 构造函数
//     */
//    public RedisDaoImpl() {
//        initRefreshThread();
//    }

    @Override
    public void setOnline(String clientId, Object o) {
        Online online = (Online) o;
        redisTemplateUtil.put(IMConstants.REDIS_IM_ONLINE_USER_PREFIX, online.getClientId(), JSONUtil.toJsonStr(online));
    }

    @Override
    public Object getOnline(String clientId) {
        String mapString = redisTemplateUtil.getMapString(IMConstants.REDIS_IM_ONLINE_USER_PREFIX, clientId);
        if(StrUtil.isEmpty(mapString))
            return null;
        return JSONUtil.toBean(mapString, Online.class);
    }

    @Override
    public void removeOnline(String clientId) {
        redisTemplateUtil.delete(IMConstants.REDIS_IM_ONLINE_USER_PREFIX, clientId);
    }

//    /**
//     * 执行数据清理的线程
//     */
//    public Thread refreshThread;
//
//    /**
//     * 是否继续执行数据清理的线程标记
//     */
//    public volatile boolean refreshFlag;
//
//    /**
//     * 初始化定时任务
//     */
//    public void initRefreshThread() {
//
//        // 如果配置了<=0的值，则不启动定时清理
//        if(ImManager.getConfig().getDataRefreshPeriod() <= 0) {
//            return;
//        }
//        // 启动定时刷新
//        this.refreshFlag = true;
//        this.refreshThread = new Thread(() -> {
//            for (;;) {
//                try {
//                    try {
//                        // 如果已经被标记为结束
//                        if(refreshFlag == false) {
//                            return;
//                        }
//                        // 执行清理
////                        refreshDataMap();
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }
//                    // 休眠N秒
//                    long dataRefreshPeriod = ImManager.getConfig().getDataRefreshPeriod();
//                    if(dataRefreshPeriod <= 0) {
//                        dataRefreshPeriod = 1;
//                    }
//                    Thread.sleep(dataRefreshPeriod * 1000);
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
//        });
//        this.refreshThread.start();
//    }
//
//    /**
//     * 结束定时任务
//     */
//    public void endRefreshThread() {
//        this.refreshFlag = false;
//    }
}
