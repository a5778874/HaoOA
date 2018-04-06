package zzh.com.haooa.bmob.bean;

import cn.bmob.v3.BmobObject;

/**
 * Created by ZZH on 2018/4/6.
 */

public class Department extends BmobObject{
    String departmentID;
    String departmentName;

    public String getDepartmentID() {
        return departmentID;
    }

    public void setDepartmentID(String departmentID) {
        this.departmentID = departmentID;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }
}
