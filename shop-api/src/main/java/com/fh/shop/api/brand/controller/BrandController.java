package com.fh.shop.api.brand.controller;

import com.fh.shop.api.brand.biz.BrandService;
import com.fh.shop.api.common.ServerResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("brand")
@CrossOrigin()
public class BrandController {
    @Autowired
    private BrandService brandService;
    @RequestMapping(value = "findReconmendBrand",method = RequestMethod.GET)
    public ServerResponse findReconmendBrand(){
        return brandService.findReconmendBrand();
    }
    @GetMapping("findAllBrand")
    public ServerResponse findAllBrand(){
        return brandService.findAllBrand();
    }


}
