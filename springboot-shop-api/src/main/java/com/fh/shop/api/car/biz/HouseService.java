package com.fh.shop.api.car.biz;

import com.fh.shop.api.car.param.HouseParam;
import com.fh.shop.api.car.po.House;
import com.fh.shop.api.common.ServerResponse;

public interface HouseService {


    ServerResponse addHouse(House house);

    ServerResponse deleteHouse(Long id);

    ServerResponse getHouseId(Long id);

    ServerResponse updateHouse(House house);

    ServerResponse findHouseList(HouseParam houseParam);

}
