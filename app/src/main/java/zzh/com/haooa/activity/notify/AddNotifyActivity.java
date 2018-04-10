package zzh.com.haooa.activity.notify;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.hyphenate.chat.EMClient;

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;
import zzh.com.haooa.R;
import zzh.com.haooa.Utils.ThreadPoolUtils;
import zzh.com.haooa.Utils.ToastUtils;
import zzh.com.haooa.activity.newsActivity.AddNewsActivity;
import zzh.com.haooa.bmob.bean.Notify;
import zzh.com.haooa.bmob.bean.news;

/**
 * Created by Administrator on 2018/4/9.
 */

public class AddNotifyActivity extends Activity implements View.OnClickListener {
    private EditText et_notify_title, et_notify_text;
    private Button bt_save_notify, bt_public_notify, bt_clear_notify;
    private boolean isEdit = true;

    public static final int STATUS_EDIT_NOTIFY = 300;  //编辑状态
    public static final int STATUS_ADD_NOTIFY = 301;   //新建状态
    private int editStatus = STATUS_ADD_NOTIFY;//编辑新闻的状态，新建状态还是编辑状态。

    private Notify NotifyDetails;//传递过来的新闻信息

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addnotify);
        initView();
        initData();
    }

    private void initData() {
        //判断是新建还是编辑状态
        Intent it = getIntent();
        editStatus = it.getIntExtra("NotifyStatus", STATUS_ADD_NOTIFY);
        if (editStatus == STATUS_EDIT_NOTIFY) {
            NotifyDetails = (Notify) it.getSerializableExtra("NotifyDetails");
            et_notify_title.setText(NotifyDetails.getNotify_title());
            et_notify_text.setText(NotifyDetails.getNotify_text());
            Log.d("TAG", "addNotifyActivity obj ID: " + NotifyDetails.getObjectId());
        } else {

        }
    }

    private void initView() {
        et_notify_title = findViewById(R.id.et_addnotifytitle);
        et_notify_text = findViewById(R.id.et_addnotifytext);
        bt_save_notify = findViewById(R.id.bt_save_notify);
        bt_public_notify = findViewById(R.id.bt_clear_notify);
        bt_clear_notify = findViewById(R.id.bt_publishnotify);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_save_notify:
                isEdit = !isEdit;
                changeEditable(isEdit);
                break;
            case R.id.bt_clear_notify:
                clearEdittext();
                break;
            case R.id.bt_publishnotify:
                publicNotify();
                break;
        }
    }

    //发布公告
    private void publicNotify() {
        final String title = et_notify_title.getText().toString();
        final String text = et_notify_text.getText().toString();

        if (TextUtils.isEmpty(title) || TextUtils.isEmpty(text)) {
            ToastUtils.showToast(AddNotifyActivity.this, "内容不能为空");
        } else {
            //编辑新闻的逻辑
            if (editStatus == STATUS_EDIT_NOTIFY) {
                ThreadPoolUtils.getInstance().getGlobalThreadPool().execute(new Runnable() {
                    @Override
                    public void run() {
                        String author = EMClient.getInstance().getCurrentUser();
                        //向Bmob服务器发布新闻
                        Notify notify = new Notify();
                        notify.setNotify_title(title);
                        notify.setNotify_text(text);
                        notify.update(NotifyDetails.getObjectId(), new UpdateListener() {
                            @Override
                            public void done(BmobException e) {
                                if (e == null) {
                                    ToastUtils.showToast(AddNotifyActivity.this, "发布成功");
                                    AddNotifyActivity.this.finish();
                                } else {
                                    ToastUtils.showToast(AddNotifyActivity.this, "发布失败");
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
                                    ToastUtils.showToast(AddNotifyActivity.this, "发布成功");
                                    AddNotifyActivity.this.finish();
                                } else {
                                    ToastUtils.showToast(AddNotifyActivity.this, "发布失败");
                                }
                            }
                        });

                    }
                });
            }
        }

    }

    //设置编辑框设否可编辑
    private void changeEditable(boolean isEdit) {
        et_notify_title.setEnabled(isEdit);
        et_notify_text.setEnabled(isEdit);
        if (isEdit) {
            bt_save_notify.setText("保存");
        } else {
            bt_save_notify.setText("编辑");
        }
    }

    //清空输入框
    private void clearEdittext() {
        et_notify_title.setText("");
        et_notify_text.setText("");
    }
}
