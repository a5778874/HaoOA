package zzh.com.haooa.activity.leave;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.hyphenate.chat.EMClient;
import com.hyphenate.easeui.widget.EaseTitleBar;
import com.jzxiang.pickerview.TimePickerDialog;
import com.jzxiang.pickerview.data.Type;
import com.jzxiang.pickerview.listener.OnDateSetListener;

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;
import zzh.com.haooa.R;
import zzh.com.haooa.Utils.Constant;
import zzh.com.haooa.Utils.DateUtils;
import zzh.com.haooa.Utils.ThreadPoolUtils;
import zzh.com.haooa.Utils.ToastUtils;
import zzh.com.haooa.activity.notify.AddNotifyActivity;
import zzh.com.haooa.bmob.bean.Leave;

/**
 * Created by Administrator on 2018/4/17.
 */

public class LeaveApplyActivity extends AppCompatActivity implements OnDateSetListener {
    private EaseTitleBar easeTitleBar;
    private TimePickerDialog mDialogAll;
    private EditText et_starttime, et_endtime, et_leave_text;
    private long startTimeMillseconds = 0;   //请假起始时间
    private long endTimeMillseconds = 0;       //请假结束时间

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leave_apply);
        initView();
    }

    private void initView() {
        initTimePickerDialog();
        et_leave_text = findViewById(R.id.et_leave_text);

        et_starttime = findViewById(R.id.et_leave_starttime);
        et_starttime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDialogAll.show(getSupportFragmentManager(), "startTime");
            }
        });

        et_endtime = findViewById(R.id.et_leave_endtime);
        et_endtime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDialogAll.show(getSupportFragmentManager(), "EndTime");
            }
        });

        easeTitleBar = findViewById(R.id.leave_titleBar);
        easeTitleBar.setLeftLayoutClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LeaveApplyActivity.this.finish();
            }
        });
        easeTitleBar.setRightLayoutClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToLeaveRecordActivity();
            }
        });
    }


    private void initTimePickerDialog() {
        long tenYears = 10L * 365 * 1000 * 60 * 60 * 24L;
        mDialogAll = new TimePickerDialog.Builder()
                .setCallBack(this)
                .setCancelStringId("取消")
                .setSureStringId("确定")
                .setTitleStringId("请选择时间")
                .setYearText("年")
                .setMonthText("月")
                .setDayText("日")
                .setHourText("时")
                .setMinuteText("分")
                .setCyclic(false)
                .setMinMillseconds(System.currentTimeMillis())
                .setMaxMillseconds(System.currentTimeMillis() + tenYears)//最大选择为10年后
                .setCurrentMillseconds(System.currentTimeMillis())
                .setThemeColor(getResources().getColor(R.color.easeTitleBarBackground))
                .setType(Type.ALL)
                .setWheelItemTextNormalColor(getResources().getColor(R.color.timetimepicker_default_text_color))
                .setWheelItemTextSelectorColor(getResources().getColor(R.color.colorPrimaryDark))
                .setWheelItemTextSize(18)
                .build();
    }


    @Override
    public void onDateSet(TimePickerDialog timePickerView, long millseconds) {
        String date = DateUtils.millisecondToDate(millseconds);
        if (timePickerView.getTag().equals("startTime")) {
            startTimeMillseconds = millseconds;
            et_starttime.setText(date);
            //如果重新设置开始时间比结束时间大时，清空结束时间
            if (dateRangeCorrect(startTimeMillseconds, endTimeMillseconds)) {

            } else {
                et_endtime.setText("");
            }

        }
        if (timePickerView.getTag().equals("EndTime")) {
            endTimeMillseconds = millseconds;
            //如果格式正确则能设置
            if (dateRangeCorrect(startTimeMillseconds, endTimeMillseconds)) {
                et_endtime.setText(date);
            } else {
                ToastUtils.showToast(LeaveApplyActivity.this, "时间范围不正确");
            }

        }
    }

    //判断起始时间和结束时间选择是否正确
    private boolean dateRangeCorrect(long starttime, long endTime) {
        boolean b = starttime < endTime ? true : false;
        return b;
    }

    //判断输入信息有没有为空
    private boolean hasInputEmpty() {
        if (TextUtils.isEmpty(et_starttime.getText().toString()) || TextUtils.isEmpty(et_endtime.getText().toString()) || TextUtils.isEmpty(et_endtime.getText().toString())) {
            return true;
        } else {
            return false;
        }
    }


    //提交申请按钮
    public void submit(View view) {
        if (!hasInputEmpty()) {
            ThreadPoolUtils.getInstance().getGlobalThreadPool().execute(new Runnable() {
                @Override
                public void run() {
                    String currentUser = EMClient.getInstance().getCurrentUser();
                    //上传到服务器
                    Leave leave = new Leave();
                    leave.setUserID(currentUser);
                    leave.setStartTime(et_starttime.getText().toString());
                    leave.setEndTime(et_endtime.getText().toString());
                    leave.setLeaveReason(et_leave_text.getText().toString());
                    leave.setApplyStatus(Constant.APPLYING);
                    leave.save(new SaveListener<String>() {
                        @Override
                        public void done(String s, BmobException e) {
                            if (e == null) {
                                ToastUtils.showToast(LeaveApplyActivity.this, "申请成功");
                                goToLeaveRecordActivity();
                                LeaveApplyActivity.this.finish();
                            } else {
                                ToastUtils.showToast(LeaveApplyActivity.this, "申请失败" + e.getLocalizedMessage());
                            }
                        }


                    });


                }
            });
        } else {
            ToastUtils.showToast(LeaveApplyActivity.this, "请填写完整信息");
        }
    }

    private void goToLeaveRecordActivity() {
        startActivity(new Intent(LeaveApplyActivity.this, LeaveRecordActivity.class));
    }
}
