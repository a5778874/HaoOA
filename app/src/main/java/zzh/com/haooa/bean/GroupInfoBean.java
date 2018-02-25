package zzh.com.haooa.bean;

import zzh.com.haooa.Utils.Constant;

/**
 * Created by Administrator on 2016/9/24.
 */
// 群信息的bean类
public class GroupInfoBean {
    private String groupName;   // 群名称
    private String groupId;     // 群id
    private String invatePerson;// 邀请人

    public GroupInfoBean() {

    }

    public GroupInfoBean(String groupName, String groupId, String invatePerson) {
        this.groupName = groupName;
        this.groupId = groupId;
        this.invatePerson = invatePerson;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getInvatePerson() {
        return invatePerson;
    }

    public void setInvatePerson(String invatePerson) {
        this.invatePerson = invatePerson;
    }

    @Override
    public String toString() {
        return "GroupInfo{" +
                "groupName='" + groupName + '\'' +
                ", groupId='" + groupId + '\'' +
                ", invatePerson='" + invatePerson + '\'' +
                '}';
    }
}
