package com.itic.im.core.dao;

/**
 * 数据持久层DAO接口
 * date: 2022/1/13 11:54
 * author: wanli.yang
 */
public interface Dao {

    /**
     * 缓存当前在线的客户端
     * @param clientId
     * @param object
     * @return void
     */
    void setOnline(String clientId, Object object);

    /**
     * 获取在线客户端
     * @param clientId
     * @return void
     */
    Object getOnline(String clientId);

    /**
     * 移除在线客户端
     * @param clientId
     * @return void
     */
    void removeOnline(String clientId);
}
