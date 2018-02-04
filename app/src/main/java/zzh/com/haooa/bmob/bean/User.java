package zzh.com.haooa.bmob.bean;

import cn.bmob.v3.BmobObject;

/**
 * Bmob云数据库操作bean类
 * Created by ZZH on 2018/2/3.
 */

public class User extends BmobObject{
    private String hxUsername;// 环信用户名
    private String nick;// 用户的昵称
    private String sex;//性别
    private String head;// 头像
    private String departmentID;//部门ID
    private String phone;//手机号
    private String mail;//邮箱
    private Boolean isAdmin;//是否管理员

    //设置Bmob关联表名
    public  User(){
        this.setTableName("user");
    }


    public String getHxUsername() {
        return hxUsername;
    }

    public void setHxUsername(String hxUsername) {
        this.hxUsername = hxUsername;
    }

    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getHead() {
        return head;
    }

    public void setHead(String head) {
        this.head = head;
    }

    public String getDepartmentID() {
        return departmentID;
    }

    public void setDepartmentID(String departmentID) {
        this.departmentID = departmentID;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public Boolean getAdmin() {
        return isAdmin;
    }

    public void setAdmin(Boolean admin) {
        isAdmin = admin;
    }
}