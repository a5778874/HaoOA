package zzh.com.haooa.bmob.bean;

import android.support.annotation.NonNull;
import android.util.Log;

import java.text.ParseException;

import cn.bmob.v3.BmobObject;
import zzh.com.haooa.Utils.DateUtils;

/**
 * Bmob云数据库操作bean类
 * Created by ZZH on 2018/2/3.
 */

public class user extends BmobObject implements Comparable<user> {
    private String hxUsername;// 环信用户名
    private String nick;// 用户的昵称
    private String sex;//性别
    private String head;// 头像
    private String departmentID;//部门ID
    private String phone;//手机号
    private String mail;//邮箱
    private String address;//地址
    private int role;//角色

    //设置Bmob关联表名
    public user() {
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getRole() {
        return role;
    }

    public void setRole(int role) {
        this.role = role;
    }


    //让集合按数据的最新创建顺序在前排序
    @Override
    public int compareTo(@NonNull user o) {
        int i = 0;
        try {
            long l = DateUtils.DateTomillisecond(o.getCreatedAt()) - DateUtils.DateTomillisecond(this.getCreatedAt());
            if (l > 0) {
                i = 1;
            } else {
                i = -1;
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return i;
    }
}
