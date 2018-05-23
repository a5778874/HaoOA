package zzh.com.haooa.dao;

import java.util.List;

import zzh.com.haooa.MyApplication;
import zzh.com.haooa.bean.DepartmentBean;
import zzh.com.haooa.bean.UserInfoBean;

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
     * 查询全部
     *
     * @return
     */
    public List<DepartmentBean> getAllDepartment() {
        return MyApplication.getInstances().getDaoSession().getDepartmentBeanDao().loadAll();
    }





}
