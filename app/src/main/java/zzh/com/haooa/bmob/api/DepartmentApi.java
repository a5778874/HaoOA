package zzh.com.haooa.bmob.api;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import zzh.com.haooa.Utils.ThreadPoolUtils;
import zzh.com.haooa.bmob.bean.Department;

/**
 * Created by ZZH on 2018/4/6.
 */

public class DepartmentApi {
    //查询全部部门
    public void getDepartmentList(final DepartmentCallBack departmentCallBack) {
        ThreadPoolUtils.getInstance().getGlobalThreadPool().execute(new Runnable() {
            @Override
            public void run() {
                BmobQuery<Department> query = new BmobQuery<Department>();
                query.setLimit(300);
                query.findObjects(new FindListener<Department>() {
                    @Override
                    public void done(List<Department> list, BmobException e) {
                        departmentCallBack.getDepartmentList(list, e);
                    }
                });
            }
        });
    }

    //根据ID查询部门
    public void getDepartmentByID(final String departmentID, final BmobStringCallBack bmobStringCallBack) {
        ThreadPoolUtils.getInstance().getGlobalThreadPool().execute(new Runnable() {
            @Override
            public void run() {
                BmobQuery<Department> query = new BmobQuery<Department>();
                query.setLimit(300);
                query.addWhereEqualTo("departmentID", departmentID);
                query.findObjects(new FindListener<Department>() {
                    @Override
                    public void done(List<Department> list, BmobException e) {
                        String name = null;
                        if (e == null) {
                            name = list.get(0).getDepartmentName();
                        }
                        bmobStringCallBack.getName(name, e);
                    }
                });
            }
        });
    }

}
