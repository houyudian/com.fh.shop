package com.fh.shop.api.common;

public class SystemConstant {
    public static final String GOODS_IS_DOWN = "0";
    public static final int GOODS_STATUS = 1;
    public static final int GOODS_ISHOT = 1;
    public static final String CATE_LIST_KEY = "cate_";
    public static final int CATE_EXPIRE = 10 * 60;

    public static final int HOT_EXPIRE = 30 * 60;
    public static final String HOT_GOODS_LIST = "hotGoodsList_";
    public static final String UPLOAD_IMG_PATH = "/uploadImage/";

    public static final String RECOMMEND_BRAND = "1";
    public static final String BRAND_KEY = "brand_";
    public static final int BRAND_EXPIRE = 30 * 60;
    public static final int RECOMMEND_EXPIRE = 10 * 60;

    public static final String APPSECRET = "hou";
    public static final String CURRENT_MEMBER = "currentMember";

    public interface OrderStatus {
        int WAIT_PAY = 0;
        int PAY_SUCCESS = 1;
        int SEND_GOODS = 2;
        int TRADE_SUCESS = 3;
        int TRADE_CLOSE = 4;
        int COMMENT_OVER = 5;

    }
    public interface PayStatus {
        int WAIT_PAY = 0;
        int PAY_SUCCESS = 1;

    }

}
