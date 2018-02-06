package zzh.com.haooa.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import zzh.com.haooa.EventBus.LoginEvent;
import zzh.com.haooa.R;
import zzh.com.haooa.activity.MainActivity;

/**
 * Created by ZZH on 2018/1/25.
 */

public class WorkFragment extends Fragment {
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_work, null);
        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

    }
}
