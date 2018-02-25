package zzh.com.haooa.activity;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;

import com.hyphenate.chat.EMClient;
import com.hyphenate.exceptions.HyphenateException;

import java.util.List;

import zzh.com.haooa.R;
import zzh.com.haooa.Utils.Constant;
import zzh.com.haooa.Utils.ThreadPoolUtils;
import zzh.com.haooa.adapter.InviteAdapter;
import zzh.com.haooa.bean.InviteTableBean;
import zzh.com.haooa.dao.InviteTableDAO;

/**
 * Created by ZZH on 2018/2/25.
 */

public class InviteActivity extends Activity {
    private ListView lv_invite;
    private InviteAdapter inviteAdapter;
    private LocalBroadcastManager mLBM;
    private BroadcastReceiver InviteChangedReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            // 刷新页面
            refresh();
        }
    };

    //处理邀请信息的回调方法
    private InviteAdapter.OnInviteListener mOnInviteListener = new InviteAdapter.OnInviteListener() {
        @Override
        public void onAccept(final InviteTableBean invationInfo) {
            Log.d("TAG", "run: invity onAccept...");
            // 通知环信服务器，点击了接受按钮
            ThreadPoolUtils.getInstance().getGlobalThreadPool().execute(new Runnable() {
                @Override
                public void run() {
                    try {
                        EMClient.getInstance().contactManager().acceptInvitation(invationInfo.getUser_hxUsername());

                        // 数据库更新
                        InviteTableBean inviteTableBean = new InviteTableBean();
                        inviteTableBean.setUser_hxUsername(invationInfo.getUser_hxUsername());
                        inviteTableBean.setReason(invationInfo.getReason());
                        inviteTableBean.setStatus(invationInfo.getStatus());
                        InviteTableDAO.init().updateInvite(inviteTableBean);

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                // 页面发生变化
                                Toast.makeText(InviteActivity.this, "接受了邀请", Toast.LENGTH_SHORT).show();

                                // 刷新页面
                                refresh();
                            }
                        });

                    } catch (HyphenateException e) {
                        e.printStackTrace();
                        Log.d("TAG", "run: accept exception:" +e.getMessage());
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(InviteActivity.this, "接受邀请失败", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                }
            });
        }

        @Override
        public void onReject(final InviteTableBean invationInfo) {
            Log.d("TAG", "run: invity onReject...");
            ThreadPoolUtils.getInstance().getGlobalThreadPool().execute(new Runnable() {
                @Override
                public void run() {
                    try {
                        EMClient.getInstance().contactManager().declineInvitation(invationInfo.getUser_hxUsername());
                        // 数据库删除邀请信息
                        InviteTableDAO.init().deleteInvite(invationInfo.getUser_hxUsername());
                        // 页面变化
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(InviteActivity.this, "拒绝成功了", Toast.LENGTH_SHORT).show();

                                // 刷新页面
                                refresh();
                            }
                        });
                    } catch (HyphenateException e) {
                        e.printStackTrace();

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(InviteActivity.this, "拒绝失败了", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                }
            });

        }

        @Override
        public void onInviteAccept(InviteTableBean invationInfo) {
            Log.d("TAG", "run: invity onInviteAccept...");
        }

        @Override
        public void onInviteReject(InviteTableBean invationInfo) {
            Log.d("TAG", "run: invity onInviteReject...");
        }

        @Override
        public void onApplicationAccept(InviteTableBean invationInfo) {
            Log.d("TAG", "run: invity onApplicationAccept...");
        }

        @Override
        public void onApplicationReject(InviteTableBean invationInfo) {
            Log.d("TAG", "run: invity onApplicationReject...");
        }
    };

    //更新列表
    private void refresh() {
        // 获取数据库中的所有邀请信息
        List<InviteTableBean> invite = InviteTableDAO.init().getInvite();
        Log.d("TAG", "refresh: "+invite.size());
        // 刷新适配器
        inviteAdapter.refresh(invite);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invite);
        initView();
        initData();
    }

    private void initData() {
        // 初始化listview适配器
        inviteAdapter = new InviteAdapter(this, mOnInviteListener);
        lv_invite.setAdapter(inviteAdapter);
        //刷新列表
        refresh();

        // 注册邀请信息变化的广播
        mLBM = LocalBroadcastManager.getInstance(this);
        mLBM.registerReceiver(InviteChangedReceiver, new IntentFilter(Constant.CONTACT_INVITE_CHANGED));
        mLBM.registerReceiver(InviteChangedReceiver, new IntentFilter(Constant.GROUP_INVITE_CHANGED));
    }

    private void initView() {
        lv_invite = findViewById(R.id.lv_invite);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //解除注册广播
        mLBM.unregisterReceiver(InviteChangedReceiver);
    }
}
