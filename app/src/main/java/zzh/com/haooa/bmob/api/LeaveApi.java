package zzh.com.haooa.bmob.api;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.UpdateListener;
import zzh.com.haooa.Utils.ThreadPoolUtils;
import zzh.com.haooa.bmob.bean.Leave;
import zzh.com.haooa.bmob.bean.user;

/**
 * Created by Administrator on 2018/5/24.
 */

public class LeaveApi {

    //根据用户查询自己的请假信息
    public void getLeaveByUser(final String hxUsername, final LeaveCallBack leaveCallBack) {
        ThreadPoolUtils.getInstance().getGlobalThreadPool().execute(new Runnable() {
            @Override
            public void run() {
                BmobQuery<Leave> query = new BmobQuery<Leave>();
                query.addWhereEqualTo("userID", hxUsername);
                query.setLimit(300);
                query.findObjects(new FindListener<Leave>() {
                    @Override
                    public void done(List<Leave> list, BmobException e) {
                        leaveCallBack.getLeaveLists(list, e);
                    }
                });
            }
        });
    }


    //查询全部请假信息
    public void getAllLeave(final String hxUsername, final LeaveCallBack leaveCallBack) {
        ThreadPoolUtils.getInstance().getGlobalThreadPool().execute(new Runnable() {
            @Override
            public void run() {
                BmobQuery<Leave> query = new BmobQuery<Leave>();
                query.setLimit(300);
                query.findObjects(new FindListener<Leave>() {
                    @Override
                    public void done(List<Leave> list, BmobException e) {
                        leaveCallBack.getLeaveLists(list, e);
                    }
                });
            }
        });
    }


    //更新假条处理状态
    public void updateStatus(String objectID, int applicationStatus, final BmobCallBack bmobCallBack){
        Leave leave = new Leave();
        leave.setApplyStatus(applicationStatus);
        leave.update(objectID, new UpdateListener() {
            @Override
            public void done(BmobException e) {
                bmobCallBack.getBmobException(e);
            }
        });

    }
}
