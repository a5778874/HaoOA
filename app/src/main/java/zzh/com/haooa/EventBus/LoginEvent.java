package zzh.com.haooa.EventBus;

/**
 * Created by ZZH on 2018/2/3.
 */

public  class LoginEvent {
    public String username;
    public String head;


    public LoginEvent(String username) {
        this.username = username;
        this.head = null;
    }


    public LoginEvent(String username, String head) {
        this.username = username;
        this.head = head;
    }
}
