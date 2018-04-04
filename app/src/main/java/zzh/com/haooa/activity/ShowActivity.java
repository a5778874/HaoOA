package zzh.com.haooa.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hyphenate.chat.EMClient;
import com.superrtc.call.ThreadUtils;

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.UpdateListener;
import zzh.com.haooa.R;
import zzh.com.haooa.Utils.ThreadPoolUtils;
import zzh.com.haooa.Utils.ToastUtils;
import zzh.com.haooa.activity.newsActivity.AddNewsActivity;
import zzh.com.haooa.bmob.bean.news;

/**
 * Created by Administrator on 2018/4/3.
 * 显示详细内容的界面
 */

public class ShowActivity extends Activity implements View.OnClickListener {
    private TextView tv_title, tv_text, tv_time, tv_author;
    private ImageView iv_delete, iv_edit;
    private RelativeLayout rl_edit;
    private news newsDetails;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show);
        initView();
        initData();
    }

    private void initView() {
        tv_title = findViewById(R.id.details_title);
        tv_time = findViewById(R.id.details_time);
        tv_text = findViewById(R.id.details_content);
        tv_author = findViewById(R.id.details_author);
        iv_delete = findViewById(R.id.details_delete);
        iv_delete.setOnClickListener(this);
        iv_edit = findViewById(R.id.details_edit);
        iv_edit.setOnClickListener(this);
        rl_edit = findViewById(R.id.rl_edit);

    }

    private void initData() {
        //获取传递过来的新闻内容
        Intent it = getIntent();
        newsDetails = (news) it.getSerializableExtra("news");
        Log.d("TAG", "showactivity obj ID: " + newsDetails.getObjectId());
        String author = newsDetails.getAuthor();
        //如果文章作者是当前登录的用户，则有删除和编辑权限
        if (EMClient.getInstance().getCurrentUser().equals(author)) {
            rl_edit.setVisibility(View.VISIBLE);
        }
        tv_author.setText(author);
        tv_title.setText(newsDetails.getNews_title());
        tv_text.setText(newsDetails.getNews_text());
        tv_time.setText(newsDetails.getCreatedAt());


    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.details_delete:
                //删除文章
                ThreadPoolUtils.getInstance().getGlobalThreadPool().execute(new Runnable() {
                    @Override
                    public void run() {
                        news myNews = new news();


                        myNews.setObjectId(newsDetails.getObjectId());
                        myNews.delete(new UpdateListener() {
                            @Override
                            public void done(BmobException e) {
                                if (e == null) {
                                    ToastUtils.showToast(ShowActivity.this, "删除成功");
                                    ShowActivity.this.finish();
                                } else {
                                    ToastUtils.showToast(ShowActivity.this, "删除失败");
                                }
                            }
                        });
                    }
                });

                break;
            case R.id.details_edit:
                //跳转到编辑页面
                Intent it = new Intent(ShowActivity.this, AddNewsActivity.class);
                it.putExtra("NewsStatus", AddNewsActivity.STATUS_EDIT_NEWS);
                it.putExtra("newsDetails", newsDetails);
                startActivity(it);
                ShowActivity.this.finish();
                break;
        }
    }
}
