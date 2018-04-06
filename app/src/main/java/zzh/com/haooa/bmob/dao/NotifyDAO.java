package zzh.com.haooa.bmob.dao;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import zzh.com.haooa.Utils.ThreadPoolUtils;
import zzh.com.haooa.bmob.bean.Notify;

/**
 * Created by ZZH on 2018/4/6.
 */

public class NotifyDAO {
    //查询全部通知
    public void getNotifyList(final NotifyCallBack notifyCallBack){
        ThreadPoolUtils.getInstance().getGlobalThreadPool().execute(new Runnable() {
            @Override
            public void run() {
                BmobQuery<Notify> query = new BmobQuery<Notify>();
                query.setLimit(300);
                query.findObjects(new FindListener<Notify>() {
                    @Override
                    public void done(List<Notify> list, BmobException e) {
                        notifyCallBack.getNotifyList(list,e);
                    }
                });
            }
        });
    }

    //根据部门ID查询自己部门通知
    public void getDepartmentNotifyList(final String departmentID, final NotifyCallBack notifyCallBack){
        ThreadPoolUtils.getInstance().getGlobalThreadPool().execute(new Runnable() {
            @Override
            public void run() {
                BmobQuery<Notify> eq1 = new BmobQuery<Notify>();
                eq1.addWhereEqualTo("departmentID", "0");
                BmobQuery<Notify> eq2 = new BmobQuery<Notify>();
                eq2.addWhereEqualTo("departmentID", departmentID);
                List<BmobQuery<Notify>> queries = new ArrayList<BmobQuery<Notify>>();
                queries.add(eq1);
                queries.add(eq2);
                BmobQuery<Notify> mainQuery = new BmobQuery<Notify>();
                mainQuery.or(queries);
                mainQuery.findObjects(new FindListener<Notify>() {
                    @Override
                    public void done(List<Notify> list, BmobException e) {
                        notifyCallBack.getNotifyList(list,e);
                    }
                });

            }
        });
    }
}
