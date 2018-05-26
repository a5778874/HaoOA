package zzh.com.haooa.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.exception.BmobException;
import zzh.com.haooa.R;
import zzh.com.haooa.Utils.Constant;
import zzh.com.haooa.Utils.ToastUtils;
import zzh.com.haooa.bean.DepartmentBean;
import zzh.com.haooa.bmob.api.BmobCallBack;
import zzh.com.haooa.bmob.api.UserApi;
import zzh.com.haooa.bmob.bean.Leave;
import zzh.com.haooa.bmob.bean.user;
import zzh.com.haooa.dao.DepartmentDao;
import zzh.com.haooa.dao.UserInfoDAO;

/**
 * Created by ZZH on 2018/5/26.
 */

public class RoleItemAdapter extends RecyclerView.Adapter<RoleItemAdapter.MyHolder> {
    private List<user> userList;
    private Context mContext;
    private List<String> departmentNameList = new ArrayList<>();
    List<DepartmentBean> validDepartmentList;
    ArrayAdapter<String> spinnerAdapter;

    public RoleItemAdapter(Context mContext, List<user> userList) {
        this.userList = userList;
        this.mContext = mContext;
        //获取部门信息并把名字存进集合里
        validDepartmentList = DepartmentDao.init().getValidDepartment();
        for (DepartmentBean bean : validDepartmentList) {
            departmentNameList.add(bean.getDepartmentName());
        }

        //初始化spinner适配器
        spinnerAdapter = new ArrayAdapter<String>(mContext, android.R.layout.simple_spinner_item, departmentNameList);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
    }


    @Override
    public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.item_role, null);
        return new MyHolder(v);
    }

    @Override
    public void onBindViewHolder(final MyHolder holder, final int position) {
        final String objID = userList.get(position).getObjectId();
        final String username = userList.get(position).getHxUsername();
        String currentDepartmentId = userList.get(position).getDepartmentID();
        String currentDepartmentName = DepartmentDao.init().getDepartmentInfoByID(currentDepartmentId).getDepartmentName();

        holder.spinner.setAdapter(spinnerAdapter);
        holder.spinner.setSelection(departmentNameList.indexOf(currentDepartmentName)); //默认选择原来部门
        holder.tv_roleName.setText(username);
        holder.tv_roleDepartment.setText(currentDepartmentName);

        holder.btn_sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //把分配的新部门信息发送到服务器

                final String selectDepartmentName = holder.spinner.getSelectedItem().toString();
                String selectedDepartmentId = DepartmentDao.init().getIdByDepartmentName(selectDepartmentName);

                new UserApi().updateDepartmentID(objID, selectedDepartmentId, new BmobCallBack() {
                    @Override
                    public void getBmobException(final BmobException e) {
                        if (e == null) {
                            ((Activity) mContext).runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    holder.tv_roleDepartment.setText(selectDepartmentName);
                                    ToastUtils.showToast(mContext, "操作成功，已将" + username + "分配到" + selectDepartmentName);
                                }
                            });
                        } else {
                            ((Activity) mContext).runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    ToastUtils.showToast(mContext, "分配部门失败" + e.getLocalizedMessage());
                                }
                            });
                        }
                    }
                });

            }
        });


    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    public class MyHolder extends RecyclerView.ViewHolder {
        private TextView tv_roleName, tv_roleDepartment;
        private Spinner spinner;
        private TextView btn_sure;

        public MyHolder(View itemView) {
            super(itemView);
            tv_roleName = itemView.findViewById(R.id.tv_rolename);
            tv_roleDepartment = itemView.findViewById(R.id.tv_role_department);
            spinner = itemView.findViewById(R.id.role_spinner);
            btn_sure = itemView.findViewById(R.id.btn_role_sure);
        }
    }
}
