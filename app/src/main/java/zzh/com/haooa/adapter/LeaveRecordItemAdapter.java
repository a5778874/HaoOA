package zzh.com.haooa.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import zzh.com.haooa.R;
import zzh.com.haooa.Utils.Constant;
import zzh.com.haooa.bmob.bean.Leave;
import zzh.com.haooa.bmob.bean.news;

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
    public void onBindViewHolder(MyHolder holder, int position) {
        String startTime = leaveList.get(position).getStartTime();
        String endTime = leaveList.get(position).getEndTime();
        String leave_text =leaveList.get(position).getLeaveReason();
        int status = leaveList.get(position).getApplyStatus();
        holder.item_startTime.setText(startTime);
        holder.item_endTime.setText(endTime);
        holder.item_text.setText(leave_text);
        String statusDesc = "";
        switch (status) {
            case Constant.APPLYING:
                holder.item_status.setTextColor(Color.RED);
                statusDesc = "审核中";
                break;
            case Constant.APPLY_SUCCESS:
                statusDesc = "已同意";
                holder.item_status.setTextColor(Color.BLUE);
                break;
            case Constant.APPLY_FAILURE:
                statusDesc = "不同意";
                holder.item_status.setTextColor(Color.BLUE);
                break;
        }
        holder.item_status.setText(statusDesc);

    }

    @Override
    public int getItemCount() {
        return leaveList.size();
    }

    public class MyHolder extends RecyclerView.ViewHolder {
        private TextView item_startTime, item_endTime, item_text,item_status;

        public MyHolder(View itemView) {
            super(itemView);
            item_startTime = itemView.findViewById(R.id.tv_item_record_starttime);
            item_endTime = itemView.findViewById(R.id.tv_item_record_endtime);
            item_text=itemView.findViewById(R.id.tv_item_record_text);
            item_status = itemView.findViewById(R.id.tv_item_record_status);
        }
    }
}
