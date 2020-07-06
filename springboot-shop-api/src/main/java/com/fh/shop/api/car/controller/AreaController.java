package com.fh.shop.api.car.controller;

import com.fh.shop.api.car.biz.AreaService;
import com.fh.shop.api.common.ServerResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api/area")
@RestController
@Api(tags = "地区接口")
public class AreaController {
    @Autowired
    private AreaService areaService;

    @GetMapping
    @ApiOperation("地区展示")
    public ServerResponse findAreaList(Long id) {
        return areaService.findAreaList(id);
    }

    @GetMapping("areaTree")
    @ApiOperation("地区数展示")
    public ServerResponse areaTree() {
        return areaService.findAreaTree();
    }

}
