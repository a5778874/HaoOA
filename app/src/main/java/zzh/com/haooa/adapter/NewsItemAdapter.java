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

public class NewsItemAdapter extends RecyclerView.Adapter<NewsItemAdapter.MyHolder> {
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
        return new MyHolder(v);
    }

    @Override
    public void onBindViewHolder(MyHolder holder, int position) {
        holder.newsTitleItem.setText(newsList.get(position).getNews_title()+" ");
        holder.newsTimeItem.setText(newsList.get(position).getCreatedAt()+" ");
    }

    @Override
    public int getItemCount() {
        return newsList.size();
    }

    public class MyHolder extends RecyclerView.ViewHolder {
        private TextView newsTitleItem,newsTimeItem;

        public MyHolder(View itemView) {
            super(itemView);
            newsTitleItem=itemView.findViewById(R.id.tv_newstitle_item);
            newsTimeItem=itemView.findViewById(R.id.tv_newstime_item);
        }
    }
}
