package zzh.com.haooa.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hyphenate.easeui.ui.EaseConversationListFragment;

import zzh.com.haooa.R;

/**
 * Created by ZZH on 2018/1/25.
 *  *使用EaseUI提供好的会话消息列表Fragment
 */

public class MessageFragment extends EaseConversationListFragment {
    @Override
    protected void initView() {
        super.initView();
        titleBar.setTitle("消息");
        //query是父类edittext的引用
        query.setHint("搜索");
    }
}
