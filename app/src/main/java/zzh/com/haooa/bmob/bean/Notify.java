package zzh.com.haooa.bmob.bean;

import cn.bmob.v3.BmobObject;

/**
 * Created by ZZH on 2018/4/6.
 */

public class Notify extends BmobObject {
    private String notify_title;
    private String notify_text;
    private String departmentID;
    private String author;

    public String getNotify_title() {
        return notify_title;
    }

    public void setNotify_title(String notify_title) {
        this.notify_title = notify_title;
    }

    public String getNotify_text() {
        return notify_text;
    }

    public void setNotify_text(String notify_text) {
        this.notify_text = notify_text;
    }

    public String getDepartmentID() {
        return departmentID;
    }

    public void setDepartmentID(String departmentID) {
        this.departmentID = departmentID;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }
}
