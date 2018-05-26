package zzh.com.haooa.adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hyphenate.chat.EMClient;

import java.util.List;

import cn.bmob.v3.exception.BmobException;
import zzh.com.haooa.R;
import zzh.com.haooa.Utils.Constant;
import zzh.com.haooa.Utils.ToastUtils;
import zzh.com.haooa.bmob.api.BmobCallBack;
import zzh.com.haooa.bmob.api.LeaveApi;
import zzh.com.haooa.bmob.bean.Leave;
import zzh.com.haooa.bmob.bean.news;
import zzh.com.haooa.dao.UserInfoDAO;

/**
 * Created by Administrator on 2018/5/25.
 */

public class LeaveRecordItemAdapter extends RecyclerView.Adapter<LeaveRecordItemAdapter.MyHolder> {
    private List<Leave> leaveList;
    private Context mContext;


    public LeaveRecordItemAdapter(Context mContext, List<Leave> leaveList) {
        this.leaveList = leaveList;
        this.mContext = mContext;
    }


    @Override
    public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.item_leave_record, null);
        return new MyHolder(v);
    }

    @Override
    public void onBindViewHolder(final MyHolder holder, final int position) {
        String startTime = leaveList.get(position).getStartTime();
        String endTime = leaveList.get(position).getEndTime();
        String leave_text = leaveList.get(position).getLeaveReason();
        String applicationUser = leaveList.get(position).getUserID();

        final int status = leaveList.get(position).getApplyStatus();
        holder.item_startTime.setText(startTime);
        holder.item_endTime.setText(endTime);
        holder.item_text.setText(leave_text);
        String statusDesc = "";
        switch (status) {
            case Constant.APPLYING:
                holder.item_status.setTextColor(Color.GREEN);
                statusDesc = "审核中";
                break;
            case Constant.APPLY_SUCCESS:
                statusDesc = "已同意";
                holder.item_status.setTextColor(Color.BLUE);
                break;
            case Constant.APPLY_FAILURE:
                statusDesc = "不同意";
                holder.item_status.setTextColor(Color.RED);
                break;
        }
        holder.item_status.setText(statusDesc);
        holder.tv_application_user.setText(applicationUser);

        final String objID = leaveList.get(position).getObjectId();
        holder.btn_agree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new LeaveApi().updateStatus(objID, Constant.APPLY_SUCCESS, new BmobCallBack() {
                    @Override
                    public void getBmobException(BmobException e) {
                        if (e == null) {
                            ((Activity) mContext).runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    holder.ll_admin_btn.setVisibility(View.GONE);
                                    holder.item_status.setText("已同意");
                                    holder.item_status.setTextColor(Color.BLUE);
                                    ToastUtils.showToast(mContext, "已同意");
                                }
                            });

                        } else {
                            ToastUtils.showToast(mContext, "处理失败" + e);
                        }
                    }
                });

            }
        });

        holder.btn_refuse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new LeaveApi().updateStatus(objID, Constant.APPLY_FAILURE, new BmobCallBack() {
                    @Override
                    public void getBmobException(BmobException e) {
                        if (e == null) {
                            ((Activity) mContext).runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    holder.ll_admin_btn.setVisibility(View.GONE);
                                    holder.item_status.setText("不同意");
                                    holder.item_status.setTextColor(Color.RED);
                                    ToastUtils.showToast(mContext, "已拒绝");
                                }
                            });

                        } else {
                            ToastUtils.showToast(mContext, "处理失败" + e);
                        }
                    }
                });
            }
        });

        //如果为管理员，则显示管理员相应的布局
        String currentRole = UserInfoDAO.init().getUser().get(0).getDepartmentID();
        if (currentRole.equals("1005")) {
            holder.rl_admin_area.setVisibility(View.VISIBLE);

            //如果为假条申请中，显示处理按钮组
            if (status == Constant.APPLYING) {
                holder.ll_admin_btn.setVisibility(View.VISIBLE);
            } else {
                holder.ll_admin_btn.setVisibility(View.GONE);
            }

        } else {
            holder.rl_admin_area.setVisibility(View.GONE);
        }

    }

    @Override
    public int getItemCount() {
        return leaveList.size();
    }


    public class MyHolder extends RecyclerView.ViewHolder {
        private TextView item_startTime, item_endTime, item_text, item_status;
        private LinearLayout ll_admin_btn;
        private RelativeLayout rl_admin_area;
        private TextView tv_application_user;
        private TextView btn_agree, btn_refuse;

        public MyHolder(View itemView) {
            super(itemView);
            item_startTime = itemView.findViewById(R.id.tv_item_record_starttime);
            item_endTime = itemView.findViewById(R.id.tv_item_record_endtime);
            item_text = itemView.findViewById(R.id.tv_item_record_text);
            item_status = itemView.findViewById(R.id.tv_item_record_status);

            rl_admin_area = itemView.findViewById(R.id.rl_admin_area);
            ll_admin_btn = itemView.findViewById(R.id.ll_admin_btn);
            tv_application_user = itemView.findViewById(R.id.tv_application_user);
            btn_agree = itemView.findViewById(R.id.btn_agree);
            btn_refuse = itemView.findViewById(R.id.btn_refuse);

        }

    }


}
