package zzh.com.haooa.dao;

import zzh.com.haooa.MyApplication;
import zzh.com.haooa.bean.UserInfoBean;
import zzh.com.haooa.greenDao.UserInfoBeanDao;

/**
 * Created by ZZH on 2018/2/4.
 */


public class UserInfoDAO {

    private  static  UserInfoDAO userInfoDAO=new UserInfoDAO();
    private UserInfoBeanDao userInfoBeanDao;
    private UserInfoDAO(){
        //初始化数据库操作对象
        userInfoBeanDao = MyApplication.getInstances().getDaoSession().getUserInfoBeanDao();
    }

    public static UserInfoDAO init(){
        return userInfoDAO;
    }

    /**
     * 添加一个用户
     * @param userInfoBean
     * @return 返回所在行id
     */
    public long addUser(UserInfoBean userInfoBean){
        return userInfoBeanDao.insert(userInfoBean);
    }

}
