package zzh.com.haooa.bmob.api;

import java.util.List;

import cn.bmob.v3.exception.BmobException;
import zzh.com.haooa.bmob.bean.Leave;

/**
 * Created by Administrator on 2018/5/24.
 */

public interface LeaveCallBack {
    void getLeaveLists(List<Leave> list, BmobException e);
}
