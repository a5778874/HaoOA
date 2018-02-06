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
    private String HxUsername;//外键，对应负责人的HxUsername
    @ToOne(joinProperty = "HxUsername")//与上面字段关联
    private UserInfoBean user;// 负责人ID
    private String departmentName;// 部门名字
    private String createTime;// 创建日期
    private String updateTime;// 更新日期
    /** Used to resolve relations */
    @Generated(hash = 2040040024)
    private transient DaoSession daoSession;
    /** Used for active entity operations. */
    @Generated(hash = 2067062791)
    private transient DepartmentBeanDao myDao;
    @Generated(hash = 595881376)
    public DepartmentBean(String departmentID, String HxUsername,
            String departmentName, String createTime, String updateTime) {
        this.departmentID = departmentID;
        this.HxUsername = HxUsername;
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
    public String getHxUsername() {
        return this.HxUsername;
    }
    public void setHxUsername(String HxUsername) {
        this.HxUsername = HxUsername;
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
    @Generated(hash = 1867105156)
    private transient String user__resolvedKey;
    /** To-one relationship, resolved on first access. */
    @Generated(hash = 238138474)
    public UserInfoBean getUser() {
        String __key = this.HxUsername;
        if (user__resolvedKey == null || user__resolvedKey != __key) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            UserInfoBeanDao targetDao = daoSession.getUserInfoBeanDao();
            UserInfoBean userNew = targetDao.load(__key);
            synchronized (this) {
                user = userNew;
                user__resolvedKey = __key;
            }
        }
        return user;
    }
    /** called by internal mechanisms, do not call yourself. */
    @Generated(hash = 945637143)
    public void setUser(UserInfoBean user) {
        synchronized (this) {
            this.user = user;
            HxUsername = user == null ? null : user.getHxUsername();
            user__resolvedKey = HxUsername;
        }
    }
    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#delete(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 128553479)
    public void delete() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.delete(this);
    }
    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#refresh(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 1942392019)
    public void refresh() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.refresh(this);
    }
    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#update(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 713229351)
    public void update() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.update(this);
    }
    /** called by internal mechanisms, do not call yourself. */
    @Generated(hash = 1590690829)
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getDepartmentBeanDao() : null;
    }


}
