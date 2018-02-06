package zzh.com.haooa;

import android.app.Activity;
import android.app.Application;
import android.database.sqlite.SQLiteDatabase;

import com.hyphenate.chat.EMOptions;
import com.hyphenate.easeui.EaseUI;

import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import cn.bmob.v3.Bmob;
import zzh.com.haooa.greenDao.DaoMaster;
import zzh.com.haooa.greenDao.DaoSession;

/**
 * Created by ZZH on 2018/2/1.
 */

public class MyApplication extends Application {
    //用于记录哪个Activity处于栈中
    public Set<Activity> activitiesSets=new LinkedHashSet<>();

    private DaoMaster.DevOpenHelper mHelper;
    private SQLiteDatabase db;
    private DaoMaster mDaoMaster;
    private DaoSession mDaoSession;
    public static MyApplication instances;

    @Override
    public void onCreate() {
        super.onCreate();
        instances=this;
        //初始化环信Ease UI
        EMOptions options = new EMOptions();
        // 默认添加好友时，是不需要验证的。false为需要验证
        options.setAcceptInvitationAlways(false);
        EaseUI.getInstance().init(this, options);
        //初始化GreenDAO
        initGreenDAO();
        //初始化bmob云服务
        Bmob.initialize(this,"b747e8fc58883e66ad8038f4df24126f");

    }


    public static MyApplication getInstances() {
        return instances;
    }

    private void initGreenDAO() {
        // 通过 DaoMaster 的内部类 DevOpenHelper，你可以得到一个便利的 SQLiteOpenHelper 对象。
        // 可能你已经注意到了，你并不需要去编写「CREATE TABLE」这样的 SQL 语句，因为 greenDAO 已经帮你做了。
        // 注意：默认的 DaoMaster.DevOpenHelper 会在数据库升级时，删除所有的表，意味着这将导致数据的丢失。
        // 所以，在正式的项目中，你还应该做一层封装，来实现数据库的安全升级。
        mHelper = new DaoMaster.DevOpenHelper(this, "HaoOA-db", null);
        db = mHelper.getWritableDatabase();
        // 注意：该数据库连接属于 DaoMaster，所以多个 Session 指的是相同的数据库连接。
        mDaoMaster = new DaoMaster(db);
        mDaoSession = mDaoMaster.newSession();
    }

    public DaoSession getDaoSession() {
        return mDaoSession;
    }

    public SQLiteDatabase getDb() {
        return db;
    }



}
