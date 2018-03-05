package zzh.com.haooa.activity.newsActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.cjj.MaterialRefreshLayout;
import com.cjj.MaterialRefreshListener;
import com.hyphenate.easeui.widget.EaseTitleBar;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

import zzh.com.haooa.R;
import zzh.com.haooa.Utils.ThreadPoolUtils;
import zzh.com.haooa.adapter.NewsItemAdapter;
import zzh.com.haooa.bmob.bean.news;

/**
 * Created by ZZH on 2018/3/5.
 */

public class NewsActivity extends Activity {
    private MaterialRefreshLayout refreshLayout;
    private EaseTitleBar newsTitleBar;
    private RecyclerView news_itemview;
    private NewsItemAdapter newsItemAdapter;
    private List<news> newsList = new ArrayList<>();
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 1) {
                newsList.clear();
                newsList.addAll((List<news>) msg.obj);
                newsItemAdapter = new NewsItemAdapter(NewsActivity.this, newsList);
                news_itemview.setAdapter(newsItemAdapter);
                news_itemview.setLayoutManager(new LinearLayoutManager(NewsActivity.this, LinearLayoutManager.VERTICAL, false));
                news_itemview.addItemDecoration(new DividerItemDecoration(NewsActivity.this,DividerItemDecoration.VERTICAL));
                Log.d("TAG", "handleMessage: " + newsList.size());
            } else {
                Log.d("TAG", "handleMessage: " + msg.obj.toString());
            }
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);
        initView();
        initDatas();

    }

    private void initDatas() {
        initNewsDatas();

    }

    private void initNewsDatas() {
        ThreadPoolUtils.getInstance().getGlobalThreadPool().execute(new Runnable() {
            @Override
            public void run() {
                BmobQuery<news> query = new BmobQuery<news>();
                query.setLimit(300);
                query.findObjects(new FindListener<news>() {
                    @Override
                    public void done(List<news> list, BmobException e) {
                        if (e == null) {
                            //获取成功1
                            Message message = new Message();
                            message.what = 1;
                            message.obj = list;
                            handler.sendMessage(message);
                        } else {
                            //获取失败0
                            Message message = new Message();
                            message.what = 0;
                            message.obj = e.toString();
                            handler.sendMessage(message);
                        }
                    }
                });
            }
        });

    }

    private void initView() {
        newsTitleBar = findViewById(R.id.titlebar_news);
        newsTitleBar.setRightLayoutClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //跳转到新建新闻页面
                startActivity(new Intent(NewsActivity.this, AddNewsActivity.class));
            }
        });

        news_itemview = findViewById(R.id.rv_news_item);
        refreshLayout = findViewById(R.id.refresh);
        //下拉刷新
        refreshLayout.setMaterialRefreshListener(new MaterialRefreshListener() {
            @Override
            public void onRefresh(MaterialRefreshLayout materialRefreshLayout) {
                //刷新完成
                refreshLayout.finishRefresh();
            }
        });

    }
}
