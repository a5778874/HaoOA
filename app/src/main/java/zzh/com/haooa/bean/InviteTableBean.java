package zzh.com.haooa.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by ZZH on 2018/2/6.
 * 邀请信息，用于添加好友和添加群
 */

@Entity
public class InviteTableBean {
    @Id
    private String user_hxUsername;// 用户的环信id
    private String nick;// 用户的名称
    private String group_name;// 群组名称
    private String group_hxid;// 群组环信id
    private String reason;// 邀请的原因
    private String status;// 邀请的状态
    @Generated(hash = 1592776173)
    public InviteTableBean(String user_hxUsername, String nick, String group_name,
            String group_hxid, String reason, String status) {
        this.user_hxUsername = user_hxUsername;
        this.nick = nick;
        this.group_name = group_name;
        this.group_hxid = group_hxid;
        this.reason = reason;
        this.status = status;
    }
    @Generated(hash = 96800422)
    public InviteTableBean() {
    }
    public String getUser_hxUsername() {
        return this.user_hxUsername;
    }
    public void setUser_hxUsername(String user_hxUsername) {
        this.user_hxUsername = user_hxUsername;
    }
    public String getNick() {
        return this.nick;
    }
    public void setNick(String nick) {
        this.nick = nick;
    }
    public String getGroup_name() {
        return this.group_name;
    }
    public void setGroup_name(String group_name) {
        this.group_name = group_name;
    }
    public String getGroup_hxid() {
        return this.group_hxid;
    }
    public void setGroup_hxid(String group_hxid) {
        this.group_hxid = group_hxid;
    }
    public String getReason() {
        return this.reason;
    }
    public void setReason(String reason) {
        this.reason = reason;
    }
    public String getStatus() {
        return this.status;
    }
    public void setStatus(String status) {
        this.status = status;
    }
}
