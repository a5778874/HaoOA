package zzh.com.haooa.adapter;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;


import java.util.ArrayList;
import java.util.List;

import zzh.com.haooa.R;
import zzh.com.haooa.Utils.Constant;
import zzh.com.haooa.Utils.ToastUtils;
import zzh.com.haooa.bean.InviteTableBean;

/**
 * Created by ZZH on 2018/2/25.
 */
// 邀请信息列表页面的适配器
public class InviteAdapter extends BaseAdapter {
    private String TAG="TAG";
    private Context mContext;
    private List<InviteTableBean> mInviteTableBeans = new ArrayList<>();
    private OnInviteListener mOnInviteListener;
    private InviteTableBean inviteTableBean;

    public InviteAdapter(Context context, OnInviteListener onInviteListener) {
        mContext = context;
        mOnInviteListener = onInviteListener;
    }

    // 刷新数据的方法
    public void refresh(List<InviteTableBean> inviteTableBeans) {

        if (inviteTableBeans != null && inviteTableBeans.size() >= 0) {

            mInviteTableBeans.clear();

            mInviteTableBeans.addAll(inviteTableBeans);
            Log.d("TAG", "inviteTableBeans: " + inviteTableBeans.size());

            notifyDataSetChanged();
        }
    }

    @Override
    public int getCount() {
        return mInviteTableBeans == null ? 0 : mInviteTableBeans.size();
    }

    @Override
    public Object getItem(int position) {
        return mInviteTableBeans.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // 1 获取或创建viewHolder
        ViewHodler hodler = null;
        if (convertView == null) {
            hodler = new ViewHodler();

            convertView = View.inflate(mContext, R.layout.item_invite, null);

            hodler.name = (TextView) convertView.findViewById(R.id.tv_invite_username);
            hodler.reason = (TextView) convertView.findViewById(R.id.tv_invite_reason);

            hodler.accept = (Button) convertView.findViewById(R.id.bt_accept);
            hodler.reject = (Button) convertView.findViewById(R.id.bt_reject);

            convertView.setTag(hodler);
        } else {
            hodler = (ViewHodler) convertView.getTag();
        }

        // 2 获取当前item数据
        inviteTableBean = mInviteTableBeans.get(position);

        // 3 显示当前item数据
        String user = inviteTableBean.getUser_hxUsername();
        Log.d(TAG, "adapter getView: "+user);
        if (user != null) {// 联系人
            // 名称的展示
            hodler.name.setText(user);

            hodler.accept.setVisibility(View.GONE);
            hodler.reject.setVisibility(View.GONE);
            Log.d(TAG, "adapter status: "+inviteTableBean.getStatus());
            // 原因
            if (inviteTableBean.getStatus() == Constant.InvitationStatus.NEW_INVITE.ordinal()) {// 新的邀请
                Log.d(TAG, "adapter  新的邀请 ");
                if (inviteTableBean.getReason() == null) {
                    hodler.reason.setText("对方请求备注：添加好友");
                } else {
                    hodler.reason.setText("对方请求备注：" + inviteTableBean.getReason());
                }

                hodler.accept.setVisibility(View.VISIBLE);
                hodler.reject.setVisibility(View.VISIBLE);

            } else if (inviteTableBean.getStatus() == Constant.InvitationStatus.INVITE_ACCEPT.ordinal()) {// 接受邀请
                Log.d(TAG, "adapter  接受邀请 ");
                if (inviteTableBean.getReason() == null) {
                    hodler.reason.setText("接受邀请");
                } else {
                    hodler.reason.setText(inviteTableBean.getReason());
                }
            } else if (inviteTableBean.getStatus() == Constant.InvitationStatus.INVITE_ACCEPT_BY_PEER.ordinal()) {// 邀请被接受
                hodler.reason.setText("邀请被接受");
                if (inviteTableBean.getReason() == null) {
                    hodler.reason.setText("邀请被接受");
                } else {
                    hodler.reason.setText(inviteTableBean.getReason());
                }
            }

            // 按钮的处理
            hodler.accept.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mOnInviteListener.onAccept(inviteTableBean);

                }
            });

            // 拒绝按钮的点击事件处理
            hodler.reject.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ToastUtils.showToast(mContext, "reject...click");
                    mOnInviteListener.onReject(inviteTableBean);
                }
            });
        } else {// 群组
            // 显示名称
            hodler.name.setText(inviteTableBean.getUser_hxUsername());
            hodler.accept.setVisibility(View.GONE);
            hodler.reject.setVisibility(View.GONE);
            if (inviteTableBean.getStatus() == Constant.InvitationStatus.GROUP_APPLICATION_ACCEPTED.ordinal()) {
                // 您的群申请请已经被接受
                hodler.reason.setText("您的群申请请已经被接受");

            } else if (inviteTableBean.getStatus() == Constant.InvitationStatus.GROUP_INVITE_ACCEPTED.ordinal()) {
                //  您的群邀请已经被接收
                hodler.reason.setText("您的群邀请已经被接收");
            } else if (inviteTableBean.getStatus() == Constant.InvitationStatus.GROUP_APPLICATION_DECLINED.ordinal()) {
                // 你的群申请已经被拒绝
                hodler.reason.setText("你的群申请已经被拒绝");
            } else if (inviteTableBean.getStatus() == Constant.InvitationStatus.GROUP_INVITE_DECLINED.ordinal()) {
                // 您的群邀请已经被拒绝
                hodler.reason.setText("您的群邀请已经被拒绝");
            } else if (inviteTableBean.getStatus() == Constant.InvitationStatus.NEW_GROUP_INVITE.ordinal()) {
                // 您收到了群邀请
                hodler.accept.setVisibility(View.VISIBLE);
                hodler.reject.setVisibility(View.VISIBLE);

                // 接受邀请
                hodler.accept.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mOnInviteListener.onInviteAccept(inviteTableBean);
                    }
                });

                // 拒绝邀请
                hodler.reject.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mOnInviteListener.onInviteReject(inviteTableBean);
                    }
                });

                hodler.reason.setText("您收到了群邀请");
            } else if (inviteTableBean.getStatus() == Constant.InvitationStatus.NEW_GROUP_APPLICATION.ordinal()) {
                // 您收到了群申请
                hodler.accept.setVisibility(View.VISIBLE);
                hodler.reject.setVisibility(View.VISIBLE);

                // 接受申请
                hodler.accept.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mOnInviteListener.onApplicationAccept(inviteTableBean);
                    }
                });

                // 拒绝申请
                hodler.reject.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mOnInviteListener.onApplicationReject(inviteTableBean);
                    }
                });
                hodler.reason.setText("您收到了群申请");
            } else if (inviteTableBean.getStatus() == Constant.InvitationStatus.GROUP_ACCEPT_INVITE.ordinal()) {
                // 你接受了群邀请
                hodler.reason.setText("你接受了群邀请");
            } else if (inviteTableBean.getStatus() == Constant.InvitationStatus.GROUP_ACCEPT_APPLICATION.ordinal()) {
                // 您批准了群申请
                hodler.reason.setText("您批准了群申请");
            } else if (inviteTableBean.getStatus() == Constant.InvitationStatus.GROUP_REJECT_INVITE.ordinal()) {
                // 您拒绝了群邀请
                hodler.reason.setText("您拒绝了群邀请");
            }

        }

        // 4 返回view
        return convertView;
    }

    private class ViewHodler {
        private TextView name;
        private TextView reason;

        private Button accept;
        private Button reject;
    }


    public interface OnInviteListener {
        // 联系人接受按钮的点击事件
        void onAccept(InviteTableBean invationInfo);

        // 联系人拒绝按钮的点击事件
        void onReject(InviteTableBean invationInf);

        // 接受邀请按钮处理
        void onInviteAccept(InviteTableBean invationInfo);

        // 拒绝邀请按钮处理
        void onInviteReject(InviteTableBean invationInfo);

        // 接受申请按钮处理
        void onApplicationAccept(InviteTableBean invationInfo);

        // 拒绝申请按钮处理
        void onApplicationReject(InviteTableBean invationInfo);
    }


}
