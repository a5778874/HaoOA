package zzh.com.haooa.net.bean;

/**
 * Created by ZZH on 2018/2/20.
 */

public class TokenRequestBodyBean {

    /**
     * 请求体的格式
     * grant_type : client_credentials
     * client_id : YXA6lVwZ0Ab3EeiHedMOWvJIEQ
     * client_secret : YXA6eOIg68GRmyy5mzdvr-Mf1PxMNtI
     */

    private String grant_type;
    private String client_id;
    private String client_secret;

    public TokenRequestBodyBean(String grant_type, String client_id, String client_secret) {
        this.grant_type = grant_type;
        this.client_id = client_id;
        this.client_secret = client_secret;
    }

    public String getGrant_type() {
        return grant_type;
    }

    public void setGrant_type(String grant_type) {
        this.grant_type = grant_type;
    }

    public String getClient_id() {
        return client_id;
    }

    public void setClient_id(String client_id) {
        this.client_id = client_id;
    }

    public String getClient_secret() {
        return client_secret;
    }

    public void setClient_secret(String client_secret) {
        this.client_secret = client_secret;
    }
}
