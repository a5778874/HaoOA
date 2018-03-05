package zzh.com.haooa.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import zzh.com.haooa.EventBus.LoginEvent;
import zzh.com.haooa.R;
import zzh.com.haooa.Utils.ToastUtils;
import zzh.com.haooa.activity.MainActivity;

/**
 * Created by ZZH on 2018/1/25.
 */

public class WorkFragment extends Fragment implements View.OnClickListener{
    private TextView tv_notification, tv_news, tv_leave, tv_goout, tv_schedule, tv_vote, tv_todo, tv_diary, tv_sign, tv_more;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_work, null);
        initView(view);
        return view;
    }

    private void initView(View view) {
        tv_notification=view.findViewById(R.id.tv_notification);
        tv_notification.setOnClickListener(this);
        tv_news=view.findViewById(R.id.tv_news);
        tv_news.setOnClickListener(this);
        tv_leave=view.findViewById(R.id.tv_leave);
        tv_leave.setOnClickListener(this);
        tv_goout=view.findViewById(R.id.tv_goout);
        tv_goout.setOnClickListener(this);
        tv_schedule=view.findViewById(R.id.tv_schedule);
        tv_schedule.setOnClickListener(this);
        tv_vote=view.findViewById(R.id.tv_vote);
        tv_vote.setOnClickListener(this);
        tv_todo=view.findViewById(R.id.tv_todo);
        tv_todo.setOnClickListener(this);
        tv_diary=view.findViewById(R.id.tv_diary);
        tv_diary.setOnClickListener(this);
        tv_sign=view.findViewById(R.id.tv_sign);
        tv_sign.setOnClickListener(this);
        tv_more=view.findViewById(R.id.tv_more);
        tv_more.setOnClickListener(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_notification:
                ToastUtils.showToast(getActivity(),"此功能待开发！");
                break;
            case R.id.tv_news:
                ToastUtils.showToast(getActivity(),"此功能待开发！");
                break;
            case R.id.tv_leave:
                ToastUtils.showToast(getActivity(),"此功能待开发！");
                break;
            case R.id.tv_goout:
                ToastUtils.showToast(getActivity(),"此功能待开发！");
                break;
            case R.id.tv_schedule:
                ToastUtils.showToast(getActivity(),"此功能待开发！");
                break;
            case R.id.tv_vote:
                ToastUtils.showToast(getActivity(),"此功能待开发！");
                break;
            case R.id.tv_todo:
                ToastUtils.showToast(getActivity(),"此功能待开发！");
                break;
            case R.id.tv_diary:
                ToastUtils.showToast(getActivity(),"此功能待开发！");
                break;
            case R.id.tv_sign:
                ToastUtils.showToast(getActivity(),"此功能待开发！");
                break;
            case R.id.tv_more:
                ToastUtils.showToast(getActivity(),"此功能待开发！");
                break;
        }

    }
}
