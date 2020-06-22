package com.fh.shop.api.goods.controller;

import com.fh.shop.api.common.ServerResponse;
import com.fh.shop.api.goods.biz.GoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("goods")
public class GoodsController {
    @Autowired
    private GoodsService goodsService;

    @RequestMapping(value = "/findHotList", method = RequestMethod.GET)
    public Object findHotList() {
        ServerResponse hotList = goodsService.findHotList();
        /*MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(hotList);
        mappingJacksonValue.setJsonpFunction(callback);*/
        return hotList;
    }

    @GetMapping("queryGoodsList")
    @ResponseBody
    @Scheduled(cron = "0/300 * * * * ?")
    public ServerResponse queryGoodsList() {
        return goodsService.queryGoodsList();
    }

}
