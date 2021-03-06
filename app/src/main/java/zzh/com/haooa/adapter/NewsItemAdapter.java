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
import zzh.com.haooa.bmob.bean.news;

/**
 * Created by ZZH on 2018/3/5.
 */

public class NewsItemAdapter extends RecyclerView.Adapter<NewsItemAdapter.MyHolder> implements View.OnClickListener{
    private List<news> newsList;
    private Context mContext;

    public NewsItemAdapter( Context mContext,List<news> newsList) {
        this.newsList = newsList;
        this.mContext = mContext;
        Log.d("TAG", "NewsItemAdapter: "+newsList.size());
    }

    @Override
    public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.item_news, null);
        v.setOnClickListener(this);
        return new MyHolder(v);
    }

    @Override
    public void onBindViewHolder(MyHolder holder, int position) {
        holder.itemView.setTag(position);
        holder.newsTitleItem.setText(newsList.get(position).getNews_title()+" ");
        holder.newsTimeItem.setText(newsList.get(position).getCreatedAt()+" ");
    }

    @Override
    public int getItemCount() {
        return newsList.size();
    }

    @Override
    public void onClick(View v) {
        if (mItemClickListener!=null){
            mItemClickListener.onItemClick((Integer) v.getTag());
        }
    }


    public class MyHolder extends RecyclerView.ViewHolder {
        private TextView newsTitleItem,newsTimeItem;

        public MyHolder(View itemView) {
            super(itemView);
            newsTitleItem=itemView.findViewById(R.id.tv_newstitle_item);
            newsTimeItem=itemView.findViewById(R.id.tv_newstime_item);
        }
    }

    //定义一个外部使用的接口
    public interface OnItemClickListener{
        void onItemClick(int position);
    }

    private OnItemClickListener mItemClickListener;

    public void setmItemClickListener(OnItemClickListener mItemClickListener) {
        this.mItemClickListener = mItemClickListener;
    }
}
