package zzh.com.haooa.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.hyphenate.chat.EMClient;
import com.hyphenate.easeui.widget.EaseTitleBar;

import java.util.List;

import cn.bmob.v3.exception.BmobException;
import zzh.com.haooa.R;
import zzh.com.haooa.Utils.ToastUtils;
import zzh.com.haooa.bean.UserInfoBean;
import zzh.com.haooa.bmob.bean.user;
import zzh.com.haooa.bmob.api.BmobStringCallBack;
import zzh.com.haooa.bmob.api.DepartmentApi;
import zzh.com.haooa.bmob.api.UserCallBack;
import zzh.com.haooa.bmob.api.UserApi;
import zzh.com.haooa.dao.DepartmentDao;
import zzh.com.haooa.dao.UserInfoDAO;

/**
 * Created by ZZH on 2018/4/6.
 */


public class UserInfoActivity extends Activity {
    private EaseTitleBar userinfo_titleBar;
    private TextView tv_hxUsername, tv_department;
    private EditText et_userinfo_nick, et_userinfo_sex, et_userinfo_phone, et_userinfo_mail, et_userinfo_address;

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

        et_userinfo_nick = findViewById(R.id.et_userinfo_nick);
        et_userinfo_sex = findViewById(R.id.et_userinfo_sex);
        et_userinfo_phone = findViewById(R.id.et_userinfo_phone);
        et_userinfo_mail = findViewById(R.id.et_userinfo_mail);
        et_userinfo_address = findViewById(R.id.et_userinfo_address);
    }

    private void initDatas() {
        //更新资料
        initUserInfo();
        //获取用户资料
//        final UserInfoBean userInfoBean = UserInfoDAO.init().getUser().get(0);
//        tv_hxUsername.setText(userInfoBean.getHxUsername());
//        tv_department.setText(userInfoBean.getDepartmentName());


    }


    private void initUserInfo() {
        //初始化用户资料
        final String hxUsername = EMClient.getInstance().getCurrentUser();
        Log.d("TAG", "MainActivity getUser: " + hxUsername);
        //获取服务器资料
        new UserApi().getUserInfo(hxUsername, new UserCallBack() {
            @Override
            public void getUser(List<user> list, BmobException e) {
                if (e == null) {
                    Log.d("TAG", "UserinfoActiity List<user> size: "+list.size());
                    //保存到本地数据库
                    final user myUser = list.get(0);
                    Log.d("TAG", "Userinfoactivity getDepartmentID: " + myUser.getDepartmentID());
                    final UserInfoBean userInfoBean = new UserInfoBean();
                    userInfoBean.setHxUsername(myUser.getHxUsername());
                    userInfoBean.setDepartmentID(myUser.getDepartmentID());
                    userInfoBean.setSex(myUser.getSex());
                    userInfoBean.setRole(myUser.getRole());
                    userInfoBean.setNick(myUser.getNick());
                    userInfoBean.setAddress(myUser.getAddress());
                    userInfoBean.setPhone(myUser.getPhone());
                    userInfoBean.setMail(myUser.getMail());
                    final String departmentName = DepartmentDao.init().getDepartmentInfoByID(myUser.getDepartmentID()).getDepartmentName();
                    Log.d("TAG", "UserInfoActivity getDepartmentID: "+myUser.getDepartmentID()+ ".. departmentName: "+departmentName);
                    userInfoBean.setDepartmentName(departmentName);

                    UserInfoDAO.init().deleteAll();
                    Log.d("TAG", "userInfoBean: " + userInfoBean.toString());
                    UserInfoDAO.init().addUser(userInfoBean);
//
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            tv_hxUsername.setText(myUser.getHxUsername());
                            tv_department.setText(departmentName);
                        }
                    });

                } else {
                    ToastUtils.showToast(UserInfoActivity.this, "初始化用户信息失败" + e.getMessage());
                    Log.d("TAG", "UserInfoActivity getLocalizedMessage: " + e.getLocalizedMessage());
                }
            }
        });
    }


    //修改资料按钮
    public void editInfo(View view) {
        editInfo(true);
    }

    //打开可编辑
    private void editInfo(boolean edit) {
        et_userinfo_nick.setEnabled(edit);
        et_userinfo_sex.setEnabled(edit);
        et_userinfo_phone.setEnabled(edit);
        et_userinfo_mail.setEnabled(edit);
        et_userinfo_address.setEnabled(edit);
    }


    //保存资料
    // TODO: 2018/5/23 完成保存资料逻辑
    public void saveInfo(View view) {
        editInfo(false);
    }
}
