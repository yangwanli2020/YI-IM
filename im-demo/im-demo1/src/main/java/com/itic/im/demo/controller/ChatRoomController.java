package com.itic.im.demo.controller;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import com.itic.im.core.model.R;
import com.itic.im.demo.model.GroupInfo;
import com.itic.im.demo.model.UserGroups;
import com.itic.im.demo.model.UserInfo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpSession;
import java.util.*;
import java.util.stream.Collectors;

/**
 * TODO
 *
 * @author wanli.yang
 * @version 1.0
 * @date 2022/1/13 10:18
 */
@Controller
public class ChatRoomController {

    public static List<UserInfo> userInfos = new ArrayList<UserInfo>();
    public static List<GroupInfo> groupInfos = new ArrayList<GroupInfo>();

    @GetMapping("/index")
    public String index() {
        return "index";
    }

    @GetMapping(value = {"/login", "/"})
    public String toLogin() {
        return "login";
    }

    @PostMapping(value = "/login")
    @ResponseBody
    public R login(HttpSession session, @RequestParam String username, @RequestParam String password) {
        List<UserInfo> l = userInfos.stream().filter(e -> e.getUsername().equals(username)).collect(Collectors.toList());
        if (CollectionUtil.isEmpty(l)) {
            return R.fail("不存在该用户名");
        }
        if (!l.get(0).getPassword().equals(password)) {
            return R.fail("密码不正确");
        }
        session.setAttribute("userId", l.get(0).getUserId());
        return R.ok();
    }

    /**
     * 描述：登录成功后，调用此接口进行页面跳转
     * @return
     */
    @GetMapping("/chatroom")
    public String toChatroom() {
        return "chatroom";
    }

    /**
     * 描述：登录成功跳转页面后，调用此接口获取用户信息
     * @return
     */
    @RequestMapping(value = "/get_userinfo", method = RequestMethod.POST)
    @ResponseBody
    public R getUserInfo(HttpSession session) {
        Object userId = session.getAttribute("userId");
        UserInfo userInfo = getByUserId((String)userId);
        if(null != userInfo) {
            // 设置群组
            List<GroupInfo> gl = new ArrayList<>();
            groupInfos.stream().forEach(e -> {
                List<UserInfo> collect = e.getMembers().stream().filter(m -> m.getUserId().equals(userInfo.getUserId())).collect(Collectors.toList());
                if(CollectionUtil.isNotEmpty(collect)) {
                    gl.add(e);
                }
            });
            return R.ok(new UserGroups(userInfo, gl));
        }
        return R.ok();
    }

    public UserInfo getByUserId(String userId) {
        List<UserInfo> l = userInfos.stream().filter(e -> e.getUserId().equals(userId)).collect(Collectors.toList());
        if (CollectionUtil.isEmpty(l)) {
            return null;
        }
        return l.get(0);
    }

    @PostConstruct
    public void init() {
        generateUserInfoList();
        generateGroups();
    }

    private void generateGroups() {
        int i = 2;
        // 设置用户群列表，共1个群
        GroupInfo groupInfo = new GroupInfo("01", "Group01", 1, "/static/img/avatar/Group01.jpg", userInfos);
        groupInfos.add(groupInfo);
        // 剩下得都是私聊
        for(int m = 0; m < userInfos.size()-1; m++) {
            for(int n = m+1; n < userInfos.size(); n++) {
                List<UserInfo> t = new ArrayList<UserInfo>();
                t.add(userInfos.get(m));
                t.add(userInfos.get(n));
                groupInfos.add(new GroupInfo("0"+i++, "Group0"+i, 2, t));
            }
        }
    }
    private void generateUserInfoList() {
        UserInfo userInfo = new UserInfo("001", "Member001", "001", "/static/img/avatar/Member001.jpg");
        UserInfo userInfo2 = new UserInfo("002", "Member002", "002", "/static/img/avatar/Member002.jpg");
        UserInfo userInfo3 = new UserInfo("003", "Member003", "003", "/static/img/avatar/Member003.jpg");
        UserInfo userInfo4 = new UserInfo("004", "Member004", "004", "/static/img/avatar/Member004.jpg");
        UserInfo userInfo5 = new UserInfo("005", "Member005", "005", "/static/img/avatar/Member005.jpg");
        UserInfo userInfo6 = new UserInfo("006", "Member006", "006", "/static/img/avatar/Member006.jpg");
        UserInfo userInfo7 = new UserInfo("007", "Member007", "007", "/static/img/avatar/Member007.jpg");
        UserInfo userInfo8 = new UserInfo("008", "Member008", "008", "/static/img/avatar/Member008.jpg");
        UserInfo userInfo9 = new UserInfo("009", "Member009", "009", "/static/img/avatar/Member009.jpg");
        userInfos.add(userInfo);
        userInfos.add(userInfo2);
        userInfos.add(userInfo3);
        userInfos.add(userInfo4);
        userInfos.add(userInfo5);
        userInfos.add(userInfo6);
        userInfos.add(userInfo7);
        userInfos.add(userInfo8);
        userInfos.add(userInfo9);
    }
}
