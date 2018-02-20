package zzh.com.haooa.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hyphenate.easeui.ui.EaseContactListFragment;

import zzh.com.haooa.R;
import zzh.com.haooa.activity.AddContactsActivity;

/**
 * Created by ZZH on 2018/1/25.
 * 使用EaseUI提供好的联系人列表Fragment
 */

public class ContactsFragment extends EaseContactListFragment {
    //初始化EaseUI的控件
    @Override
    protected void initView() {
        super.initView();
        //添加好友按钮
        titleBar.setRightImageResource(R.mipmap.em_add);
        titleBar.setTitle("联系人");
        //扩展EaseUI的fragment布局
        View v=View.inflate(getContext(),R.layout.fragment_contacts,null);
        listView.addHeaderView(v);
        //query是父类edittext的引用
        query.setHint("搜索");
    }

    //重写此方法用于处理逻辑业务
    @Override
    protected void setUpView() {
        super.setUpView();
        //环信EaseUI标题栏右侧按钮的点击事件
        titleBar.setRightLayoutClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it=new Intent(getActivity(), AddContactsActivity.class);
                startActivity(it);
            }
        });
    }

}
