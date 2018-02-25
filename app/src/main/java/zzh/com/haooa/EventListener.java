package zzh.com.haooa;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.hyphenate.EMContactListener;
import com.hyphenate.EMGroupChangeListener;
import com.hyphenate.chat.EMClient;

import zzh.com.haooa.Utils.Constant;
import zzh.com.haooa.Utils.SpUtils;
import zzh.com.haooa.bean.InviteTableBean;
import zzh.com.haooa.bean.UserContactsTableBean;
import zzh.com.haooa.dao.InviteTableDAO;
import zzh.com.haooa.dao.UserContactsDAO;
import zzh.com.haooa.greenDao.InviteTableBeanDao;


/**
 * Created by Administrator on 2016/9/24.
 */
// 环信全局事件监听类
public class EventListener {
    private String TAG="TAG";
    private Context mContext;
    private final LocalBroadcastManager mLBM;

    public EventListener(Context context) {
        mContext = context;

        // 创建一个发送广播的管理者对象
        mLBM = LocalBroadcastManager.getInstance(mContext);

        // 注册一个联系人变化的监听
        EMClient.getInstance().contactManager().setContactListener(emContactListener);


    }

    private final EMContactListener emContactListener = new EMContactListener() {
        // 联系人增加后执行的方法
        @Override
        public void onContactAdded(String s) {
            Log.d(TAG, "EventListener onContactAdded: ..联系人增加");
            // 数据库添加
            UserContactsTableBean userContactsTableBean = new UserContactsTableBean();
            userContactsTableBean.setHxUsername(s);
            userContactsTableBean.setIsContacts(true);
            UserContactsDAO.init().addContacts(userContactsTableBean);
            // 发送联系人变化的广播
            mLBM.sendBroadcast(new Intent(Constant.CONTACT_CHANGED));
        }

        // 联系人删除后执行的方法
        @Override
        public void onContactDeleted(String s) {
            Log.d(TAG, "EventListener onContactDeleted: ..联系人删除");
            //TODO
        }

        // 接受到联系人的新邀请
        @Override
        public void onContactInvited(String name, String reason) {
            Log.d(TAG, "EventListener onContactInvited: ..接受到联系人的新邀请");
            // 数据库邀请表的添加
            InviteTableBean inviteTableBean = new InviteTableBean();
            inviteTableBean.setUser_hxUsername(name);
            inviteTableBean.setReason(reason);
            inviteTableBean.setStatus(Constant.InvitationStatus.NEW_INVITE.ordinal());// 新邀请
            InviteTableDAO.init().addInvite(inviteTableBean);

            // 红点的处理
            SpUtils.getInstance().save(Constant.IS_NEW_INVITE, true);
            // 发送邀请信息变化的广播
            mLBM.sendBroadcast(new Intent(Constant.CONTACT_INVITE_CHANGED));

        }

        // 别人同意了你的好友邀请
        @Override
        public void onFriendRequestAccepted(String name) {
            Log.d(TAG, "EventListener onFriendRequestAccepted: ..别人同意了你的好友邀请");
            // 数据库邀请表的更新
            InviteTableBean inviteTableBean = new InviteTableBean();
            inviteTableBean.setUser_hxUsername(name);
            inviteTableBean.setStatus(Constant.InvitationStatus.INVITE_ACCEPT_BY_PEER.ordinal());// 新邀请
            InviteTableDAO.init().updateInvite(inviteTableBean);

            // 红点的处理
            SpUtils.getInstance().save(Constant.IS_NEW_INVITE, true);
            // 发送邀请信息变化的广播
            mLBM.sendBroadcast(new Intent(Constant.CONTACT_INVITE_CHANGED));
        }

        // 别人拒绝了你好友邀请
        @Override
        public void onFriendRequestDeclined(String s) {
            Log.d(TAG, "EventListener onFriendRequestDeclined: .. 别人拒绝了你好友邀请");
            // 红点的处理
            SpUtils.getInstance().save(Constant.IS_NEW_INVITE, true);

            // 发送邀请信息变化的广播
            mLBM.sendBroadcast(new Intent(Constant.CONTACT_INVITE_CHANGED));
        }
    };

}
