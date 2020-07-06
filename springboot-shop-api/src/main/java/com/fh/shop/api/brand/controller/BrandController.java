package com.fh.shop.api.brand.controller;

import com.fh.shop.api.brand.biz.BrandService;
import com.fh.shop.api.common.ServerResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/brand")
@Api(tags = "品牌接口")
public class BrandController {
    @Autowired
    private BrandService brandService;


    @ApiOperation("品牌")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "nonce",value = "唯一随机数",dataType = "string",required =true, paramType = "header"),
            @ApiImplicitParam(name = "time",value = "当前时间",dataType = "string",required =true, paramType = "header"),
            @ApiImplicitParam(name = "sign",value = "签名",dataType = "string",required =true, paramType = "header"),
    })
    @RequestMapping(value = "findReconmendBrand", method = RequestMethod.GET)
    public ServerResponse findReconmendBrand() {
        return brandService.findReconmendBrand();
    }

    @GetMapping("findAllBrand")
    @ApiOperation("品牌展示")
    public ServerResponse findAllBrand() {
        return brandService.findAllBrand();
    }

}
