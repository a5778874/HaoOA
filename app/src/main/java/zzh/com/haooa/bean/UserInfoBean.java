package zzh.com.haooa.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.ToOne;
import org.greenrobot.greendao.annotation.Generated;


/**
 *
 * Created by ZZH on 2018/2/3.
 * 保存登录的用户信息
 */

@Entity
public class UserInfoBean {
    @Id
    private String HxUsername;// 环信用户名
    private String nick;// 用户的昵称
    private String sex;//性别
    private String head;// 头像
    private String departmentID;
    private String departmentName;//部门名字
    private String phone;//手机号
    private String mail;//邮箱
    private String address;//住址
    private int role;//角色
    private String createTime;//创建时间
    private String updateTime;//更新时间
    @Generated(hash = 1598971523)
    public UserInfoBean(String HxUsername, String nick, String sex, String head,
            String departmentID, String departmentName, String phone, String mail,
            String address, int role, String createTime, String updateTime) {
        this.HxUsername = HxUsername;
        this.nick = nick;
        this.sex = sex;
        this.head = head;
        this.departmentID = departmentID;
        this.departmentName = departmentName;
        this.phone = phone;
        this.mail = mail;
        this.address = address;
        this.role = role;
        this.createTime = createTime;
        this.updateTime = updateTime;
    }
    @Generated(hash = 1818808915)
    public UserInfoBean() {
    }
    public String getHxUsername() {
        return this.HxUsername;
    }
    public void setHxUsername(String HxUsername) {
        this.HxUsername = HxUsername;
    }
    public String getNick() {
        return this.nick;
    }
    public void setNick(String nick) {
        this.nick = nick;
    }
    public String getSex() {
        return this.sex;
    }
    public void setSex(String sex) {
        this.sex = sex;
    }
    public String getHead() {
        return this.head;
    }
    public void setHead(String head) {
        this.head = head;
    }
    public String getDepartmentID() {
        return this.departmentID;
    }
    public void setDepartmentID(String departmentID) {
        this.departmentID = departmentID;
    }
    public String getDepartmentName() {
        return this.departmentName;
    }
    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }
    public String getPhone() {
        return this.phone;
    }
    public void setPhone(String phone) {
        this.phone = phone;
    }
    public String getMail() {
        return this.mail;
    }
    public void setMail(String mail) {
        this.mail = mail;
    }
    public String getAddress() {
        return this.address;
    }
    public void setAddress(String address) {
        this.address = address;
    }
    public int getRole() {
        return this.role;
    }
    public void setRole(int role) {
        this.role = role;
    }
    public String getCreateTime() {
        return this.createTime;
    }
    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }
    public String getUpdateTime() {
        return this.updateTime;
    }
    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }
  
}
