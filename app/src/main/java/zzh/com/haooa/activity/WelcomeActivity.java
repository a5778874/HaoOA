package zzh.com.haooa.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;

import com.hyphenate.chat.EMClient;

import zzh.com.haooa.R;
import zzh.com.haooa.Utils.ThreadPoolUtils;

/**
 * Created by ZZH on 2018/2/7.
 */

public class WelcomeActivity extends Activity {
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            // 如果当前activity已经退出，那么我就不处理handler中的消息
            if (isFinishing()) {
                return;
            }
            // 判断进入主页面还是登录页面
            gotoMainOrLogin();
        }
    };

    //若之前登陆过进到主页面，没登陆过进入登录页面
    private void gotoMainOrLogin() {
        ThreadPoolUtils.getInstance().getGlobalThreadPool().execute(new Runnable() {
            @Override
            public void run() {
                if (EMClient.getInstance().isLoggedInBefore()) {
                    //登陆过进入主页面
                    Intent intent = new Intent(WelcomeActivity.this, MainActivity.class);
                    startActivity(intent);
                } else {
                    //没登陆过进入登录界面
                    Intent intent = new Intent(WelcomeActivity.this, LoginActivity.class);
                    intent.putExtra("fromWelcomeActivity", true);
                    startActivity(intent);
                }
                WelcomeActivity.this.finish();
            }
        });
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        // 发送2s钟的延时消息
        handler.sendMessageDelayed(Message.obtain(), 1000);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // 销毁handler消息
        handler.removeCallbacksAndMessages(null);
    }
}
