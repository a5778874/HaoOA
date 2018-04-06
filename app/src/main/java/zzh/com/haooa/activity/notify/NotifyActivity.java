package zzh.com.haooa.activity.notify;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;

import java.util.List;

import cn.bmob.v3.exception.BmobException;
import zzh.com.haooa.R;
import zzh.com.haooa.bmob.bean.Notify;
import zzh.com.haooa.bmob.dao.NotifyCallBack;
import zzh.com.haooa.bmob.dao.NotifyDAO;

/**
 * Created by ZZH on 2018/4/6.
 */

public class NotifyActivity extends Activity{
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notify);

    }
}
