package zzh.com.haooa.Main.bean;

/**
 * Created by ZZH on 2018/1/25.
 */


public class TabSpecBean {
    private String title; // fragment选项卡文字
    private int icon; // fragment选项卡图标
    private Class fragment; // 对应fragment

    public TabSpecBean(String title, int icon, Class fragment) {
        this.title = title;
        this.icon = icon;
        this.fragment = fragment;
    }

    public String getTitle() {

        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    public Class getFragment() {
        return fragment;
    }

    public void setFragment(Class fragment) {
        this.fragment = fragment;
    }
}
