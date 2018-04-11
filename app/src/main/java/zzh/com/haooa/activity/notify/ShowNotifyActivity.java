package zzh.com.haooa.activity.notify;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hyphenate.chat.EMClient;
import com.hyphenate.easeui.widget.EaseTitleBar;

import java.lang.ref.WeakReference;
import java.util.List;

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.UpdateListener;
import zzh.com.haooa.R;
import zzh.com.haooa.Utils.ThreadPoolUtils;
import zzh.com.haooa.Utils.ToastUtils;
import zzh.com.haooa.activity.newsActivity.AddNewsActivity;
import zzh.com.haooa.activity.newsActivity.NewsActivity;
import zzh.com.haooa.activity.newsActivity.ShowNewsActivity;
import zzh.com.haooa.adapter.NewsItemAdapter;
import zzh.com.haooa.bmob.bean.Notify;
import zzh.com.haooa.bmob.bean.news;
import zzh.com.haooa.bmob.dao.BmobStringCallBack;
import zzh.com.haooa.bmob.dao.DepartmentDAO;
import zzh.com.haooa.dao.UserInfoDAO;

/**
 * Created by ZZH on 2018/4/7.
 */

public class ShowNotifyActivity extends Activity implements View.OnClickListener {
    private TextView notify_title;
    private TextView notify_text;
    private TextView notify_time;
    private static TextView notify_department;
    private EaseTitleBar easeTitleBar;
    private RelativeLayout rl_notifydetails_edit;
    private ImageView iv_notifydetails_delete, iv_notifydetails_edit;
    private Notify notifyDetails;
    String departmentName;

    private Handler mHandler;

    static class mHandler extends Handler {
        WeakReference<ShowNotifyActivity> mActivity;

        public mHandler(ShowNotifyActivity activity) {
            mActivity = new WeakReference<ShowNotifyActivity>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            final ShowNotifyActivity showNotifyActivity = mActivity.get();
            if (msg.what == 1) {
                notify_department.setText(msg.obj.toString());
            }

        }
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_notify);
        mHandler = new mHandler(this);
        initView();
        initData();
    }

    private void initView() {
        iv_notifydetails_delete = findViewById(R.id.iv_notifydetails_delete);
        iv_notifydetails_delete.setOnClickListener(this);
        iv_notifydetails_edit = findViewById(R.id.iv_notifydetails_edit);
        iv_notifydetails_edit.setOnClickListener(this);
        notify_title = findViewById(R.id.tv_notifydetails_title);
        notify_text = findViewById(R.id.tv_notifydetails_content);
        notify_time = findViewById(R.id.tv_notifydetails_time);
        notify_department = findViewById(R.id.tv_notifydetails_department);
        rl_notifydetails_edit = findViewById(R.id.rl_notifydetails_edit);
        easeTitleBar = findViewById(R.id.titlebar_show_notify);
        easeTitleBar.setLeftLayoutClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShowNotifyActivity.this.finish();
            }
        });
    }

    private void initData() {
        //获取传递过来的通知内容
        Intent it = getIntent();
        notifyDetails = (Notify) it.getSerializableExtra("notify");

        String departmentID = notifyDetails.getDepartmentID();

        //departmentID为0则该通知是通知所有部门
        if (departmentID.equals("0")) {
            notify_department.setText("所有");

        } else {
            new DepartmentDAO().getDepartmentByID(notifyDetails.getDepartmentID(), new BmobStringCallBack() {
                @Override
                public void getName(String name, BmobException e) {
                    if (e == null) {
                        Message message = new Message();
                        message.what = 1;
                        message.obj = name;
                        mHandler.sendMessage(message);
                    }
                }
            });

        }

        String author = notifyDetails.getAuthor();
        //如果作者是当前登录的用户，则有删除和编辑权限
        if (EMClient.getInstance().getCurrentUser().equals(author)) {
            rl_notifydetails_edit.setVisibility(View.VISIBLE);
        }
        notify_title.setText(notifyDetails.getNotify_title());
        notify_text.setText(notifyDetails.getNotify_text());
        notify_time.setText(notifyDetails.getCreatedAt());


    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.iv_notifydetails_delete:
                //删除通知
                ThreadPoolUtils.getInstance().getGlobalThreadPool().execute(new Runnable() {
                    @Override
                    public void run() {
                        Notify notify = new Notify();
                        notify.setObjectId(notifyDetails.getObjectId());
                        notify.delete(new UpdateListener() {
                            @Override
                            public void done(BmobException e) {
                                if (e == null) {
                                    ToastUtils.showToast(ShowNotifyActivity.this, "删除成功");
                                    ShowNotifyActivity.this.finish();
                                } else {
                                    ToastUtils.showToast(ShowNotifyActivity.this, "删除失败");
                                }
                            }
                        });
                    }
                });

                break;
            case R.id.iv_notifydetails_edit:
                //跳转到编辑页面
                Intent it = new Intent(ShowNotifyActivity.this, AddNotifyActivity.class);
                it.putExtra("NotifyStatus", AddNotifyActivity.STATUS_EDIT_NOTIFY);
                it.putExtra("NotifyDetails", notifyDetails);
                startActivity(it);
                ShowNotifyActivity.this.finish();
                break;
        }

    }
}
