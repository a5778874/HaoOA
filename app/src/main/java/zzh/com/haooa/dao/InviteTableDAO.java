package zzh.com.haooa.dao;

import java.util.List;

import zzh.com.haooa.MyApplication;
import zzh.com.haooa.bean.InviteTableBean;
import zzh.com.haooa.bean.UserAccountTableBean;
import zzh.com.haooa.greenDao.InviteTableBeanDao;

/**
 * Created by ZZH on 2018/2/25.
 */

public class InviteTableDAO {
    private static InviteTableDAO inviteTableDAO = new InviteTableDAO();

    private InviteTableDAO() {

    }

    public static InviteTableDAO init() {
        return inviteTableDAO;
    }

    /**
     * 保存邀请信息
     *
     * @param inviteTableBean
     * @return 返回所在行id
     */
    public long addInvite(InviteTableBean inviteTableBean) {
        return MyApplication.getInstances().getDaoSession().getInviteTableBeanDao().insertOrReplace(inviteTableBean);
    }


    /**
     * 查询邀请信息
     */
    public List<InviteTableBean> getInvite() {
        List<InviteTableBean> lists = MyApplication.getInstances().getDaoSession().getInviteTableBeanDao().loadAll();
        return lists;
    }

    /**
     * 更新邀请信息
     *
     * @param inviteTableBean
     */
    public void updateInvite(InviteTableBean inviteTableBean) {
        MyApplication.getInstances().getDaoSession().getInviteTableBeanDao().update(inviteTableBean);
    }

    /**
     * 根据键删除邀请信息
     *
     * @param hxusername
     * @return
     */
    public boolean deleteInvite(String hxusername) {
        InviteTableBeanDao inviteTableBeanDao = MyApplication.getInstances().getDaoSession().getInviteTableBeanDao();
        //查询是否存在
        InviteTableBean bean = inviteTableBeanDao.queryBuilder().where(InviteTableBeanDao.Properties.User_hxUsername.eq(hxusername)).build().unique();
        if (bean == null) {
            return false;
        }
        else {
            //若用户存在则删除
            inviteTableBeanDao.deleteByKey(hxusername);
            return true;
        }
    }


}

