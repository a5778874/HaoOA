package zzh.com.haooa.dao;

import zzh.com.haooa.MyApplication;
import zzh.com.haooa.bean.UserAccountTableBean;
import zzh.com.haooa.bean.UserInfoBean;
import zzh.com.haooa.greenDao.UserAccountTableBeanDao;

/**
 * Created by ZZH on 2018/2/4.
 */


public class UserAccountDAO {

    private static UserAccountDAO userAccountDAO = new UserAccountDAO();

    private UserAccountDAO() {

    }

    public static UserAccountDAO init() {
        return userAccountDAO;
    }

    /**
     * 保存登录用户
     *
     * @param userAccountTableBean
     * @return 返回所在行id
     */
    public long addUser(UserAccountTableBean userAccountTableBean) {
        return MyApplication.getInstances().getDaoSession().getUserAccountTableBeanDao().insertOrReplace(userAccountTableBean);
    }


    public String getCurrentOnlineUser() {
        UserAccountTableBean useraccount = MyApplication.getInstances().getDaoSession().getUserAccountTableBeanDao().queryBuilder().
                where(UserAccountTableBeanDao.Properties.IsOnline.eq(true)).unique();
        if (useraccount != null)
            return useraccount.getHxUsername();
        else
            return "";

    }


}
