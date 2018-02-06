package zzh.com.haooa.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import zzh.com.haooa.EventBus.LoginEvent;
import zzh.com.haooa.EventBus.RegistEvent;
import zzh.com.haooa.MyApplication;
import zzh.com.haooa.R;
import zzh.com.haooa.Utils.ThreadPoolUtils;
import zzh.com.haooa.Utils.ToastUtils;

/**
 * Created by ZZH on 2018/2/1.
 */

public class LoginActivity extends Activity implements View.OnClickListener {
    private TextView goRegister;
    private TextView loginUsername, loginPassword;
    private Button bt_login;
    private ProgressBar loginBar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        //增加栈记录
        MyApplication.getInstances().activitiesSets.add(LoginActivity.this);
        initView();
        initData();

    }

    private void initView() {
        goRegister = findViewById(R.id.tv_login_registerclick);
        goRegister.setOnClickListener(this);
        loginUsername = findViewById(R.id.et_login_username);
        loginPassword = findViewById(R.id.et_login_password);
        bt_login = findViewById(R.id.btn_login);
        bt_login.setOnClickListener(this);
        loginBar = findViewById(R.id.loginPb);
    }

    private void initData() {
        //注册EventBus广播，用来接收注册用户信息
        EventBus.getDefault().register(LoginActivity.this);
    }


    //接收EventBus信息
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onRegistEvent(RegistEvent event) {
        loginUsername.setText(event.username);
        loginPassword.setText(event.password);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_login_registerclick:
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
                break;
            case R.id.btn_login:
                loginBar.setVisibility(View.VISIBLE);
                login();
                break;
        }
    }

    //登录
    private void login() {
        // 1 获取输入的用户名和密码
        final String loginName = loginUsername.getText().toString();
        final String loginPwd = loginPassword.getText().toString();

        // 2 校验输入的用户名和密码
        if (TextUtils.isEmpty(loginName) || TextUtils.isEmpty(loginPwd)) {
            ToastUtils.showToast(LoginActivity.this, "输入的用户名或密码不能为空");
            return;
        }


        // 3 登录逻辑处理
        ThreadPoolUtils.getInstance().getGlobalThreadPool().execute(new Runnable() {
            @Override
            public void run() {

                // 去环信服务器登录
                EMClient.getInstance().login(loginName, loginPwd, new EMCallBack() {
                    // 登录成功的处理
                    @Override
                    public void onSuccess() {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                loginBar.setVisibility(View.GONE);
                                ToastUtils.showToast(LoginActivity.this, "登录成功");
                                //使用eventBus发送注册的用户名密码到登录界面
                                EventBus.getDefault().post(new LoginEvent(loginName));
                                LoginActivity.this.finish();
                            }
                        });

                    }

                    // 登录失败的处理
                    @Override
                    public void onError(int i, final String s) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                loginBar.setVisibility(View.GONE);
                                ToastUtils.showToast(LoginActivity.this, "登录失败：" + s);
                            }
                        });

                    }

                    // 登录过程中的处理
                    @Override
                    public void onProgress(int i, String s) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                loginBar.setVisibility(View.VISIBLE);
                            }
                        });
                    }
                });
            }
        });


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //移除栈记录
        MyApplication.getInstances().activitiesSets.remove(LoginActivity.this);
        //解除注册eventBus广播
        EventBus.getDefault().unregister(LoginActivity.this);
    }
}
