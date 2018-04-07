package zzh.com.haooa.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import zzh.com.haooa.R;
import zzh.com.haooa.bmob.bean.Notify;

/**
 * Created by ZZH on 2018/4/7.
 */

public class NotifyItemAdapter extends RecyclerView.Adapter<NotifyItemAdapter.MyHolder> implements View.OnClickListener {

    private List<Notify> notifyList;
    private Context mContext;

    public NotifyItemAdapter(Context mContext, List<Notify> notifyList) {
        this.notifyList = notifyList;
        this.mContext = mContext;
        Log.d("TAG", "NotifyItemAdapter: " + notifyList.size());
    }


    @Override
    public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.item_notify, null);
        v.setOnClickListener(this);
        return new NotifyItemAdapter.MyHolder(v);
    }

    @Override
    public void onBindViewHolder(MyHolder holder, int position) {
        holder.itemView.setTag(position);
        holder.notifyTitleItem.setText(notifyList.get(position).getNotify_title()+" ");
        holder.notifyTimeItem.setText(notifyList.get(position).getCreatedAt()+" ");

    }

    @Override
    public int getItemCount() {
        return notifyList.size();
    }

    @Override
    public void onClick(View v) {
        if (mItemClickListener!=null){
            mItemClickListener.onItemClick((Integer) v.getTag());
        }
    }

    public class MyHolder extends RecyclerView.ViewHolder {
        private TextView notifyTitleItem,notifyTimeItem;

        public MyHolder(View itemView) {
            super(itemView);
            notifyTitleItem=itemView.findViewById(R.id.tv_notifytitle_item);
            notifyTimeItem=itemView.findViewById(R.id.tv_notifytime_item);
        }
    }

    //定义一个外部使用的接口
    public interface OnItemClickListener{
        void onItemClick(int position);
    }

    private NewsItemAdapter.OnItemClickListener mItemClickListener;

    public void setmItemClickListener(NewsItemAdapter.OnItemClickListener mItemClickListener) {
        this.mItemClickListener = mItemClickListener;
    }
}
