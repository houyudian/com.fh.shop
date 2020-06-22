package admin.util;

import org.apache.commons.lang3.StringUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateTime {

    public static final String  Y_M_D="yyyy-MM-dd";
    public static final String  FULL_TIME="yyyy-MM-dd HH:mm:ss";


    public static String date2str(Date date,String dateType){
        if (date==null){
            return "";
        }
        SimpleDateFormat sim=new SimpleDateFormat(dateType);
        return sim.format(date);
    }
    public static Date str2date(String str,String dateType){
        if (StringUtils.isEmpty(str)){
            throw new RuntimeException("日期格式的字符串为空！");
        }
        SimpleDateFormat sim=new SimpleDateFormat(dateType);
        Date parse = null;
        try {
            parse = sim.parse(str);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return parse;
    }
}
