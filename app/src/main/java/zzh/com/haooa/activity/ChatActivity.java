package zzh.com.haooa.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.LocalBroadcastManager;
import android.view.View;

import com.hyphenate.chat.EMMessage;
import com.hyphenate.easeui.EaseConstant;
import com.hyphenate.easeui.ui.EaseChatFragment;
import com.hyphenate.easeui.widget.chatrow.EaseCustomChatRowProvider;

import zzh.com.haooa.R;

// 会话详情页面
public class ChatActivity extends FragmentActivity {

    private String mHxid;
    private EaseChatFragment easeChatFragment;
    private LocalBroadcastManager mLBM;
    private int mChatType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_chat);

        initData();

        //initListener();
    }


    private void initData() {
        // 创建一个会话的fragment
        easeChatFragment = new EaseChatFragment();

        mHxid = getIntent().getStringExtra(EaseConstant.EXTRA_USER_ID);

        // 获取聊天类型
        mChatType = getIntent().getExtras().getInt(EaseConstant.EXTRA_CHAT_TYPE);

        easeChatFragment.setArguments(getIntent().getExtras());

        // 替换fragment
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fl_chat, easeChatFragment).commit();

        // 获取发送广播的管理者
        mLBM = LocalBroadcastManager.getInstance(ChatActivity.this);
    }
}
