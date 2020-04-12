package com.fh.shop.api.goods.controller;

import com.fh.shop.api.common.ServerResponse;
import com.fh.shop.api.goods.biz.GoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("goods")
public class GoodsController {
    @Autowired
    private GoodsService goodsService;
    @ResponseBody
    @RequestMapping(value = "/findHotList",method = RequestMethod.GET)
    public Object findHotList(String callback){
        ServerResponse hotList = goodsService.findHotList();
        MappingJacksonValue mappingJacksonValue=new MappingJacksonValue(hotList);
        mappingJacksonValue.setJsonpFunction(callback);
        return mappingJacksonValue;
    }
}
