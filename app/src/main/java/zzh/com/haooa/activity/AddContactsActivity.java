package zzh.com.haooa.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.hyphenate.chat.EMClient;
import com.hyphenate.exceptions.HyphenateException;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;
import zzh.com.haooa.R;
import zzh.com.haooa.Utils.ThreadPoolUtils;
import zzh.com.haooa.Utils.ToastUtils;
import zzh.com.haooa.net.bean.RespondUserBean;
import zzh.com.haooa.net.bean.TokenBean;
import zzh.com.haooa.net.bean.TokenRequestBodyBean;
import zzh.com.haooa.net.interfaces.IHxUser;

/**
 * Created by ZZH on 2018/2/20.
 */

public class AddContactsActivity extends Activity implements View.OnClickListener {
    private EditText et_searchUser, et_addReason;
    private LinearLayout ll_add;
    private ImageView iv_addHead;
    private TextView tv_addName, tv_searchBtn;
    private Button bt_add;

    private Retrofit retrofit;
    private IHxUser iHxUser;
    private TokenRequestBodyBean tokenRequestBodyBean = new TokenRequestBodyBean("client_credentials",
            "YXA6lVwZ0Ab3EeiHedMOWvJIEQ",
            "YXA6eOIg68GRmyy5mzdvr-Mf1PxMNtI");

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addcontacts);
        initView();
        initRetrofit();
    }

    private void initRetrofit() {
        //初始化retrofit
        retrofit = new Retrofit.Builder()
                .baseUrl("http://a1.easemob.com/1108180201178124/haooa/")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
        iHxUser = retrofit.create(IHxUser.class);
    }

    private void initView() {
        et_searchUser = findViewById(R.id.et_searchname);
        tv_searchBtn = findViewById(R.id.tv_add_find);
        tv_searchBtn.setOnClickListener(this);
        et_addReason = findViewById(R.id.et_add_reason);
        iv_addHead = findViewById(R.id.iv_add_photo);
        tv_addName = findViewById(R.id.tv_add_name);
        ll_add = findViewById(R.id.ll_add);
        bt_add = findViewById(R.id.bt_add_add);
        bt_add.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_add_add:
                String addReason = et_addReason.getText().toString();

                if (TextUtils.isEmpty(addReason))
                    addReason = "很高兴认识你，加个好友吧！";

                addUser(tv_addName.getText().toString(), addReason);
                break;
            case R.id.tv_add_find:
                String user = et_searchUser.getText().toString();
                if (!TextUtils.isEmpty(user))
                    findUser(user);
                break;
        }
    }

    //查找联系人
    private void findUser(final String user) {
        //1.请求网络获取token，flatMap是把一个Observable对象转为另一个Observable对象
        iHxUser.getToken(tokenRequestBodyBean)
                //2.带着token继续请求网络获取注册用户列表信息,token值是"Bearer " token
                .flatMap(new Func1<TokenBean, Observable<RespondUserBean>>() {
                    @Override
                    public Observable<RespondUserBean> call(TokenBean tokenBean) {
                        return iHxUser.getServerUsers("Bearer " + tokenBean.getAccess_token());
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                //3.把获得的响应信息去掉不用的数据取出用户列表集合
                .map(new Func1<RespondUserBean, List<RespondUserBean.EntitiesBean>>() {
                    @Override
                    public List<RespondUserBean.EntitiesBean> call(RespondUserBean respondUserBean) {
                        return respondUserBean.getEntities();
                    }
                })
                //4.数据操作
                .subscribe(new Observer<List<RespondUserBean.EntitiesBean>>() {
                    @Override
                    public void onCompleted() {
                        Log.d("TAG", "onCompleted: ");
                    }

                    @Override
                    public void onError(Throwable throwable) {
                        Log.d("TAG", "onError: " + throwable.getMessage());
                    }

                    @Override
                    public void onNext(List<RespondUserBean.EntitiesBean> entitiesBeans) {
                        //得到包含所有用户名的集合
                        List<String> usernameList = new ArrayList<>();
                        for (RespondUserBean.EntitiesBean user : entitiesBeans)
                            usernameList.add(user.getUsername());
                        //判断该用户是否存在
                        if (usernameList.contains(user)) {
                            tv_addName.setText(user);
                            ll_add.setVisibility(View.VISIBLE);
                        } else {
                            ToastUtils.showToast(AddContactsActivity.this, "用户不存在");
                            ll_add.setVisibility(View.GONE);
                        }

                    }
                });

    }


    //添加联系人
    private void addUser(final String user, final String addReason) {

        ThreadPoolUtils.getInstance().getGlobalThreadPool().execute(new Runnable() {
            @Override
            public void run() {
                try {
                    //向环信服务器发送好友邀请
                    EMClient.getInstance().contactManager().addContact(user, addReason);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            ToastUtils.showToast(AddContactsActivity.this, "发送添加好友邀请成功");
                        }
                    });
                } catch (final HyphenateException e) {
                    e.printStackTrace();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            ToastUtils.showToast(AddContactsActivity.this, "发送添加好友邀请失败" + e.toString());
                        }
                    });
                }

            }
        });

    }
}
