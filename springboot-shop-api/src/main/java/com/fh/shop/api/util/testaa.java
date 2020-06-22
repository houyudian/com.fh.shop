package com.fh.shop.api.util;

import org.junit.Test;

public class testaa {
    private static final String APP_SECRET = "huahua";

    @Test
    public void test() {
        //bff30e0666a89626dacae7c0dee28638
        String huahua = MD5Util.sign("1591797433213-279" + "1591797433213", APP_SECRET);
        System.out.println(huahua);
    }

}
