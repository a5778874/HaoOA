package zzh.com.haooa.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.ToOne;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.DaoException;
import zzh.com.haooa.greenDao.DaoSession;
import zzh.com.haooa.greenDao.DepartmentBeanDao;
import zzh.com.haooa.greenDao.UserInfoBeanDao;

/**
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
    @ToOne(joinProperty = "departmentID")
    private DepartmentBean department;//部门ID
    private String phone;//手机号
    private String mail;//邮箱
    private String address;//住址
    private int role;//角色
    private String createTime;//创建时间
    private String updateTime;//更新时间
    /** Used to resolve relations */
    @Generated(hash = 2040040024)
    private transient DaoSession daoSession;
    /** Used for active entity operations. */
    @Generated(hash = 663796412)
    private transient UserInfoBeanDao myDao;
    @Generated(hash = 1330787407)
    public UserInfoBean(String HxUsername, String nick, String sex, String head,
            String departmentID, String phone, String mail, String address,
            int role, String createTime, String updateTime) {
        this.HxUsername = HxUsername;
        this.nick = nick;
        this.sex = sex;
        this.head = head;
        this.departmentID = departmentID;
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
    @Generated(hash = 752020286)
    private transient String department__resolvedKey;
    /** To-one relationship, resolved on first access. */
    @Generated(hash = 1489701537)
    public DepartmentBean getDepartment() {
        String __key = this.departmentID;
        if (department__resolvedKey == null || department__resolvedKey != __key) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            DepartmentBeanDao targetDao = daoSession.getDepartmentBeanDao();
            DepartmentBean departmentNew = targetDao.load(__key);
            synchronized (this) {
                department = departmentNew;
                department__resolvedKey = __key;
            }
        }
        return department;
    }
    /** called by internal mechanisms, do not call yourself. */
    @Generated(hash = 1292533847)
    public void setDepartment(DepartmentBean department) {
        synchronized (this) {
            this.department = department;
            departmentID = department == null ? null : department.getDepartmentID();
            department__resolvedKey = departmentID;
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
    @Generated(hash = 1364632475)
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getUserInfoBeanDao() : null;
    }


    
}
