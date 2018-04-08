package zzh.com.haooa.activity.notify;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hyphenate.chat.EMClient;
import com.hyphenate.easeui.widget.EaseTitleBar;

import zzh.com.haooa.R;
import zzh.com.haooa.activity.newsActivity.ShowNewsActivity;
import zzh.com.haooa.bmob.bean.Notify;
import zzh.com.haooa.bmob.bean.news;
import zzh.com.haooa.dao.UserInfoDAO;

/**
 * Created by ZZH on 2018/4/7.
 */

public class ShowNotifyActivity extends Activity {
    private TextView notify_title, notify_text, notify_time, notify_department;
    private EaseTitleBar easeTitleBar;
    private RelativeLayout rl_notifydetails_edit;
    private Notify notifyDetails;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_notify);
        initView();
        initData();
    }

    private void initView() {
        notify_title = findViewById(R.id.tv_notifydetails_title);
        notify_text = findViewById(R.id.tv_notifydetails_content);
        notify_time = findViewById(R.id.tv_notifydetails_time);
        notify_department = findViewById(R.id.tv_notifydetails_department);
        rl_notifydetails_edit = findViewById(R.id.rl_notifydetails_edit);
        easeTitleBar=findViewById(R.id.titlebar_show_notify);
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
        String departmentName = "";
        //departmentID为0则该通知是通知所有部门
        if (departmentID.equals("0")) {
            departmentName = "所有";
        } else {
            departmentName = UserInfoDAO.init().getUser().get(0).getDepartmentName();
        }

        String author = notifyDetails.getAuthor();
        //如果作者是当前登录的用户，则有删除和编辑权限
        if (EMClient.getInstance().getCurrentUser().equals(author)) {
            rl_notifydetails_edit.setVisibility(View.VISIBLE);
        }
        notify_department.setText(departmentName);
        notify_title.setText(notifyDetails.getNotify_title());
        notify_text.setText(notifyDetails.getNotify_text());
        notify_time.setText(notifyDetails.getCreatedAt());


    }

}
