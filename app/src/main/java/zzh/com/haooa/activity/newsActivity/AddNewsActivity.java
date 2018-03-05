package zzh.com.haooa.activity.newsActivity;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.hyphenate.chat.EMClient;

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;
import zzh.com.haooa.R;
import zzh.com.haooa.Utils.ThreadPoolUtils;
import zzh.com.haooa.Utils.ToastUtils;
import zzh.com.haooa.bmob.bean.news;

/**
 * Created by ZZH on 2018/3/5.
 */

public class AddNewsActivity extends Activity implements View.OnClickListener {
    private EditText et_newsTitle, et_newsText;
    private Button bt_editNews, bt_deleteNews, bt_publicNews;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addnews);
        initView();
    }

    private void initView() {
        et_newsTitle = findViewById(R.id.et_newstitle);
        et_newsText = findViewById(R.id.et_newstext);
        bt_editNews = findViewById(R.id.bt_editnews);
        bt_editNews.setOnClickListener(this);
        bt_deleteNews = findViewById(R.id.bt_deletenews);
        bt_deleteNews.setOnClickListener(this);
        bt_publicNews = findViewById(R.id.bt_publishnews);
        bt_publicNews.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_editnews:

                break;
            case R.id.bt_deletenews:

                break;
            case R.id.bt_publishnews:
                publicNews();
                break;
        }

    }

    //发布新闻
    private void publicNews() {
        final String title = et_newsTitle.getText().toString();
        final String text = et_newsText.getText().toString();
        if (TextUtils.isEmpty(title) || TextUtils.isEmpty(text)) {
            ToastUtils.showToast(AddNewsActivity.this, "内容不能为空");
        } else {
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
