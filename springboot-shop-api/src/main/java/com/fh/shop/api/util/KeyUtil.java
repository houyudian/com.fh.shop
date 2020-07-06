package com.fh.shop.api.util;

public class KeyUtil {

    public static final int MEMBER_EXPIRE = 30 * 60;

    public static String buildMemberKey(Long memberId, String uuid) {
        return "member:" + memberId + ":" + uuid;
    }

    public static String buildSecureKey(String nonce) {
        return "secure:" + nonce;
    }

    public static String buildCarKey(Long memberId) {
        return "cart:" + memberId;
    }
    public static String buildOrderStockLessKey(Long memberId) {
        return "order:stock:less:" + memberId;
    }
    public static String buildOrderKey(Long memberId) {
        return "order:" + memberId;
    }
    public static String buildOrderErrorKey(Long memberId) {
        return "order:error" + memberId;
    }
    public static String buildPayLogKey(Long memberId) {
        return "paylog:" + memberId;
    }
}
