package com.fh.shop.api.car.controller;

import com.fh.shop.api.car.biz.AreaService;
import com.fh.shop.api.common.ServerResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("area")
@RestController
@CrossOrigin("*")
public class AreaController {
    @Autowired
    private AreaService areaService;

    @GetMapping
    public ServerResponse findAreaList(Long id) {
        return areaService.findAreaList(id);
    }

    @GetMapping("areaTree")
    public ServerResponse areaTree() {
        return areaService.findAreaTree();
    }

}
