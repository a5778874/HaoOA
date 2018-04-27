package zzh.com.haooa.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.hyphenate.chat.EMClient;
import com.hyphenate.easeui.widget.EaseTitleBar;

import java.util.List;

import cn.bmob.v3.exception.BmobException;
import zzh.com.haooa.R;
import zzh.com.haooa.Utils.ToastUtils;
import zzh.com.haooa.bean.UserInfoBean;
import zzh.com.haooa.bmob.bean.Department;
import zzh.com.haooa.bmob.bean.user;
import zzh.com.haooa.bmob.dao.BmobStringCallBack;
import zzh.com.haooa.bmob.dao.DepartmentCallBack;
import zzh.com.haooa.bmob.dao.DepartmentDAO;
import zzh.com.haooa.bmob.dao.UserCallBack;
import zzh.com.haooa.bmob.dao.UserDAO;
import zzh.com.haooa.dao.UserInfoDAO;
import zzh.com.haooa.greenDao.UserInfoBeanDao;

/**
 * Created by ZZH on 2018/4/6.
 */


public class UserInfoActivity extends Activity {
    private EaseTitleBar userinfo_titleBar;
    private TextView tv_hxUsername, tv_department;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_userinfo);
        initView();
        initDatas();


    }


    private void initView() {
        userinfo_titleBar = findViewById(R.id.userinfo_titleBar);
        userinfo_titleBar.setLeftLayoutClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UserInfoActivity.this.finish();
            }
        });
        tv_hxUsername = findViewById(R.id.tv_userinfo_name);
        tv_department = findViewById(R.id.tv_userinfo_department);
    }

    private void initDatas() {
        //更新资料
        initUserInfo();
        //获取用户资料
        final UserInfoBean userInfoBean = UserInfoDAO.init().getUser().get(0);
        tv_hxUsername.setText(userInfoBean.getHxUsername());
        tv_department.setText(userInfoBean.getDepartmentName());


    }


    private void initUserInfo() {
        //初始化用户资料
        final String hxUsername = EMClient.getInstance().getCurrentUser();
        Log.d("TAG", "MainActivity getUser: " + hxUsername);
        //获取服务器资料
        new UserDAO().getUserInfo(hxUsername, new UserCallBack() {
            @Override
            public void getUser(List<user> list, BmobException e) {
                if (e == null) {
                    //保存到本地数据库
                    user myUser = list.get(0);
                    Log.d("TAG", "MainActivity getUser: " + myUser.getDepartmentID());
                    final UserInfoBean userInfoBean = new UserInfoBean();
                    userInfoBean.setHxUsername(myUser.getHxUsername());
                    userInfoBean.setDepartmentID(myUser.getDepartmentID());
                    userInfoBean.setSex(myUser.getSex());
                    userInfoBean.setRole(myUser.getRole());
                    userInfoBean.setNick(myUser.getNick());
                    userInfoBean.setAddress(myUser.getAddress());
                    userInfoBean.setPhone(myUser.getPhone());
                    userInfoBean.setMail(myUser.getMail());

                        UserInfoDAO.init().deleteAll();
                        Log.d("TAG", "userInfoBean: " + userInfoBean.toString());
                        UserInfoDAO.init().addUser(userInfoBean);
                        //从服务器获取部门id对应的名字并保存
                        new DepartmentDAO().getDepartmentByID(myUser.getDepartmentID(), new BmobStringCallBack() {
                            @Override
                            public void getName(String name, BmobException e) {
                                if (e == null) {
                                    userInfoBean.setDepartmentName(name);
                                    UserInfoDAO.init().updateUser(userInfoBean);
                                }
                            }
                        });

                } else {
                    ToastUtils.showToast(UserInfoActivity.this, "初始化用户信息失败,请检查网络是否连接");
                    Log.d("TAG", "UserInfoActivity getUser: " + e.getLocalizedMessage());
                }
            }
        });
    }


}
