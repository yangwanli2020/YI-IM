package com.itic.im.core.handler;

import com.itic.im.core.util.SoMap;

/**
 * 消息处理扩展点
 * date: 2022/1/15 22:11
 * author: yangwl
 */
public abstract class Handler {

    public void pre(Object o){}

    public void after(Object o){}
}
