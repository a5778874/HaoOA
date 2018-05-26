package zzh.com.haooa.bmob.api;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.UpdateListener;
import zzh.com.haooa.Utils.Constant;
import zzh.com.haooa.Utils.ThreadPoolUtils;
import zzh.com.haooa.bmob.bean.Leave;
import zzh.com.haooa.bmob.bean.user;

/**
 * Created by ZZH on 2018/4/6.
 */

public class UserApi {
    //查询当前用户的资料
    public void getUserInfo(final String hxUsername, final UserCallBack userCallBack) {
        ThreadPoolUtils.getInstance().getGlobalThreadPool().execute(new Runnable() {
            @Override
            public void run() {
                BmobQuery<user> query = new BmobQuery<user>();
                query.addWhereEqualTo("hxUsername", hxUsername);
                query.setLimit(300);
                query.findObjects(new FindListener<user>() {
                    @Override
                    public void done(List<user> list, BmobException e) {
                        userCallBack.getUser(list, e);
                    }
                });
            }
        });
    }

    //查询非管理员用户列表
    public void getNotAdminUser(final UserCallBack userCallBack){
        ThreadPoolUtils.getInstance().getGlobalThreadPool().execute(new Runnable() {
            @Override
            public void run() {
                BmobQuery<user> query = new BmobQuery<user>();
                query.addWhereNotEqualTo("departmentID", Constant.ROLE_ADMIN);
                query.setLimit(300);
                query.findObjects(new FindListener<user>() {
                    @Override
                    public void done(List<user> list, BmobException e) {
                        userCallBack.getUser(list, e);
                    }
                });
            }
        });
    }


    //更新用户部门
    public void updateDepartmentID(String objectID, String departmentID, final BmobCallBack bmobCallBack){
        user  mUser = new user();
        mUser.setDepartmentID(departmentID);
        mUser.update(objectID, new UpdateListener() {
            @Override
            public void done(BmobException e) {
                bmobCallBack.getBmobException(e);
            }
        });
    }
}
