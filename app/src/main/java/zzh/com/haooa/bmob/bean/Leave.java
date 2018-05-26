package zzh.com.haooa.bmob.bean;

import android.support.annotation.NonNull;
import android.util.Log;

import java.text.ParseException;

import cn.bmob.v3.BmobObject;
import zzh.com.haooa.Utils.DateUtils;

/**
 * Created by Administrator on 2018/5/23.
 */

public class Leave extends BmobObject implements Comparable<Leave>{
    private String userID;
    private String startTime;
    private String endTime;
    private int applyStatus;   //0审核中  -1 不通过    1通过
    private String leaveReason;
    private String img;

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }


    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getLeaveReason() {
        return leaveReason;
    }

    public void setLeaveReason(String leaveReason) {
        this.leaveReason = leaveReason;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public int getApplyStatus() {
        return applyStatus;
    }

    public void setApplyStatus(int applyStatus) {
        this.applyStatus = applyStatus;
    }


    //让集合按数据的最新创建顺序在前排序
    @Override
    public int compareTo(@NonNull Leave o) {
        int i=0;
        try {
            i= (int) (DateUtils.DateTomillisecond(o.getCreatedAt())-DateUtils.DateTomillisecond(this.getCreatedAt()));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return i;
    }
}
