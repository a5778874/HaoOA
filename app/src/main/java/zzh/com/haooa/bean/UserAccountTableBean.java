package zzh.com.haooa.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by ZZH on 2018/2/6.
 * 保存登录用户帐号信息
 */

@Entity
public class UserAccountTableBean {
    @Id
    private String HxUsername;// 环信用户名
    private String head;      //用户头像
    private boolean isOnline;//是否在线
    @Generated(hash = 328519646)
    public UserAccountTableBean(String HxUsername, String head, boolean isOnline) {
        this.HxUsername = HxUsername;
        this.head = head;
        this.isOnline = isOnline;
    }
    @Generated(hash = 1190270220)
    public UserAccountTableBean() {
    }
    public String getHxUsername() {
        return this.HxUsername;
    }
    public void setHxUsername(String HxUsername) {
        this.HxUsername = HxUsername;
    }
    public String getHead() {
        return this.head;
    }
    public void setHead(String head) {
        this.head = head;
    }
    public boolean getIsOnline() {
        return this.isOnline;
    }
    public void setIsOnline(boolean isOnline) {
        this.isOnline = isOnline;
    }
}
