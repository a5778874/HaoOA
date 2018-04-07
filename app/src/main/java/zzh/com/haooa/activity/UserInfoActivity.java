package zzh.com.haooa.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.hyphenate.easeui.widget.EaseTitleBar;

import java.util.List;

import cn.bmob.v3.exception.BmobException;
import zzh.com.haooa.R;
import zzh.com.haooa.bean.UserInfoBean;
import zzh.com.haooa.bmob.bean.Department;
import zzh.com.haooa.bmob.dao.BmobStringCallBack;
import zzh.com.haooa.bmob.dao.DepartmentCallBack;
import zzh.com.haooa.bmob.dao.DepartmentDAO;
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
        //获取用户资料
        final UserInfoBean userInfoBean = UserInfoDAO.init().getUser().get(0);
        tv_hxUsername.setText(userInfoBean.getHxUsername());
        tv_department.setText(userInfoBean.getDepartmentName());


    }


}
