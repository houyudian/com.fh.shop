package com.fh.shop.api.category.controller;

import com.fh.shop.api.category.biz.ICategoryService;
import com.fh.shop.api.common.ServerResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@RequestMapping("category")
@Controller
public class CategoryController {

    @Autowired
    private ICategoryService categoryService;

    @ResponseBody
    @RequestMapping("findAllCategory")
    public ServerResponse findAllCategory() {
        return categoryService.findAllCateList();
    }
}
