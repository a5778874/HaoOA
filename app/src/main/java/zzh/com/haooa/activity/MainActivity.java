package zzh.com.haooa.activity;

import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTabHost;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TabHost;
import android.widget.TextView;

import com.hyphenate.chat.EMClient;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.exception.BmobException;
import zzh.com.haooa.EventBus.LoginEvent;
import zzh.com.haooa.MyApplication;
import zzh.com.haooa.Utils.ToastUtils;
import zzh.com.haooa.bean.UserInfoBean;
import zzh.com.haooa.bmob.bean.user;
import zzh.com.haooa.bmob.dao.BmobStringCallBack;
import zzh.com.haooa.bmob.dao.DepartmentDAO;
import zzh.com.haooa.bmob.dao.UserCallBack;
import zzh.com.haooa.bmob.dao.UserDAO;
import zzh.com.haooa.dao.UserAccountDAO;
import zzh.com.haooa.dao.UserInfoDAO;
import zzh.com.haooa.fragment.ContactsFragment;
import zzh.com.haooa.bean.TabSpecBean;
import zzh.com.haooa.fragment.MessageFragment;
import zzh.com.haooa.fragment.MineFragment;
import zzh.com.haooa.R;
import zzh.com.haooa.fragment.WorkFragment;
import zzh.com.haooa.greenDao.UserInfoBeanDao;

public class MainActivity extends FragmentActivity {
    private FrameLayout tabContent;
    private FragmentTabHost tabhost;
    //放置fragment选显卡内容的集合
    private List<TabSpecBean> tabSpecLists;
    private LayoutInflater mInflater;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //增加栈记录
        MyApplication.getInstances().activitiesSets.add(MainActivity.this);
        mInflater = LayoutInflater.from(this);
        initView();
        initData();
    }

    @Override
    protected void onStart() {
        super.onStart();

    }

    private void initData() {
        //注册EventBus广播，用来接收注册用户信息
        EventBus.getDefault().register(MainActivity.this);
        //初始化用户信息
        initUserInfo();
    }


    //接收EventBus信息
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onLoginEvent(LoginEvent event) {
        MineFragment.setUser(event.username, event.head);
        //初始化用户信息
        initUserInfo();
    }


    private void initView() {
        tabContent = findViewById(R.id.tabContent);
        tabhost = findViewById(android.R.id.tabhost);
        tabContent = findViewById(R.id.tabContent);
        initTabHost();
    }

    private void initTabHost() {
        tabhost.setup(MainActivity.this, getSupportFragmentManager(), R.id.tabContent);
        tabSpecLists = new ArrayList<>();
        TabSpecBean tab_work = new TabSpecBean("工作", R.drawable.selector_bt_work, WorkFragment.class);
        TabSpecBean tab_message = new TabSpecBean("消息", R.drawable.selector_bt_message, MessageFragment.class);
        TabSpecBean tab_contacts = new TabSpecBean("联系人", R.drawable.selector_bt_contacts, ContactsFragment.class);
        TabSpecBean tab_mine = new TabSpecBean("个人中心", R.drawable.selector_bt_mine, MineFragment.class);
        tabSpecLists.add(tab_work);
        tabSpecLists.add(tab_message);
        tabSpecLists.add(tab_contacts);
        tabSpecLists.add(tab_mine);

        for (TabSpecBean tabSpecBean : tabSpecLists) {
            //设置选项卡单条的文字
            TabHost.TabSpec tabSpec = tabhost.newTabSpec(tabSpecBean.getTitle());
            //设置选显卡单条的布局
            tabSpec.setIndicator(buildIndicator(tabSpecBean));
            //添加到选显卡
            tabhost.addTab(tabSpec, tabSpecBean.getFragment(), null);
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

    private void initUserInfo() {
        //初始化用户资料
        final String hxUsername = EMClient.getInstance().getCurrentUser();
        Log.d("TAG", "MainActivity getUser: " + hxUsername);
        //获取服务器资料
        new UserDAO().getUserInfo(hxUsername, new UserCallBack() {
            @Override
            public void getUser(List<user> list, BmobException e) {
                if (e == null) {
                    //保存到本地数据库
                    user myUser = list.get(0);
                    Log.d("TAG", "MainActivity getUser: " + myUser.getDepartmentID());
                    final UserInfoBean userInfoBean = new UserInfoBean();
                    userInfoBean.setHxUsername(myUser.getHxUsername());
                    userInfoBean.setDepartmentID(myUser.getDepartmentID());
                    userInfoBean.setSex(myUser.getSex());
                    userInfoBean.setNick(myUser.getNick());
                    userInfoBean.setAddress(myUser.getAddress());
                    userInfoBean.setPhone(myUser.getPhone());
                    userInfoBean.setMail(myUser.getMail());

                    //查询上一次的登录的用户信息
                    List<UserInfoBean> user = UserInfoDAO.init().getUser();
                    String localUser = "";
                    //判断是第一次登录
                    if (user.size() > 0)
                        localUser = user.get(0).getHxUsername();

                    //判断是否替换新用户的信息，保证本地数据库只保留一条当前用户的数据
                    if (!localUser.equals(hxUsername)) {
                        UserInfoDAO.init().deleteAll();
                        Log.d("TAG", "userInfoBean: " + userInfoBean.toString());
                        UserInfoDAO.init().addUser(userInfoBean);
                        //从服务器获取部门id对应的名字并保存
                        new DepartmentDAO().getDepartmentByID(myUser.getDepartmentID(), new BmobStringCallBack() {
                            @Override
                            public void getName(String name, BmobException e) {
                                if (e == null) {
                                    userInfoBean.setDepartmentName(name);
                                    UserInfoDAO.init().updateUser(userInfoBean);
                                }
                            }
                        });
                    }


                } else {
                    ToastUtils.showToast(MainActivity.this, "初始化用户信息失败,请检查网络是否连接");
                    Log.d("TAG", "MainActivity getUser: " + e.getLocalizedMessage());
                }
            }
        });
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
