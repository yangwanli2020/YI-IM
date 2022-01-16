package com.itic.im.demo.model;

import java.io.Serializable;
import java.util.List;

public class GroupInfo implements Serializable {

    private static final long serialVersionUID = -8273867209121564648L;
    private String groupId;
    private int groupType;   // 1 群聊  2私聊
    private String groupName;
    private String groupAvatarUrl;
    private List<UserInfo> members;
    public GroupInfo(){}
    public GroupInfo(String groupId, String groupName, int groupType, String groupAvatarUrl, List<UserInfo> members) {
        super();
        this.groupId = groupId;
        this.groupName = groupName;
        this.groupType = groupType;
        this.groupAvatarUrl = groupAvatarUrl;
        this.members = members;
    }
    public GroupInfo(String groupId, String groupName, int groupType, List<UserInfo> members) {
        super();
        this.groupId = groupId;
        this.groupName = groupName;
        this.groupType = groupType;
        this.members = members;
    }
    public String getGroupId() {
        return groupId;
    }
    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }
    public List<UserInfo> getMembers() {
        return members;
    }
    public void setMembers(List<UserInfo> members) {
        this.members = members;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getGroupAvatarUrl() {
        return groupAvatarUrl;
    }

    public void setGroupAvatarUrl(String groupAvatarUrl) {
        this.groupAvatarUrl = groupAvatarUrl;
    }

    public int getGroupType() {
        return groupType;
    }

    public void setGroupType(int groupType) {
        this.groupType = groupType;
    }
}
