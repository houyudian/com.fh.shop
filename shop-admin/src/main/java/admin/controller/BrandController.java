package admin.controller;

import admin.biz.brand.BrandService;
import admin.common.DataTableResult;
import admin.common.ServerResponse;
import admin.param.BrandParam;
import admin.po.Brand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("brand")
public class BrandController {
    @Autowired
    private BrandService brandService;

    @RequestMapping("toList")
    public String toList() {
        return "/brand/listBrand";
    }

    @RequestMapping("toAdd")
    public String toAdd() {
        return "/brand/addBrand";
    }

    @RequestMapping("findBrand")
    @ResponseBody
    public ServerResponse findBrand() {
        return brandService.findBrand();
    }

    @RequestMapping("addBrand")
    @ResponseBody
    public ServerResponse addBrand(Brand brand) {
        return brandService.addBrand(brand);
    }

    @RequestMapping(value = "findBrandList")
    @ResponseBody
    public DataTableResult findBrandList(BrandParam brandParam) {
        return brandService.findBrandList(brandParam);
    }

    @RequestMapping(value = "updateReconmend", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse updateReconmend(Long brandId, String isReconmend) {
        return brandService.updateReconmend(brandId, isReconmend);
    }
@RequestMapping(value = "deleteBrandCache",method =RequestMethod.GET)
@ResponseBody
    public ServerResponse deleteBrandCache(){

        return brandService.deleteBrandCache();
    }


}
