package zzh.com.haooa.activity.leave;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.jzxiang.pickerview.TimePickerDialog;
import com.jzxiang.pickerview.data.Type;
import com.jzxiang.pickerview.listener.OnDateSetListener;

import zzh.com.haooa.R;
import zzh.com.haooa.Utils.DateUtils;
import zzh.com.haooa.Utils.ToastUtils;

/**
 * Created by Administrator on 2018/4/17.
 */

public class LeaveApplyActivity extends AppCompatActivity implements OnDateSetListener {
    private TimePickerDialog mDialogAll;
    private EditText et_starttime, et_endtime;
    private long startTimeMillseconds = 0;   //请假起始时间
    private long endTimeMillseconds = 0;       //请假结束时间

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leave_apply);
        initView();
    }

    private void initView() {
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
                .setMaxMillseconds(System.currentTimeMillis() + tenYears)
                .setCurrentMillseconds(System.currentTimeMillis())
                .setThemeColor(getResources().getColor(R.color.easeTitleBarBackground))
                .setType(Type.ALL)
                .setWheelItemTextNormalColor(getResources().getColor(R.color.timetimepicker_default_text_color))
                .setWheelItemTextSelectorColor(getResources().getColor(R.color.colorPrimaryDark))
                .setWheelItemTextSize(18)
                .build();

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


    }


    @Override
    public void onDateSet(TimePickerDialog timePickerView, long millseconds) {
        String date = DateUtils.millisecondToDate(millseconds);
        if (timePickerView.getTag().equals("startTime")) {
            startTimeMillseconds = millseconds;
            et_starttime.setText(date);
            //如果重新设置开始时间比结束时间大时，清空结束时间
            if (dateRangeCorrect(startTimeMillseconds,endTimeMillseconds)){

            }else {
                et_endtime.setText("");
            }

        }
        if (timePickerView.getTag().equals("EndTime")) {
            endTimeMillseconds = millseconds;
            //如果格式正确则能设置
            if (dateRangeCorrect(startTimeMillseconds,endTimeMillseconds)){
                et_endtime.setText(date);
            }else {
                ToastUtils.showToast(LeaveApplyActivity.this,"时间范围不正确");
            }

        }
    }

    //判断起始时间和结束时间选择是否正确
    private boolean dateRangeCorrect(long starttime, long endTime) {
        boolean b = starttime < endTime ? true : false;
        return b;
    }
}
