package zzh.com.haooa.net.interfaces;

import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import rx.Observable;
import zzh.com.haooa.net.bean.RespondUserBean;
import zzh.com.haooa.net.bean.TokenBean;
import zzh.com.haooa.net.bean.TokenRequestBodyBean;

/**
 * Created by ZZH on 2018/2/25.
 */

public interface IHxUser {
    /**
     * post请求环信请求用户的token
     *
     * 第一个泛型参数Call<请求成功返回的对象>
     *
     * @Body 表示请求体的对象
     * @Headers 表示请求头信息
     */
    @Headers("Accept:application/json")
    @POST("token")
    //需要导入 io包的Observable ：import io.reactivex.Observable;
    Observable<TokenBean> getToken(@Body TokenRequestBodyBean tokenRequestBodyBean);

    @Headers("Accept:application/json")
    @GET("users?org_name=1108180201178124&&app_name=haooa")
    Observable<RespondUserBean> getServerUsers(@Header("Authorization") String Authorization);
}
