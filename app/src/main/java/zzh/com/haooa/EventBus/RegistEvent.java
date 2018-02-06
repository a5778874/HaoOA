package zzh.com.haooa.EventBus;

/**
 * Created by ZZH on 2018/2/3.
 */

public  class RegistEvent {
    public String username;
    public String password;

    public RegistEvent(String username, String password) {
        this.username = username;
        this.password = password;
    }
}
