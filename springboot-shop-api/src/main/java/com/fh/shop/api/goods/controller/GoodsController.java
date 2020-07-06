package com.fh.shop.api.goods.controller;

import com.fh.shop.api.annotation.Secure;
import com.fh.shop.api.common.ServerResponse;
import com.fh.shop.api.goods.biz.GoodsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/goods")
@Api(tags = "商品接口")
public class GoodsController {
    @Autowired
    private GoodsService goodsService;

    @Secure
@ApiOperation("热销商品")
@ApiImplicitParams({
        @ApiImplicitParam(name = "nonce",value = "唯一随机数",dataType = "string",required =true, paramType = "header"),
        @ApiImplicitParam(name = "time",value = "当前时间",dataType = "string",required =true, paramType = "header"),
        @ApiImplicitParam(name = "sign",value = "签名",dataType = "string",required =true, paramType = "header"),
})
    @RequestMapping(value = "/findHotList", method = RequestMethod.GET)
    public Object findHotList() {
        ServerResponse hotList = goodsService.findHotList();
        /*MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(hotList);
        mappingJacksonValue.setJsonpFunction(callback);*/
        return hotList;
    }

    @GetMapping("queryGoodsList")
    @ResponseBody
 //  @Scheduled(cron = "0 0/5 * * * ?")
    @ApiOperation("定时发送邮件")
    public ServerResponse queryGoodsList() {
        return goodsService.queryGoodsList();
    }

}
