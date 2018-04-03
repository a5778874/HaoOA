package zzh.com.haooa.bmob.bean;

import java.io.Serializable;

import cn.bmob.v3.BmobObject;

/**
 * Created by ZZH on 2018/3/5.
 */

public class news extends BmobObject implements Serializable{
    private String news_title;
    private String news_text;
    private String author;


    public String getNews_title() {
        return news_title;
    }

    public void setNews_title(String news_title) {
        this.news_title = news_title;
    }

    public String getNews_text() {
        return news_text;
    }

    public void setNews_text(String news_text) {
        this.news_text = news_text;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }
}
