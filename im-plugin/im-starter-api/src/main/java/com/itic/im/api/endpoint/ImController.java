package com.itic.im.api.endpoint;

import com.itic.im.core.ImManager;
import com.itic.im.core.model.DefaultPushMessage;
import com.itic.im.core.model.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *  实时通讯API
 * @author wanli.yang
 * @version 1.0
 * @date 2022/1/13 13:36
 */
@Api(tags = "实时通讯")
@RestController
@RequestMapping("/im")
public class ImController {

    @ApiOperation("发送消息")
    @PostMapping("/sendMsg")
    public R sendMsg(DefaultPushMessage pushMessage) {
        ImManager.getImTemplate().handler(pushMessage);
        return R.ok();
    }
}
