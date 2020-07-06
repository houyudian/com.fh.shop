package com.fh.shop.api.util;

import java.math.BigDecimal;

public class BigDecimalUtil {

    public static BigDecimal mult(String s1, String s2) {

        BigDecimal b = new BigDecimal(s1);
        BigDecimal b1 = new BigDecimal(s2);
        return b.multiply(b1);

    }
    public static BigDecimal mul(String s1, String s2) {

        BigDecimal b = new BigDecimal(s1);
        BigDecimal b1 = new BigDecimal(s2);
        return b.multiply(b1).setScale(2);

    }

    public static BigDecimal add(String s1, String s2) {
        BigDecimal b = new BigDecimal(s1);
        BigDecimal b1 = new BigDecimal(s2);

        return b.add(b1);
    }

}
