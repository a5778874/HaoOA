package zzh.com.haooa.fragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.hyphenate.chat.EMClient;
import com.hyphenate.easeui.EaseConstant;
import com.hyphenate.easeui.domain.EaseUser;
import com.hyphenate.easeui.ui.EaseContactListFragment;
import com.hyphenate.exceptions.HyphenateException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import zzh.com.haooa.R;
import zzh.com.haooa.Utils.Constant;
import zzh.com.haooa.Utils.SpUtils;
import zzh.com.haooa.Utils.ThreadPoolUtils;
import zzh.com.haooa.activity.AddContactsActivity;
import zzh.com.haooa.activity.ChatActivity;
import zzh.com.haooa.activity.InviteActivity;
import zzh.com.haooa.bean.UserContactsTableBean;
import zzh.com.haooa.dao.UserContactsDAO;

/**
 * Created by ZZH on 2018/1/25.
 * 使用EaseUI提供好的联系人列表Fragment
 */

public class ContactsFragment extends EaseContactListFragment {
    private ImageView iv_contact_red;
    private LinearLayout ll_contact_invite;

    private LocalBroadcastManager mLBM;

    //邀请通知信息改变广播
    private BroadcastReceiver ContactInviteChangeReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            // 更新红点显示
            iv_contact_red.setVisibility(View.VISIBLE);
            SpUtils.getInstance().save(Constant.IS_NEW_INVITE, true);

        }
    };

    //联系人变化广播
    private BroadcastReceiver ContactChangeReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            // 刷新页面
            refreshContact();
        }
    };

    //刷新联系人列表
    private void refreshContact() {

        // 获取数据
        List<UserContactsTableBean> contacts = UserContactsDAO.init().getAllContacts();

        // 校验
        if (contacts != null && contacts.size() >= 0) {

            // 设置数据
            Map<String, EaseUser> contactsMap = new HashMap<>();

            // 转换
            for (UserContactsTableBean contact : contacts) {
                EaseUser easeUser = new EaseUser(contact.getHxUsername());
                contactsMap.put(contact.getHxUsername(), easeUser);
            }
            setContactsMap(contactsMap);
            // 刷新页面
            refresh();

        }
    }

    //1.初始化EaseUI的控件
    @Override
    protected void initView() {
        super.initView();
        //添加好友按钮
        titleBar.setRightImageResource(R.mipmap.em_add);
        titleBar.setTitle("联系人");
        //扩展EaseUI的fragment布局
        View v=View.inflate(getContext(),R.layout.fragment_contacts,null);
        listView.addHeaderView(v);
        //query是父类edittext的引用
        query.setHint("搜索");
        // 获取红点对象
        iv_contact_red = v.findViewById(R.id.iv_contact_red);
        // 获取邀请信息条目的对象
        ll_contact_invite =v.findViewById(R.id.ll_contact_invite);
        // 设置listview会话条目的点击事件
        setContactListItemClickListener(new EaseContactListItemClickListener() {
            @Override
            public void onListItemClicked(EaseUser user) {

                if (user == null) {
                    return;
                }

                Intent intent = new Intent(getActivity(), ChatActivity.class);

                // 传递参数
                intent.putExtra(EaseConstant.EXTRA_USER_ID, user.getUsername());

                startActivity(intent);
            }
        });
    }

    //2.重写此方法用于处理逻辑业务
    @Override
    protected void setUpView() {
        super.setUpView();
        //环信EaseUI标题栏右侧按钮的点击事件
        titleBar.setRightLayoutClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it=new Intent(getActivity(), AddContactsActivity.class);
                startActivity(it);
            }
        });
        // 初始化红点显示
        boolean isNewInvite = SpUtils.getInstance().getBoolean(Constant.IS_NEW_INVITE, false);
        iv_contact_red.setVisibility(isNewInvite ? View.VISIBLE : View.GONE);

        // 邀请信息条目点击事件
        ll_contact_invite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 红点处理
                iv_contact_red.setVisibility(View.GONE);
                SpUtils.getInstance().save(Constant.IS_NEW_INVITE, false);

                // 跳转到邀请信息列表页面
                Intent intent = new Intent(getActivity(), InviteActivity.class);

                startActivity(intent);
            }
        });

        // 注册广播
        mLBM = LocalBroadcastManager.getInstance(getActivity());
        mLBM.registerReceiver(ContactInviteChangeReceiver, new IntentFilter(Constant.CONTACT_INVITE_CHANGED));
        mLBM.registerReceiver(ContactChangeReceiver, new IntentFilter(Constant.CONTACT_CHANGED));
        // 从环信服务器获取所有的联系人信息
        getContactFromHxServer();
        // 绑定listview和contextmenu，用于长按删除联系人的弹出菜单
        registerForContextMenu(listView);

    }

    private String mHxid;
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);

        // 获取环信id
        int position = ((AdapterView.AdapterContextMenuInfo) menuInfo).position;

        EaseUser easeUser = (EaseUser) listView.getItemAtPosition(position);

        mHxid = easeUser.getUsername();

        // 添加布局
        getActivity().getMenuInflater().inflate(R.menu.delete, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {

        if (item.getItemId() == R.id.contact_delete) {
            // 执行删除选中的联系人操作
            deleteContact();

            return true;
        }

        return super.onContextItemSelected(item);
    }

    // 执行删除选中的联系人操作
    private void deleteContact() {

       ThreadPoolUtils.getInstance().getGlobalThreadPool().execute(new Runnable() {
            @Override
            public void run() {
                // 从环信服务器中删除联系人
                try {
                    EMClient.getInstance().contactManager().deleteContact(mHxid);

                    // 本地数据库删除
                   UserContactsDAO.init().deleteInvite(mHxid);

                    if (getActivity() == null) {
                        return;
                    }

                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            // toast提示
                            Toast.makeText(getActivity(), "删除" + mHxid + "成功", Toast.LENGTH_SHORT).show();

                            // 刷新页面
                            refreshContact();
                        }
                    });
                } catch (HyphenateException e) {
                    e.printStackTrace();

                    if (getActivity() == null) {
                        return;
                    }

                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getActivity(), "删除" + mHxid + "失败", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });
    }



    // 从环信服务器获取所有的联系人信息
    private void getContactFromHxServer() {
        ThreadPoolUtils.getInstance().getGlobalThreadPool().execute(new Runnable() {
            @Override
            public void run() {
                try {
                    List<String> hxids = EMClient.getInstance().contactManager().getAllContactsFromServer();
                    // 校验
                    if (hxids != null && hxids.size() >= 0) {

                        // 保存好友信息到本地数据库
                        for (String hxid : hxids) {
                            UserContactsTableBean userContactsTableBean=new UserContactsTableBean();
                            userContactsTableBean.setHxUsername(hxid);
                            userContactsTableBean.setIsContacts(true);
                            UserContactsDAO.init().addContacts(userContactsTableBean);
                        }

                        if (getActivity() == null) {
                            return;
                        }

                        // 刷新页面
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                // 刷新页面的方法
                                refreshContact();
                            }
                        });

                    }
                } catch (HyphenateException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        // 解除注册广播
        mLBM.unregisterReceiver(ContactInviteChangeReceiver);
        mLBM.unregisterReceiver(ContactChangeReceiver);
    }
}
