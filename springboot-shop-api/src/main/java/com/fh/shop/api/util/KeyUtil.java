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
}
