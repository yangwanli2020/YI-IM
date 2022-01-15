package com.itic.im.demo.handler;

import com.itic.im.core.handler.Handler;
import org.springframework.stereotype.Component;

/**
 * @author wanli.yang
 * @version 1.0
 * @date 2022/1/15 22:12
 */
@Component
public class ImHandler extends Handler {

    @Override
    public void after(Object o) {
        // 调用父类方法， 父类方法中o又默认插入数据库操作，如果想自定义插入或不插入不调用即可
        super.after(o);
        // 其他操作
    }
}
