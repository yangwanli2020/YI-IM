package com.itic.im.core;

import cn.hutool.extra.spring.SpringUtil;
import com.corundumstudio.socketio.AuthorizationListener;
import com.corundumstudio.socketio.SocketIONamespace;
import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.listener.*;
import com.itic.im.core.config.ImConfig;
import com.itic.im.core.constants.IMConstants;
import com.itic.im.core.dao.Dao;
import com.itic.im.core.dao.DefaultDaoImpl;
import com.itic.im.core.exception.ImException;
import com.itic.im.core.handler.DefaultEventHandler;
import com.itic.im.core.handler.DefaultHandler;
import com.itic.im.core.handler.Handler;
import com.itic.im.core.listener.DefaultRegisterListener;
import com.itic.im.core.model.Online;
import com.itic.im.core.template.DefaultImTemplateImpl;
import com.itic.im.core.template.ImTemplate;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * IM管理器
 *
 * @author wanli.yang
 * @version 1.0
 * @date 2022/1/12 12:43
 */
@SuppressWarnings("deprecation")
public class ImManager {

    /**
     * 配置文件 Bean
     */
    private volatile static ImConfig config;
    public static void setConfig(ImConfig config) {
        ImManager.config = config;
    }
    public static ImConfig getConfig() {
        if (config == null) {
            synchronized (ImManager.class) {
                ImConfig b = SpringUtil.getBean(ImConfig.class);
                if(null == b) {
                    if (config == null) {
                        config = new ImConfig();
                    }
                } else {
                    config = SpringUtil.getBean(ImConfig.class);
                }
            }
        }
        return config;
    }

    /**
     * socket服务
     */
    private volatile static SocketIOServer socketIOServer;
    public static SocketIOServer getSocketIOServer() {
        if (socketIOServer == null) {
            synchronized (ImManager.class) {
                if(null == socketIOServer) {
                    socketIOServer = SpringUtil.getBean(SocketIOServer.class);
                    if(null == socketIOServer) {
                        throw  new ImException("socketIOServer is null");
                    }
                }
            }
        }
        return socketIOServer;
    }
    public static void setSocketIOServer(SocketIOServer socketIOServer) {
        if(null == ImManager.socketIOServer) {
            ImManager.socketIOServer = socketIOServer;
        }
    }

    /**
     *  添加命名空间
     */
    public static SocketIONamespace addNamespace(String n) {
        return ImManager.getSocketIOServer().addNamespace(n);
    }
    /**
     *  获取默认命名空间
     */
    public static SocketIONamespace defaultNamespace() {
        SocketIONamespace namespace = ImManager.getSocketIOServer().getNamespace(ImManager.getConfig().getNamespace());
        return namespace;
    }

    /**
     *  获取命名空间
     */
    public static SocketIONamespace getNamespace(String n) {
        return ImManager.getSocketIOServer().getNamespace(n);
    }

    /**
     * 持久化 Bean
     */
    private volatile static Dao dao;
    public static void setDao(Dao dao) {
        ImManager.dao = dao;
    }
    public static Dao getDao() {
        if (dao == null) {
            synchronized (ImManager.class) {
                if (dao == null) {
                    setDao(new DefaultDaoImpl());
                }
            }
        }
        return dao;
    }

    /**
     * 消息通讯template
     */
    private volatile static ImTemplate imTemplate;
    public static void setImTemplate(ImTemplate imTemplate) {
        ImManager.imTemplate = imTemplate;
    }
    public static ImTemplate getImTemplate() {
        if (imTemplate == null) {
            synchronized (ImManager.class) {
                if (imTemplate == null) {
                    setImTemplate(new DefaultImTemplateImpl());
                }
            }
        }
        return imTemplate;
    }

    /**
     * 消息持久化处理器
     */
    private volatile static Handler handler;
    public static void setHandler(Handler handler) {
        ImManager.handler = handler;
    }
    public static Handler getHandler() {
        if (handler == null) {
            synchronized (ImManager.class) {
                if (handler == null) {
                    setHandler(new DefaultHandler());
                }
            }
        }
        return handler;
    }

    /**
     * 连接监听
     */

    public static void setConnectListener(ConnectListener connectListener) {
        defaultNamespace().addConnectListener(connectListener);
    }

    /**
     * 连接断开监听
     */
    public static void setDisconnectListener(DisconnectListener disconnectListener) {
        defaultNamespace().addDisconnectListener(disconnectListener);
    }

    /**
     * 连接注册监听
     */
    private static volatile  DataListener registerListener;
    public static void setRegisterListener(DataListener registerListener) {
        ImManager.registerListener = registerListener;
    }
    /**
     * 全局事件监听
     */
    private volatile static DefaultEventHandler eventHandler;
    public static void setDefaultEventHandler(DefaultEventHandler eventHandler) {
        ImManager.eventHandler = eventHandler;
    }

    /**
     * ping监听
     */
    public static void setPingListener(PingListener pingListener) {
        defaultNamespace().addPingListener(pingListener);
    }

    /**
     * 异常监听
     */
    public static void setExceptionListener(ExceptionListener exceptionListener) {
        getSocketIOServer().getConfiguration().setExceptionListener(exceptionListener);
    }

    /**
     * 认证监听
     */
    public static void setAuthorizationListener(AuthorizationListener authorizationListener) {
        getSocketIOServer().getConfigCopy().setAuthorizationListener(authorizationListener);
    }

    /**
     * 启动
     */
    public static void start() {

        // 设置注册监听
        if(null == ImManager.registerListener) {
            ImManager.registerListener = new DefaultRegisterListener();
        }
        defaultNamespace().addEventListener(IMConstants.REGISTER_EVENT, Online.class, ImManager.registerListener); // 注册事件监听
        // 设置全局事件监听
        if(null == ImManager.eventHandler) {
            ImManager.eventHandler = new DefaultEventHandler();
        }
        defaultNamespace().addListeners(ImManager.eventHandler);
        // 启动
        getSocketIOServer().start();
    }

    /**
     * 停止
     */
    public static void stop() {
        try {
           getSocketIOServer().stop();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
