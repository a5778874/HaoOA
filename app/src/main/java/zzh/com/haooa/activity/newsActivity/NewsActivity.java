package zzh.com.haooa.activity.newsActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.hyphenate.easeui.widget.EaseTitleBar;

import zzh.com.haooa.R;

/**
 * Created by ZZH on 2018/3/5.
 */

public class NewsActivity extends Activity {
    private EaseTitleBar newsTitleBar;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);
        initView();
    }

    private void initView() {
        newsTitleBar=findViewById(R.id.titlebar_news);
        newsTitleBar.setRightLayoutClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //跳转到新建新闻页面
                startActivity(new Intent(NewsActivity.this,AddNewsActivity.class));
            }
        });
    }
}
