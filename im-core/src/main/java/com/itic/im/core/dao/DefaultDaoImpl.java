package com.itic.im.core.dao;

import com.itic.im.core.model.Online;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 持久层默认实现，内存模式
 *
 * @author wanli.yang
 * @version 1.0
 * @date 2022/1/13 11:55
 */
public class DefaultDaoImpl implements Dao {

    /**
     * 在线客户端集合
     */
    public Map<String, Object> onlineMap = new ConcurrentHashMap<String, Object>();

    @Override
    public void setOnline(String clientId, Object o) {
        onlineMap.put(clientId, o);
    }

    @Override
    public Object getOnline(String clientId) {
        return onlineMap.get(clientId);
    }

    @Override
    public void removeOnline(String clientId) {
        onlineMap.remove(clientId);
    }

}
