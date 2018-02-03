package zzh.com.haooa.Utils;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by ZZH on 2018/2/3.
 */

public class ToastUtils {
    public static void showToast(Context context,String text){
        Toast.makeText(context,text,Toast.LENGTH_SHORT).show();
    }
}
