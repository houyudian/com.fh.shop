package com.fh.shop.api.car.biz;

import com.fh.shop.api.common.DataTableResult;
import com.fh.shop.api.common.ServerResponse;
import com.fh.shop.api.car.param.HouseParam;
import com.fh.shop.api.car.po.House;

public interface HouseService {
    DataTableResult findHouse(HouseParam houseParam);

    ServerResponse addHouse(House house);

    ServerResponse deleteHouse(Long id);

    ServerResponse getHouseId(Long id);

    ServerResponse updateHouse(House house);

    ServerResponse findHouseList(HouseParam houseParam);

}
