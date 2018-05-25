package zzh.com.haooa.bmob.api;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import zzh.com.haooa.Utils.ThreadPoolUtils;
import zzh.com.haooa.bmob.bean.user;

/**
 * Created by ZZH on 2018/4/6.
 */

public class UserApi {
    //查询当前用户的资料
    public void getUserInfo(final String hxUsername, final UserCallBack userCallBack) {
        ThreadPoolUtils.getInstance().getGlobalThreadPool().execute(new Runnable() {
            @Override
            public void run() {
                BmobQuery<user> query = new BmobQuery<user>();
                query.addWhereEqualTo("hxUsername", hxUsername);
                query.setLimit(300);
                query.findObjects(new FindListener<user>() {
                    @Override
                    public void done(List<user> list, BmobException e) {
                        userCallBack.getUser(list, e);
                    }
                });
            }
        });
    }
}
