package zzh.com.haooa.bmob.api;

import java.util.List;

import cn.bmob.v3.exception.BmobException;
import zzh.com.haooa.bmob.bean.user;

/**
 * Created by ZZH on 2018/4/6.
 */

public interface UserCallBack {
    void getUser(List<user> list, BmobException e);
}
