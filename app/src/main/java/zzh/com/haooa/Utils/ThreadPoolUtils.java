package zzh.com.haooa.Utils;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by ZZH on 2018/2/3.
 */

public class ThreadPoolUtils {
    // 创建对象
    private static ThreadPoolUtils threadPoolUtils = new ThreadPoolUtils();
    private ExecutorService executors = Executors.newCachedThreadPool();

    // 私有化构造
    private ThreadPoolUtils() {

    }

    // 获取单例对象
    public static ThreadPoolUtils getInstance(){
        return threadPoolUtils;
    }

    // 获取全局线程池对象
    public ExecutorService getGlobalThreadPool(){
        return executors;
    }

}
