package zzh.com.haooa.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import de.hdodenhof.circleimageview.CircleImageView;
import zzh.com.haooa.activity.LoginActivity;
import zzh.com.haooa.R;

/**
 * Created by Administrator on 2018/1/25.
 */

public class MineFragment extends Fragment implements View.OnClickListener{
    private CircleImageView civ_userhead;
    private View view =null;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_mine,null);
        initView(view);
        return view;
    }

    private void initView(View view) {
        if (view==null){
            return;
        }
        civ_userhead=view.findViewById(R.id.civ_userhead);
        civ_userhead.setOnClickListener(MineFragment.this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.civ_userhead:
                startActivity(new Intent(getActivity(), LoginActivity.class));
                break;
        }
    }
}
