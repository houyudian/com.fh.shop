package com.fh.shop.api.task;

import com.fh.shop.api.goods.biz.GoodsService;
import com.fh.shop.api.goods.po.Goods;
import com.fh.shop.api.util.MailUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Component
public class Stocktask {
    @Autowired
    private MailUtil mailUtil;

    @Resource
    private GoodsService goodsService;

   @Scheduled(cron = "0 0 1 * * ?")
    public void checkStock() {
        System.out.println("-====" + new Date());

        List<Goods> goods = goodsService.stockLess();
        StringBuffer result = new StringBuffer();
        String tableHtml = "<table>\n" +
                "    <thead border=\"1px\"cellspacing=\"0px\" bgcolor=\"red\">\n" +
                "    <tr>\n" +
                "    <th>名称</th>\n" +
                "    <th>价格</th>\n" +
                "    <th>库存</th>\n" +
                "    </tr>\n" +
                "    </thead>\n" +
                "    <tbody>\n";
        result.append(tableHtml);
        goods.forEach(x -> {
            result.append("<tr>\n" +
                    "    <th>" + x.getProductName() + "</th>\n" +
                    "    <th>" + x.getPrice() + "</th>\n" +
                    "    <th>" + x.getStock() + "</th>\n" +
                    "    </tr>");
        });
        result.append("</tbody></table>");
     //  mailUtil.sendMail("389168944@qq.com", "库存不足提示(哈喽我来看你了)", result.toString());
    }

}
