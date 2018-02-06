package zzh.com.haooa.fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import de.hdodenhof.circleimageview.CircleImageView;
import zzh.com.haooa.EventBus.LoginEvent;
import zzh.com.haooa.Utils.ThreadPoolUtils;
import zzh.com.haooa.Utils.ToastUtils;
import zzh.com.haooa.activity.LoginActivity;
import zzh.com.haooa.R;
import zzh.com.haooa.bean.UserAccountTableBean;
import zzh.com.haooa.dao.UserAccountDAO;

/**
 * Created by Administrator on 2018/1/25.
 */

public class MineFragment extends Fragment implements View.OnClickListener {
    private static CircleImageView civ_userhead;
    private static TextView mine_name;
    private Button bt_exitLogin;
    private View view = null;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_mine, null);
        initView(view);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        //若没登录则禁止点击退出登录按钮
        bt_exitLogin.setClickable(EMClient.getInstance().isLoggedInBefore());
    }

    @Override
    public void onResume() {
        super.onResume();
        //显示用户
        showUserAccount();
    }

    private void initView(View view) {
        if (view == null) {
            return;
        }
        mine_name = view.findViewById(R.id.mine_name);
        civ_userhead = view.findViewById(R.id.civ_userhead);
        civ_userhead.setOnClickListener(MineFragment.this);
        bt_exitLogin = view.findViewById(R.id.mine_exit_login);
        bt_exitLogin.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.civ_userhead:
                final Intent it = new Intent(getActivity(), LoginActivity.class);
                //如果之前登陆过，提示切换用户，并退出之前登录的帐号
                if (EMClient.getInstance().isLoggedInBefore()) {
                    new AlertDialog.Builder(getContext()).setTitle("切换帐号提醒")
                            .setMessage("你之前已经登陆过，切换帐号会自动退出登录，需要切换帐号吗？")
                            .setPositiveButton("确定切换", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    EMClient.getInstance().logout(false, new EMCallBack() {
                                        @Override
                                        public void onSuccess() {
                                            startActivity(it);
                                        }

                                        @Override
                                        public void onError(int i, String s) {
                                            ToastUtils.showToast(getContext(), "切换失败");
                                            return;
                                        }

                                        @Override
                                        public void onProgress(int i, String s) {

                                        }
                                    });
                                }
                            }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    }).show();
                } else {
                    startActivity(it);
                }

                break;
            case R.id.mine_exit_login:
                exitLogin();
                break;
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }


    //设置登录用户或未登录用户的信息
    private void showUserAccount() {
        if (EMClient.getInstance().isLoggedInBefore()) {
            mine_name.setText(EMClient.getInstance().getCurrentUser());
            civ_userhead.setImageResource(R.mipmap.defaultloginheader);
        } else {
            mine_name.setText("请登录");
            civ_userhead.setImageResource(R.mipmap.man_header);
        }
    }

    //给其他Activity调用，设置登录后显示用户头像和名称
    public static void setUser(String username, String head) {
        //设置头像
        if (head == null) {
            civ_userhead.setImageResource(R.mipmap.defaultloginheader);
        }
        //设置登录名
        mine_name.setText(username);
    }

    public void exitLogin() {
        ThreadPoolUtils.getInstance().getGlobalThreadPool().execute(new Runnable() {
            @Override
            public void run() {

                //退出登录环信
                EMClient.getInstance().logout(false, new EMCallBack() {
                    @Override
                    public void onSuccess() {
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                //保存退出信息到本地
                                UserAccountTableBean userAccountTableBean = new UserAccountTableBean();
                                userAccountTableBean.setHxUsername(mine_name.getText().toString());
                                userAccountTableBean.setIsOnline(false);
                                UserAccountDAO.init().addUser(userAccountTableBean);
                                // 更新ui显示
                                civ_userhead.setImageResource(R.mipmap.man_header);
                                mine_name.setText("请登录");
                                //退出后不能点击退出按钮
                                bt_exitLogin.setClickable(false);
                                ToastUtils.showToast(getActivity(), "退出成功");
                            }
                        });
                    }

                    @Override
                    public void onError(int i, String s) {

                    }

                    @Override
                    public void onProgress(int i, String s) {

                    }
                });
            }
        });
    }

}
