package zzh.com.haooa.Utils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Administrator on 2018/5/23.
 */

public class DateUtils {
    public static String millisecondToDate(Long millisecond) {
        Date date = new Date(millisecond);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        String format = simpleDateFormat.format(date);
        return format;
    }
}
