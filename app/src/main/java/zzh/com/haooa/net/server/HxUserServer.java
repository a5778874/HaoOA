package zzh.com.haooa.net.server;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;
import zzh.com.haooa.net.bean.RespondUserBean;
import zzh.com.haooa.net.bean.TokenBean;
import zzh.com.haooa.net.bean.TokenRequestBodyBean;
import zzh.com.haooa.net.interfaces.IHxUser;

/**
 * Created by ZZH on 2018/2/25.
 */

public class HxUserServer {
    List<RespondUserBean.EntitiesBean> userLists = new ArrayList<>();
    private Retrofit retrofit;
    private IHxUser iHxUser;
    private TokenRequestBodyBean tokenRequestBodyBean = new TokenRequestBodyBean("client_credentials",
            "YXA6lVwZ0Ab3EeiHedMOWvJIEQ",
            "YXA6eOIg68GRmyy5mzdvr-Mf1PxMNtI");

    private static HxUserServer server=new HxUserServer();

    public static HxUserServer getInstance(){
        return server;
    }

    private HxUserServer() {
        //初始化retrofit
        retrofit = new Retrofit.Builder()
                .baseUrl("http://a1.easemob.com/1108180201178124/haooa/")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
        iHxUser = retrofit.create(IHxUser.class);
    }

    public List<RespondUserBean.EntitiesBean> getRegistHxUsers() {

        //1.请求网络获取token，flatMap是把一个Observable对象转为另一个Observable对象
        iHxUser.getToken(tokenRequestBodyBean)
                //2.带着token继续请求网络获取注册用户列表信息,token值是"Bearer " token
                .flatMap(new Func1<TokenBean, Observable<RespondUserBean>>() {
                    @Override
                    public Observable<RespondUserBean> call(TokenBean tokenBean) {
                        return iHxUser.getServerUsers("Bearer " + tokenBean.getAccess_token());
                    }
                })
               // .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                //3.把获得的响应信息去掉不用的数据取出用户列表集合
                .map(new Func1<RespondUserBean, List<RespondUserBean.EntitiesBean>>() {
                    @Override
                    public List<RespondUserBean.EntitiesBean> call(RespondUserBean respondUserBean) {
                        return respondUserBean.getEntities();
                    }
                })
                //4.数据操作
                .subscribe(new Action1<List<RespondUserBean.EntitiesBean>>() {
                    @Override
                    public void call(List<RespondUserBean.EntitiesBean> entitiesBeans) {
                        userLists.clear();
                        Log.d("TAG", "call: "+entitiesBeans.size());
                        userLists.addAll(entitiesBeans);
                        Log.d("TAG", "call userLists1: "+userLists.size());
                    }
                });
        Log.d("TAG", "call userLists2: "+userLists.size());
        return userLists;
    }
}
