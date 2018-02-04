package zzh.com.haooa.dao;

import zzh.com.haooa.MyApplication;
import zzh.com.haooa.bean.UserInfoBean;
import zzh.com.haooa.greenDao.UserInfoBeanDao;

/**
 * Created by ZZH on 2018/2/4.
 */


public class UserInfoDAO {

    /**
     * 添加一个用户
     * @param userInfoBean
     * @return 返回所在行id
     */
    public static long addUser(UserInfoBean userInfoBean){
        return MyApplication.getInstances().getDaoSession().getUserInfoBeanDao().insert(userInfoBean);
    }

}
