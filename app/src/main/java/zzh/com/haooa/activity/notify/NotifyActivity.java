package zzh.com.haooa.activity.notify;

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

import cn.bmob.v3.exception.BmobException;
import zzh.com.haooa.R;
import zzh.com.haooa.Utils.ToastUtils;
import zzh.com.haooa.activity.newsActivity.AddNewsActivity;
import zzh.com.haooa.activity.newsActivity.NewsActivity;
import zzh.com.haooa.activity.newsActivity.ShowNewsActivity;
import zzh.com.haooa.adapter.NewsItemAdapter;
import zzh.com.haooa.adapter.NotifyItemAdapter;
import zzh.com.haooa.bmob.bean.Notify;
import zzh.com.haooa.bmob.bean.news;
import zzh.com.haooa.bmob.dao.NotifyCallBack;
import zzh.com.haooa.bmob.dao.NotifyDAO;
import zzh.com.haooa.dao.UserInfoDAO;
import zzh.com.haooa.greenDao.UserInfoBeanDao;

/**
 * Created by ZZH on 2018/4/6.
 */

public class NotifyActivity extends Activity {

    private MaterialRefreshLayout notify_refreshLayout;
    private EaseTitleBar notifyTitleBar;
    private RecyclerView notify_itemview;
    // private  newsItemAdapter;
    private ProgressBar pb_notifylists;
    private NotifyItemAdapter notifyItemAdapter;
    private List<Notify> notifyList = new ArrayList<>();  //保存服务器返回的通知列表

    private Handler mHandler;

    //防止内存泄漏的handler写法
    static class MyHandler extends Handler {
        WeakReference<NotifyActivity> mActivity;

        public MyHandler(NotifyActivity activity) {
            mActivity = new WeakReference<NotifyActivity>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            final NotifyActivity notifyActivity = mActivity.get();
            if (msg.what == 1) {
                notifyActivity.notifyList.clear();
                notifyActivity.notifyList.addAll((List<Notify>) msg.obj);
                notifyActivity.notifyItemAdapter = new NotifyItemAdapter(notifyActivity, notifyActivity.notifyList);
                notifyActivity.notify_itemview.setAdapter(notifyActivity.notifyItemAdapter);
                notifyActivity.notify_itemview.setLayoutManager(new LinearLayoutManager(notifyActivity, LinearLayoutManager.VERTICAL, false));
                notifyActivity.notify_itemview.addItemDecoration(new DividerItemDecoration(notifyActivity, DividerItemDecoration.VERTICAL));
                notifyActivity.notifyItemAdapter.setmItemClickListener(new NewsItemAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(int position) {
                        Notify notifyDetails = notifyActivity.notifyList.get(position);
                        //跳转到详情页
                        Intent it = new Intent(notifyActivity, ShowNotifyActivity.class);
                        it.putExtra("notify", notifyDetails);
                        notifyActivity.startActivity(it);
                    }
                });
            } else {

            }

        }
    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notify);
        mHandler = new MyHandler(this);
        initView();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mHandler.removeCallbacksAndMessages(null);
    }

    private void initView() {
        pb_notifylists = findViewById(R.id.pb_notifylists);
        notifyTitleBar = findViewById(R.id.titlebar_notify);
        notifyTitleBar.setLeftLayoutClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NotifyActivity.this.finish();
            }
        });
        notifyTitleBar.setRightLayoutClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //判断是否有权限发表公告，1为有权限，0为无权限
                Log.d("TAG", "onClick: "+UserInfoDAO.init().getUser().get(0).getRole());
                if (UserInfoDAO.init().getUser().get(0).getRole() == 1) {
                    Intent it = new Intent(NotifyActivity.this, AddNotifyActivity.class);
                    it.putExtra("NotifyStatus", AddNotifyActivity.STATUS_ADD_NOTIFY);
                    //跳转到新建公告页面
                    startActivity(it);
                } else {
                    ToastUtils.showToast(NotifyActivity.this, "你无权限发表公告");
                }
            }
        });
        notify_itemview = findViewById(R.id.rv_notify_item);
        notify_refreshLayout = findViewById(R.id.notify_refresh);
        //下拉刷新
        notify_refreshLayout.setMaterialRefreshListener(new MaterialRefreshListener() {
            @Override
            public void onRefresh(MaterialRefreshLayout materialRefreshLayout) {
                initNotifyDatas();
                //刷新完成
                notify_refreshLayout.finishRefresh();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        initDatas();
    }

    private void initDatas() {
        initNotifyDatas();
    }

    private void initNotifyDatas() {
        pb_notifylists.setVisibility(View.VISIBLE);
        //1.获取部门id
        String departmentID = UserInfoDAO.init().getUser().get(0).getDepartmentID();

        //2.只加载自己部门的通知
        new NotifyDAO().getNotifyList(new NotifyCallBack() {
            @Override
            public void getNotifyList(List<Notify> list, BmobException e) {
                if (e == null) {
                    //获取成功1
                    Message message = new Message();
                    message.what = 1;
                    message.obj = list;
                    mHandler.sendMessage(message);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            pb_notifylists.setVisibility(View.GONE);
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
                            pb_notifylists.setVisibility(View.GONE);
                        }
                    });
                }
            }
        });
    }


}
