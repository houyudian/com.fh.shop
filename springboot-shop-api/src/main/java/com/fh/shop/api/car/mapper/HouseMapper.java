package com.fh.shop.api.car.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fh.shop.api.car.param.HouseParam;
import com.fh.shop.api.car.po.House;

import java.util.List;

public interface HouseMapper extends BaseMapper<House> {

    Long houseCount(HouseParam houseParam);

    List<House> findHouseList(HouseParam houseParam);

}
