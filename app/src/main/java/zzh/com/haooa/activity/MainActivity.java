package zzh.com.haooa.activity;

import android.app.Activity;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTabHost;
import android.os.Bundle;
import android.transition.Explode;
import android.transition.Fade;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TabHost;
import android.widget.TextView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Retrofit;
import rx.Observable;
import zzh.com.haooa.EventBus.LoginEvent;
import zzh.com.haooa.EventBus.RegistEvent;
import zzh.com.haooa.MyApplication;
import zzh.com.haooa.fragment.ContactsFragment;
import zzh.com.haooa.bean.TabSpecBean;
import zzh.com.haooa.fragment.MessageFragment;
import zzh.com.haooa.fragment.MineFragment;
import zzh.com.haooa.R;
import zzh.com.haooa.fragment.WorkFragment;

public class MainActivity extends FragmentActivity {
    private FrameLayout tabContent;
    private FragmentTabHost tabhost;
    //放置fragment选显卡内容的集合
    private List<TabSpecBean> tabSpecLists;
    private  LayoutInflater mInflater;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //增加栈记录
        MyApplication.getInstances().activitiesSets.add(MainActivity.this);
        mInflater=LayoutInflater.from(this);
        initView();
        initData();


    }

    private void initData() {
        //注册EventBus广播，用来接收注册用户信息
        EventBus.getDefault().register(MainActivity.this);
    }

    //接收EventBus信息
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onLoginEvent(LoginEvent event) {
        MineFragment.setUser(event.username,event.head);

    }

    private void initView() {
        tabContent = findViewById(R.id.tabContent);
        tabhost = findViewById(android.R.id.tabhost);
        tabContent = findViewById(R.id.tabContent);
        initTabHost();
    }

    private void initTabHost() {
        tabhost.setup(MainActivity.this,getSupportFragmentManager(),R.id.tabContent);
        tabSpecLists = new ArrayList<>();
        TabSpecBean tab_work = new TabSpecBean("工作", R.drawable.selector_bt_work, WorkFragment.class);
        TabSpecBean tab_message = new TabSpecBean("消息", R.drawable.selector_bt_message, MessageFragment.class);
        TabSpecBean tab_contacts = new TabSpecBean("联系人", R.drawable.selector_bt_contacts, ContactsFragment.class);
        TabSpecBean tab_mine = new TabSpecBean("个人中心", R.drawable.selector_bt_mine, MineFragment.class);
        tabSpecLists.add(tab_work);
        tabSpecLists.add(tab_message);
        tabSpecLists.add(tab_contacts);
        tabSpecLists.add(tab_mine);

        for (TabSpecBean tabSpecBean : tabSpecLists){
            //设置选项卡单条的文字
            TabHost.TabSpec tabSpec = tabhost.newTabSpec(tabSpecBean.getTitle());
            //设置选显卡单条的布局
            tabSpec.setIndicator(buildIndicator(tabSpecBean));
            //添加到选显卡
            tabhost.addTab(tabSpec,tabSpecBean.getFragment(),null);
            //默认显示第一个
            tabhost.setCurrentTab(0);
            //设置没有分割线
            tabhost.getTabWidget().setShowDividers(LinearLayout.SHOW_DIVIDER_NONE);
        }
    }

    //构建Indicator选项卡的View布局
    private View buildIndicator(TabSpecBean bean) {
        View view = mInflater.inflate(R.layout.tab_indicator, null);
        ImageView img = view.findViewById(R.id.im_tabicon);
        TextView text = view.findViewById(R.id.tv_tabtext);
        img.setBackgroundResource(bean.getIcon());
        text.setText(bean.getTitle());
        return view;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //解除注册eventBus广播
        EventBus.getDefault().unregister(MainActivity.this);
        //移除栈记录
        MyApplication.getInstances().activitiesSets.remove(MainActivity.this);
    }
}
