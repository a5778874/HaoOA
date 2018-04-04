package zzh.com.haooa.activity.newsActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.hyphenate.chat.EMClient;

import java.io.Serializable;

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;
import zzh.com.haooa.R;
import zzh.com.haooa.Utils.ThreadPoolUtils;
import zzh.com.haooa.Utils.ToastUtils;
import zzh.com.haooa.bmob.bean.news;

/**
 * Created by ZZH on 2018/3/5.
 */

public class AddNewsActivity extends Activity implements View.OnClickListener {
    private EditText et_newsTitle, et_newsText;
    private Button bt_saveNews, bt_deleteNews, bt_publicNews;
    public static final int STATUS_EDIT_NEWS = 200;  //编辑状态
    public static final int STATUS_ADD_NEWS = 201;   //新建状态
    private int editStatus = STATUS_ADD_NEWS;//编辑新闻的状态，新建状态还是编辑状态。
    private news newsDetails;//传递过来的新闻信息
    private boolean isEdit = true;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addnews);
        initView();
        initData();
    }

    private void initData() {
        Intent it = getIntent();
        editStatus = it.getIntExtra("NewsStatus", STATUS_ADD_NEWS);
        if (editStatus == STATUS_EDIT_NEWS) {
            newsDetails = (news) it.getSerializableExtra("newsDetails");
            et_newsTitle.setText(newsDetails.getNews_title());
            et_newsText.setText(newsDetails.getNews_text());
            Log.d("TAG", "addnewsActivity obj ID: " + newsDetails.getObjectId());
        } else {

        }
    }

    private void initView() {
        et_newsTitle = findViewById(R.id.et_newstitle);
        et_newsText = findViewById(R.id.et_newstext);
        bt_saveNews = findViewById(R.id.bt_save_news);
        bt_saveNews.setOnClickListener(this);
        bt_deleteNews = findViewById(R.id.bt_deletenews);
        bt_deleteNews.setOnClickListener(this);
        bt_publicNews = findViewById(R.id.bt_publishnews);
        bt_publicNews.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_save_news:
                isEdit = !isEdit;
                changeEditable(isEdit);
                break;
            case R.id.bt_deletenews:
                clearEdittext();
                break;
            case R.id.bt_publishnews:
                publicNews();
                break;
        }

    }

    //设置编辑框设否可编辑
    private void changeEditable(boolean isEdit) {
        et_newsText.setEnabled(isEdit);
        et_newsTitle.setEnabled(isEdit);
        if (isEdit) {
            bt_saveNews.setText("保存");
        } else {
            bt_saveNews.setText("编辑");
        }
    }

    //清空输入框
    private void clearEdittext(){
        et_newsTitle.setText("");
        et_newsText.setText("");
    }

    //发布新闻
    private void publicNews() {
        final String title = et_newsTitle.getText().toString();
        final String text = et_newsText.getText().toString();

        if (TextUtils.isEmpty(title) || TextUtils.isEmpty(text)) {
            ToastUtils.showToast(AddNewsActivity.this, "内容不能为空");
        } else {
            //编辑新闻的逻辑
            if (editStatus == STATUS_EDIT_NEWS) {
                ThreadPoolUtils.getInstance().getGlobalThreadPool().execute(new Runnable() {
                    @Override
                    public void run() {
                        String author = EMClient.getInstance().getCurrentUser();
                        //向Bmob服务器发布新闻
                        news myNews = new news();
                        myNews.setNews_title(title);
                        myNews.setNews_text(text);
                        myNews.update(newsDetails.getObjectId(), new UpdateListener() {
                            @Override
                            public void done(BmobException e) {
                                if (e == null) {
                                    ToastUtils.showToast(AddNewsActivity.this, "发布成功");
                                    AddNewsActivity.this.finish();
                                } else {
                                    ToastUtils.showToast(AddNewsActivity.this, "发布失败");
                                }
                            }
                        });

                    }
                });

            }
            //新建新闻的逻辑
            else {
                ThreadPoolUtils.getInstance().getGlobalThreadPool().execute(new Runnable() {
                    @Override
                    public void run() {
                        String author = EMClient.getInstance().getCurrentUser();
                        //向Bmob服务器发布新闻
                        news myNews = new news();
                        myNews.setNews_title(title);
                        myNews.setNews_text(text);
                        myNews.setAuthor(author);
                        myNews.save(new SaveListener<String>() {
                            @Override
                            public void done(String objectId, BmobException e) {
                                if (e == null) {
                                    ToastUtils.showToast(AddNewsActivity.this, "发布成功");
                                    AddNewsActivity.this.finish();
                                } else {
                                    ToastUtils.showToast(AddNewsActivity.this, "发布失败");
                                }
                            }
                        });

                    }
                });
            }
        }

    }
}
