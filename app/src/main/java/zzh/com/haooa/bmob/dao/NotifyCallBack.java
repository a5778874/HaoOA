package zzh.com.haooa.bmob.dao;

import java.util.List;

import cn.bmob.v3.exception.BmobException;
import zzh.com.haooa.bmob.bean.Notify;

/**
 * Created by ZZH on 2018/4/6.
 */

public interface NotifyCallBack {
    void getNotifyList(List<Notify> list, BmobException e);
}
