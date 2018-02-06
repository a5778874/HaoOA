package zzh.com.haooa.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by ZZH on 2018/2/6.
 * 保存登录用户联系人信息
 */

@Entity
public class UserContactsTableBean {
    @Id
    private String HxUsername;// 环信用户名
    private String head;      //用户头像
    private boolean isOnline;//是否在线
    private boolean isContacts;//是否是联系人
    @Generated(hash = 1010260291)
    public UserContactsTableBean(String HxUsername, String head, boolean isOnline,
            boolean isContacts) {
        this.HxUsername = HxUsername;
        this.head = head;
        this.isOnline = isOnline;
        this.isContacts = isContacts;
    }
    @Generated(hash = 564950109)
    public UserContactsTableBean() {
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
    public boolean getIsContacts() {
        return this.isContacts;
    }
    public void setIsContacts(boolean isContacts) {
        this.isContacts = isContacts;
    }
}
