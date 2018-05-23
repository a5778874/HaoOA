package zzh.com.haooa.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.ToOne;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.DaoException;
import zzh.com.haooa.greenDao.DaoSession;
import zzh.com.haooa.greenDao.UserInfoBeanDao;
import zzh.com.haooa.greenDao.DepartmentBeanDao;

/**
 * Created by ZZH on 2018/2/4.
 */

@Entity
public class DepartmentBean {

    @Id
    private String departmentID;// 部门ID
    private String leaderID;//外键，对应负责人的HxUsername
    private String departmentName;// 部门名字
    private String createTime;// 创建日期
    private String updateTime;// 更新日期
    @Generated(hash = 1600371485)
    public DepartmentBean(String departmentID, String leaderID,
            String departmentName, String createTime, String updateTime) {
        this.departmentID = departmentID;
        this.leaderID = leaderID;
        this.departmentName = departmentName;
        this.createTime = createTime;
        this.updateTime = updateTime;
    }
    @Generated(hash = 1119420877)
    public DepartmentBean() {
    }
    public String getDepartmentID() {
        return this.departmentID;
    }
    public void setDepartmentID(String departmentID) {
        this.departmentID = departmentID;
    }
    public String getLeaderID() {
        return this.leaderID;
    }
    public void setLeaderID(String leaderID) {
        this.leaderID = leaderID;
    }
    public String getDepartmentName() {
        return this.departmentName;
    }
    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
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
