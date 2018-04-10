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
import android.widget.ProgressBar;

import com.cjj.MaterialRefreshLayout;
import com.cjj.MaterialRefreshListener;
import com.hyphenate.easeui.widget.EaseTitleBar;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

import zzh.com.haooa.R;
import zzh.com.haooa.Utils.ThreadPoolUtils;
import zzh.com.haooa.activity.notify.NotifyActivity;
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
    private ProgressBar pb_newslists;
    private List<news> newsList = new ArrayList<>();  //保存服务器返回的新闻列表

    private Handler mHandler;

    static class mHandler extends Handler {
        WeakReference<NewsActivity> mActivity;

        public mHandler(NewsActivity activity) {
            mActivity = new WeakReference<NewsActivity>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            final NewsActivity newsActivity = mActivity.get();
            if (msg.what == 1) {
                newsActivity.newsList.clear();
                newsActivity.newsList.addAll((List<news>) msg.obj);
                newsActivity.newsItemAdapter = new NewsItemAdapter(newsActivity, newsActivity.newsList);
                newsActivity.news_itemview.setAdapter(newsActivity.newsItemAdapter);
                newsActivity.news_itemview.setLayoutManager(new LinearLayoutManager(newsActivity, LinearLayoutManager.VERTICAL, false));
                newsActivity.news_itemview.addItemDecoration(new DividerItemDecoration(newsActivity, DividerItemDecoration.VERTICAL));
                newsActivity.newsItemAdapter.setmItemClickListener(new NewsItemAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(int position) {
                        news newsDetails = newsActivity.newsList.get(position);
                        //跳转到详情页
                        Intent it = new Intent(newsActivity, ShowNewsActivity.class);
                        it.putExtra("news", newsDetails);
                        newsActivity.startActivity(it);
                    }
                });
            }

        }
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);
        initView();
        mHandler = new mHandler(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        initDatas();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mHandler.removeCallbacksAndMessages(null);
    }

    private void initDatas() {
        initNewsDatas();

    }

    private void initNewsDatas() {
        pb_newslists.setVisibility(View.VISIBLE);
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
                            mHandler.sendMessage(message);
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    pb_newslists.setVisibility(View.GONE);
                                }
                            });

                        } else {
                            //获取失败0
                            Message message = new Message();
                            message.what = 0;
                            message.obj = e.toString();
                            mHandler.sendMessage(message);
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    pb_newslists.setVisibility(View.GONE);
                                }
                            });
                        }
                    }
                });
            }
        });

    }

    private void initView() {
        pb_newslists = findViewById(R.id.pb_newslists);
        newsTitleBar = findViewById(R.id.titlebar_news);
        newsTitleBar.setRightLayoutClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(NewsActivity.this, AddNewsActivity.class);
                it.putExtra("NewsStatus", AddNewsActivity.STATUS_ADD_NEWS);
                //跳转到新建新闻页面
                startActivity(it);
            }
        });
        newsTitleBar.setLeftLayoutClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NewsActivity.this.finish();
            }
        });

        news_itemview = findViewById(R.id.rv_news_item);
        refreshLayout = findViewById(R.id.refresh);
        //下拉刷新
        refreshLayout.setMaterialRefreshListener(new MaterialRefreshListener() {
            @Override
            public void onRefresh(MaterialRefreshLayout materialRefreshLayout) {
                initNewsDatas();
                //刷新完成
                refreshLayout.finishRefresh();
            }
        });

    }
}
