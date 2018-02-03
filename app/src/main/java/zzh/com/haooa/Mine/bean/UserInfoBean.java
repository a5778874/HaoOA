package zzh.com.haooa.Mine.bean;

/**
 * Created by ZZH on 2018/2/3.
 */

public class UserInfoBean {
    private String username;// 用户名
    private String hxid;// 环信id
    private String nick;// 用户的昵称
    private String photo;// 头像

    public UserInfoBean() {
    }

    public UserInfoBean(String username) {
        this.username = username;
        this.hxid = username;
        this.nick = username;
    }

    public String getUsername() {
        return username;
    }

    public void setName(String username) {
        this.username = username;
    }

    public String getHxid() {
        return hxid;
    }

    public void setHxid(String hxid) {
        this.hxid = hxid;
    }

    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    @Override
    public String toString() {
        return "UserInfoBean{" +
                "username='" + username + '\'' +
                ", hxid='" + hxid + '\'' +
                ", nick='" + nick + '\'' +
                ", photo='" + photo + '\'' +
                '}';
    }
}
