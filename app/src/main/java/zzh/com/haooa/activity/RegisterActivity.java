package zzh.com.haooa.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.hyphenate.chat.EMClient;
import com.hyphenate.easeui.widget.EaseTitleBar;
import com.hyphenate.exceptions.HyphenateException;

import org.greenrobot.eventbus.EventBus;

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;
import zzh.com.haooa.EventBus.RegistEvent;
import zzh.com.haooa.MyApplication;
import zzh.com.haooa.R;
import zzh.com.haooa.Utils.ThreadPoolUtils;
import zzh.com.haooa.Utils.ToastUtils;
import zzh.com.haooa.bmob.bean.user;

/**
 * Created by ZZH on 2018/2/1.
 */

public class RegisterActivity extends Activity implements View.OnClickListener {
    private Button regButton;
    private EditText input_username, input_password, input_password_again;
    private EaseTitleBar easeTitleBar;
    private ProgressBar pb;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        //增加栈记录
        MyApplication.getInstances().activitiesSets.add(RegisterActivity.this);
        initView();
    }

    private void initView() {

        easeTitleBar = findViewById(R.id.reg_titleBar);
        easeTitleBar.setLeftLayoutClickListener(this);
        regButton = findViewById(R.id.btn_reg_register);
        regButton.setOnClickListener(this);
        input_username = findViewById(R.id.et_reg_username);
        input_password = findViewById(R.id.et_reg_password);
        input_password_again = findViewById(R.id.et_reg_password_again);
        pb = findViewById(R.id.pb);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_reg_register:
                registAccount();
                break;
            //tileBar左侧按钮
            case R.id.left_layout:
                finish();
                break;
        }
    }


    //注册
    private void registAccount() {
        pb.setVisibility(View.VISIBLE);
        // 1 获取输入的用户名和密码
        final String registName = input_username.getText().toString();
        final String registPwd = input_password.getText().toString();
        final String registPwdAgain = input_password_again.getText().toString();

        // 2 校验输入的用户名和密码
        if (TextUtils.isEmpty(registName) || TextUtils.isEmpty(registPwd) || TextUtils.isEmpty(registPwd)) {
            ToastUtils.showToast(RegisterActivity.this, "信息不能为空");
            pb.setVisibility(View.GONE);
            return;
        }
        if (!registPwd.equals(registPwdAgain)) {
            ToastUtils.showToast(RegisterActivity.this, "两次输入的密码不同");
            pb.setVisibility(View.GONE);
            return;
        }
        //3 去服务器注册帐号
        ThreadPoolUtils.getInstance().getGlobalThreadPool().execute(new Runnable() {
            @Override
            public void run() {
                try {
                    //去环信注册
                    EMClient.getInstance().createAccount(registName, registPwd);
                    //把注册环信帐号同步到Bmob服务器
                    final user myUser = new user();
                    myUser.setHxUsername(registName);
                    //DepartmentID 1000代表 暂无部门
                    myUser.setDepartmentID("1000");
                    //默认注册的为普通用户
                    myUser.setRole(0);
                    myUser.save(new SaveListener<String>() {
                        @Override
                        public void done(String s, final BmobException e) {
                            if (e == null) {
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        ToastUtils.showToast(RegisterActivity.this, "注册成功");
                                        //使用eventBus发送注册的用户名密码到登录界面
                                        EventBus.getDefault().post(new RegistEvent(registName, registPwd));
                                        RegisterActivity.this.finish();
                                    }

                                });
                            } else {
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        pb.setVisibility(View.GONE);
                                        ToastUtils.showToast(RegisterActivity.this, "同步帐号到服务器失败," + e.getLocalizedMessage());
                                    }
                                });
                            }
                        }
                    });
                } catch (final HyphenateException e) {
                    e.printStackTrace();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            pb.setVisibility(View.GONE);
                            ToastUtils.showToast(RegisterActivity.this, "注册失败:" + e.getDescription());
                        }
                    });
                }
            }
        });
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        //移除栈记录
        MyApplication.getInstances().activitiesSets.remove(RegisterActivity.this);
    }
}
