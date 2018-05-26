package zzh.com.haooa.dao;

import java.util.List;

import zzh.com.haooa.MyApplication;
import zzh.com.haooa.Utils.Constant;
import zzh.com.haooa.bean.DepartmentBean;
import zzh.com.haooa.bean.UserInfoBean;
import zzh.com.haooa.greenDao.DepartmentBeanDao;

/**
 * Created by Administrator on 2018/5/23.
 */

public class DepartmentDao {
    private static DepartmentDao departmentDao = new DepartmentDao();

    private DepartmentDao() {

    }

    public static DepartmentDao init() {
        return departmentDao;
    }

    /**
     * 添加信息
     *
     * @param departmentBean
     * @return 返回所在行id
     */
    public long addDepartment(DepartmentBean departmentBean) {
        return MyApplication.getInstances().getDaoSession().getDepartmentBeanDao().insertOrReplace(departmentBean);
    }

    /**
     * 删除全部记录
     */
    public void deleteAll() {
        MyApplication.getInstances().getDaoSession().getDepartmentBeanDao().deleteAll();
    }

    /**
     * 查询全部部门信息
     *
     * @return
     */
    public List<DepartmentBean> getAllDepartment() {
        return MyApplication.getInstances().getDaoSession().getDepartmentBeanDao().loadAll();
    }


    //查询有效的部门信息
    public List<DepartmentBean> getValidDepartment() {
        return MyApplication.getInstances().getDaoSession().getDepartmentBeanDao().queryBuilder().where(DepartmentBeanDao.Properties.DepartmentID.notEq(Constant.ROLE_NONE)).list();
    }


    //根据部门id查询部门信息
    public DepartmentBean getDepartmentInfoByID(String departmentID) {
        DepartmentBean departmentBean = MyApplication.getInstances().getDaoSession().getDepartmentBeanDao().queryBuilder().where(DepartmentBeanDao.Properties.DepartmentID.eq(departmentID)).unique();
        return departmentBean;
    }


    //根据部门名查询部门id
    public String getIdByDepartmentName(String departmentName) {
        DepartmentBean departmentBean = MyApplication.getInstances().getDaoSession().getDepartmentBeanDao().queryBuilder().where(DepartmentBeanDao.Properties.DepartmentName.eq(departmentName)).unique();
        return departmentBean.getDepartmentID();
    }

}
