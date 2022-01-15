package com.itic.im.demo.model;

import java.io.Serializable;
import java.util.List;

/**
 * TODO
 *
 * @author wanli.yang
 * @version 1.0
 * @date 2022/1/14 16:50
 */
public class UserGroups implements Serializable {
    private static final long serialVersionUID = 2723340248786952662L;

    private UserInfo userInfo;
    private List<GroupInfo> groupInfos;

    public UserGroups() {

    }
    public UserGroups(UserInfo userInfo, List<GroupInfo> groupInfos) {
    this.userInfo = userInfo;
    this.groupInfos = groupInfos;
    }
    public UserInfo getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(UserInfo userInfo) {
        this.userInfo = userInfo;
    }

    public List<GroupInfo> getGroupInfos() {
        return groupInfos;
    }

    public void setGroupInfos(List<GroupInfo> groupInfos) {
        this.groupInfos = groupInfos;
    }
}
