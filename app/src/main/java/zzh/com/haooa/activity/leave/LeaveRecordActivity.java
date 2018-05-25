package zzh.com.haooa.activity.leave;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.hyphenate.chat.EMClient;
import com.hyphenate.easeui.widget.EaseTitleBar;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.exception.BmobException;
import zzh.com.haooa.R;
import zzh.com.haooa.Utils.ToastUtils;
import zzh.com.haooa.adapter.LeaveRecordItemAdapter;
import zzh.com.haooa.bmob.api.DepartmentApi;
import zzh.com.haooa.bmob.api.LeaveApi;
import zzh.com.haooa.bmob.api.LeaveCallBack;
import zzh.com.haooa.bmob.bean.Leave;

/**
 * Created by Administrator on 2018/5/23.
 */

public class LeaveRecordActivity extends Activity {
    private ProgressBar progressBar;
    private EaseTitleBar easeTitleBar;
    private ImageView iv_noMessage;
    private RecyclerView rv_leave_record;
    private List<Leave> leaveRecordList = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leave_record);
        initView();
    }

    @Override
    protected void onResume() {
        super.onResume();
        initLeaveRecordData();
    }

    private void initLeaveRecordData() {
        progressBar.setVisibility(View.VISIBLE);
        String userID = EMClient.getInstance().getCurrentUser();
        new LeaveApi().getLeaveByUser(userID, new LeaveCallBack() {
            @Override
            public void getLeaveLists(List<Leave> list, final BmobException e) {
                Log.d("TAG2", "getLeaveLists: " + list.size());
                if (e == null) {
                    //如果有记录则显示列表，没记录显示暂无消息图片
                    if (list.size() > 0) {
                        leaveRecordList.clear();
                        leaveRecordList.addAll(list);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                progressBar.setVisibility(View.GONE);
                                iv_noMessage.setVisibility(View.GONE);
                                rv_leave_record.setVisibility(View.VISIBLE);
                                rv_leave_record.setAdapter(new LeaveRecordItemAdapter(LeaveRecordActivity.this, leaveRecordList));
                                rv_leave_record.setLayoutManager(new LinearLayoutManager(LeaveRecordActivity.this, LinearLayoutManager.VERTICAL, false));
                                rv_leave_record.addItemDecoration(new DividerItemDecoration(LeaveRecordActivity.this, DividerItemDecoration.VERTICAL));
                            }
                        });
                    } else {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                progressBar.setVisibility(View.GONE);
                                rv_leave_record.setVisibility(View.GONE);
                                iv_noMessage.setVisibility(View.VISIBLE);

                            }
                        });

                    }

                } else {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            progressBar.setVisibility(View.GONE);
                            ToastUtils.showToast(LeaveRecordActivity.this, "获取数据失败" + e);
                        }
                    });
                }
            }
        });
    }

    private void initView() {
        progressBar = findViewById(R.id.pb_leave_record);
        iv_noMessage = findViewById(R.id.iv_leave_record_no_message);
        rv_leave_record = findViewById(R.id.rv_leave_record);

        easeTitleBar = findViewById(R.id.leave_record_titleBar);
        easeTitleBar.setLeftLayoutClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LeaveRecordActivity.this.finish();
            }
        });

    }
}
