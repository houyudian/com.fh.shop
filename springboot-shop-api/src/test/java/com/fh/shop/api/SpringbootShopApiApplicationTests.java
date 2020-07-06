package com.fh.shop.api;

import com.fh.shop.api.brand.po.Brand;
import com.fh.shop.api.goods.po.Goods;
import com.fh.shop.api.rabbitmq.HelloSender;
import com.github.wxpay.sdk.MyConfig;
import com.github.wxpay.sdk.WXPay;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashMap;
import java.util.Map;

@SpringBootTest
//@RunWith(SpringJUnit4ClassRunner.class)
public class SpringbootShopApiApplicationTests {
    @Autowired
    private HelloSender helloSender;

    @Test
    void contextLoads() {
        for (int i = 1; i < 10; i++) {
            Goods g = new Goods();
            g.setId(Long.parseLong(i + ""));
            g.setPrices("300" + i);
            g.setStock(10L);
            helloSender.sendGoodsMessage(g);
        }

    }
    @Test
   public void test(){
        Brand brand = new Brand();
       brand.setId(2L);
       brand.setBrandName("vivo");;
        helloSender.sendBrandMessage(brand);
    }


  @Test
    public void  test4() throws Exception {
        MyConfig config = new MyConfig();
        WXPay wxpay = new WXPay(config);

        Map<String, String> data = new HashMap<String, String>();
        data.put("body", "腾讯充值中心-QQ会员充值");
        data.put("out_trade_no", "2016090910595900000012");
        data.put("device_info", "");
        data.put("fee_type", "CNY");
        data.put("total_fee", "1");
        data.put("notify_url", "http://www.example.com/wxpay/notify");
        data.put("trade_type", "NATIVE");  // 此处指定为扫码支付
        data.put("product_id", "12");

        try {
            Map<String, String> resp = wxpay.unifiedOrder(data);
            System.out.println(resp);
        } catch (Exception e) {
            e.printStackTrace();
        }
   }

}
