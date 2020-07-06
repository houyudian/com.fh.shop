package com.fh.shop.api.category.controller;

import com.fh.shop.api.annotation.Secure;
import com.fh.shop.api.category.biz.ICategoryService;
import com.fh.shop.api.common.ServerResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@RequestMapping("/api/category")
@Controller
@Api(tags = "分类接口")
public class CategoryController {

    @Autowired
    private ICategoryService categoryService;

    @RequestMapping("findAllCategory")
    @Secure
    @ResponseBody
    @ApiOperation("分类")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "nonce",value = "唯一随机数",dataType = "string",required =true, paramType = "header"),
            @ApiImplicitParam(name = "time",value = "当前时间",dataType = "string",required =true, paramType = "header"),
            @ApiImplicitParam(name = "sign",value = "签名",dataType = "string",required =true, paramType = "header"),
    })
    public ServerResponse findAllCategory() {
        return categoryService.findAllCateList();
    }
}
