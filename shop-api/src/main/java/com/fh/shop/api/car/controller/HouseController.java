package com.fh.shop.api.car.controller;

import com.fh.shop.api.common.DataTableResult;
import com.fh.shop.api.common.ServerResponse;
import com.fh.shop.api.car.biz.HouseService;
import com.fh.shop.api.car.param.HouseParam;
import com.fh.shop.api.car.po.House;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin("*")
@RequestMapping("houses")
public class HouseController {
    @Autowired
    private HouseService houseService;

    @RequestMapping("findHouse")
    public DataTableResult findHouse(HouseParam houseParam) {
        return houseService.findHouse(houseParam);
    }

    @RequestMapping(value = "house/page", method = RequestMethod.GET)
    public ServerResponse findHouseList(HouseParam houseParam) {
        return houseService.findHouseList(houseParam);
    }

    @RequestMapping("addHouse")
    public ServerResponse addHouse(House house) {
        return houseService.addHouse(house);
    }

    @RequestMapping("deleteHouse")
    public ServerResponse deleteHouse(Long id) {
        return houseService.deleteHouse(id);
    }

    @RequestMapping("getHouseId")
    public ServerResponse getHouseId(Long id) {
        return houseService.getHouseId(id);
    }

    @RequestMapping("updateHouse")
    public ServerResponse updateHouse(House house) {
        return houseService.updateHouse(house);
    }
}
