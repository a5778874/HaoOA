package zzh.com.haooa.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.TextView;

import java.util.List;

import cn.bmob.v3.exception.BmobException;
import zzh.com.haooa.R;
import zzh.com.haooa.bmob.bean.Department;
import zzh.com.haooa.bmob.dao.BmobStringCallBack;
import zzh.com.haooa.bmob.dao.DepartmentCallBack;
import zzh.com.haooa.bmob.dao.DepartmentDAO;
import zzh.com.haooa.bmob.dao.UserDAO;
import zzh.com.haooa.dao.UserInfoDAO;

/**
 * Created by ZZH on 2018/4/6.
 */

public class UserInfoActivity extends Activity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_userinfo);


    }
}
