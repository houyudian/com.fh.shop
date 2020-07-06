package com.fh.shop.api.car.controller;

import com.fh.shop.api.car.biz.HouseService;
import com.fh.shop.api.car.param.HouseParam;
import com.fh.shop.api.car.po.House;
import com.fh.shop.api.common.ServerResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/houses")
@Api(tags = "房屋接口")
public class HouseController {
    @Autowired
    private HouseService houseService;


    @RequestMapping(value = "house/page", method = RequestMethod.GET)
    @ApiOperation("查询房屋信息")
    public ServerResponse findHouseList(HouseParam houseParam) {
        return houseService.findHouseList(houseParam);
    }

    @RequestMapping("addHouse")
    @ApiOperation("增加房屋信息")
    public ServerResponse addHouse(House house) {
        return houseService.addHouse(house);
    }

    @RequestMapping("deleteHouse")
    @ApiOperation("删除房屋信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id",value = "需要删除的id",required = true,dataType = "long",paramType = "path")
    })
    public ServerResponse deleteHouse(Long id) {
        return houseService.deleteHouse(id);
    }

    @RequestMapping("getHouseId")
    @ApiOperation("根据id获取房屋信息")
    public ServerResponse getHouseId(Long id) {
        return houseService.getHouseId(id);
    }

    @RequestMapping("updateHouse")
    @ApiOperation("修改房屋信息")
    public ServerResponse updateHouse(House house) {
        return houseService.updateHouse(house);
    }
}
