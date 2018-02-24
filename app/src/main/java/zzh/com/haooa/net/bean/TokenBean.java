package zzh.com.haooa.net.bean;

/**
 * Created by ZZH on 2018/2/20.
 */

//请求token的响应体
public class TokenBean {

    /**
     * 请求url：http://a1.easemob.com/1108180201178124/haooa/token
     *
     * 请求头 header： Accept: application/json
     *
     * 响应体格式 respond body：
     * access_token : YWMtMaUBqBYgEeiqvAUnaCbXZgAAAAAAAAAAAAAAAAAAAAGVXBnQBvcR6Id50w5a8kgRAgMAAAFhsomT9gBPGgAz8QsqmaAGpThrWfMcMfTPuJrcNAVBG_qD0IqxmFNmoQ
     * expires_in : 5184000
     * application : 955c19d0-06f7-11e8-8779-d30e5af24811
     */

    private String access_token;
    private int expires_in;
    private String application;

    public String getAccess_token() {
        return access_token;
    }

    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }

    public int getExpires_in() {
        return expires_in;
    }

    public void setExpires_in(int expires_in) {
        this.expires_in = expires_in;
    }

    public String getApplication() {
        return application;
    }

    public void setApplication(String application) {
        this.application = application;
    }
}
