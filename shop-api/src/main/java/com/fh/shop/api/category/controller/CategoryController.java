package com.fh.shop.api.category.controller;

import com.fh.shop.api.annotation.Secure;
import com.fh.shop.api.category.biz.ICategoryService;
import com.fh.shop.api.common.ServerResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@RequestMapping("category")
@Controller
@CrossOrigin(maxAge = 36000)
public class CategoryController {

    @Autowired
    private ICategoryService categoryService;

    @ResponseBody
    @RequestMapping("findAllCategory")
    @Secure
    public ServerResponse findAllCategory() {
        return categoryService.findAllCateList();
    }
}
