package zzh.com.haooa.Main;

import android.app.Application;

import com.hyphenate.chat.EMOptions;
import com.hyphenate.easeui.EaseUI;

/**
 * Created by ZZH on 2018/2/1.
 */

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        //初始化环信Ease UI
        EMOptions options = new EMOptions();
        // 默认添加好友时，是不需要验证的。false为需要验证
        options.setAcceptInvitationAlways(false);
        EaseUI.getInstance().init(this, options);
    }
}
