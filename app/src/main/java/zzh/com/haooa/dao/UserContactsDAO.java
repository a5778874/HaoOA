package zzh.com.haooa.dao;

import java.util.List;

import zzh.com.haooa.MyApplication;
import zzh.com.haooa.bean.InviteTableBean;
import zzh.com.haooa.bean.UserContactsTableBean;
import zzh.com.haooa.greenDao.InviteTableBeanDao;
import zzh.com.haooa.greenDao.UserContactsTableBeanDao;

/**
 * Created by ZZH on 2018/2/25.
 */

public class UserContactsDAO {
    private static UserContactsDAO userContactsDAO = new UserContactsDAO();

    private UserContactsDAO() {

    }

    public static UserContactsDAO init() {
        return userContactsDAO;
    }

    /**
     * 添加联系人
     *
     * @return 返回所在行id
     */
    public long addContacts(UserContactsTableBean userContactsTableBean) {
        return MyApplication.getInstances().getDaoSession().getUserContactsTableBeanDao().insertOrReplace(userContactsTableBean);
    }


    /**
     * 删除全部联系人
     */
    public void deleteAll(){
        MyApplication.getInstances().getDaoSession().getUserContactsTableBeanDao().deleteAll();
    }


    /**
     * 获取联系人列表
     */
    public List<UserContactsTableBean> getAllContacts() {
        List<UserContactsTableBean> lists = MyApplication.getInstances().getDaoSession().getUserContactsTableBeanDao().loadAll();
        return lists;
    }


    /**
     * 根据名字删除联系人
     */
    public boolean deleteInvite(String hxusername) {
        UserContactsTableBeanDao userContactsTableBeanDao = MyApplication.getInstances().getDaoSession().getUserContactsTableBeanDao();
        //查询是否存在
        UserContactsTableBean contactbean = userContactsTableBeanDao.queryBuilder().where(UserContactsTableBeanDao.Properties.HxUsername.eq(hxusername)).build().unique();
        if (contactbean == null) {
            return false;
        } else {
            //若用户存在则删除
            userContactsTableBeanDao.deleteByKey(hxusername);
            return true;
        }
    }
}
