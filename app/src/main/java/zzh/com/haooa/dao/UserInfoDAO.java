package zzh.com.haooa.dao;

import java.util.List;

import zzh.com.haooa.MyApplication;
import zzh.com.haooa.bean.UserInfoBean;
import zzh.com.haooa.greenDao.UserInfoBeanDao;

/**
 * Created by ZZH on 2018/2/4.
 */


public class UserInfoDAO {

    private static UserInfoDAO userInfoDAO=new UserInfoDAO();

    private UserInfoDAO(){

    }

    public  static UserInfoDAO init(){
        return userInfoDAO;
    }

    /**
     * 添加一个用户信息
     * @param userInfoBean
     * @return 返回所在行id
     */
    public long addUser(UserInfoBean userInfoBean){
        return MyApplication.getInstances().getDaoSession().getUserInfoBeanDao().insertOrReplace(userInfoBean);
    }

    /**
     * 删除全部记录
     */
    public void deleteAll(){
        MyApplication.getInstances().getDaoSession().getUserInfoBeanDao().deleteAll();
    }

    /**
     * 查询全部
     * @return
     */
    public  List<UserInfoBean> getUser(){
      return MyApplication.getInstances().getDaoSession().getUserInfoBeanDao().loadAll();
    }

}
