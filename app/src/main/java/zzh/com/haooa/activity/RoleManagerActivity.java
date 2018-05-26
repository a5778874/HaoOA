package zzh.com.haooa.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.cjj.MaterialRefreshLayout;
import com.cjj.MaterialRefreshListener;
import com.hyphenate.easeui.widget.EaseTitleBar;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import cn.bmob.v3.exception.BmobException;
import zzh.com.haooa.R;
import zzh.com.haooa.Utils.ToastUtils;
import zzh.com.haooa.activity.leave.LeaveRecordActivity;
import zzh.com.haooa.adapter.RoleItemAdapter;
import zzh.com.haooa.bmob.api.UserApi;
import zzh.com.haooa.bmob.api.UserCallBack;
import zzh.com.haooa.bmob.bean.user;
import zzh.com.haooa.dao.UserInfoDAO;

/**
 * Created by ZZH on 2018/5/26.
 */

public class RoleManagerActivity extends Activity {
    private EaseTitleBar easeTitleBar;
    private RecyclerView recyclerView;
    private MaterialRefreshLayout refreshLayout;
    private List<user> roleUserlists=new ArrayList<>();
    private RoleItemAdapter adapter;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_role_manager);
        initView();
    }

    @Override
    protected void onResume() {
        super.onResume();
        initUserDatas();
    }

    private void initUserDatas() {
        new UserApi().getNotAdminUser(new UserCallBack() {
            @Override
            public void getUser(final List<user> list, final BmobException e) {
                if(e==null) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            roleUserlists.clear();
                            roleUserlists.addAll(list);
                            Collections.sort(roleUserlists);
                            adapter=new RoleItemAdapter(RoleManagerActivity.this,roleUserlists);
                            recyclerView.setAdapter(adapter);
                            recyclerView.addItemDecoration(new DividerItemDecoration(RoleManagerActivity.this, DividerItemDecoration.VERTICAL));
                            recyclerView.setLayoutManager(new LinearLayoutManager(RoleManagerActivity.this,LinearLayoutManager.VERTICAL,false));
                        }
                    });


                }else {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            ToastUtils.showToast(RoleManagerActivity.this,"获取用户列表失败"+e.getMessage());
                        }
                    });
                }
            }
        });
    }

    private void initView() {
        easeTitleBar=findViewById(R.id.role_titleBar);
        easeTitleBar.setLeftLayoutClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RoleManagerActivity.this.finish();
            }
        });

        recyclerView=findViewById(R.id.rv_role_item);

        refreshLayout=findViewById(R.id.role_refresh);
        refreshLayout.setMaterialRefreshListener(new MaterialRefreshListener() {
            @Override
            public void onRefresh(MaterialRefreshLayout materialRefreshLayout) {
                initUserDatas();
                refreshLayout.finishRefresh();
            }
        });


    }
}
