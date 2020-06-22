package com.fh.shop.api.car.biz;

import com.fh.shop.api.common.DataTableResult;
import com.fh.shop.api.common.ServerResponse;
import com.fh.shop.api.car.mapper.HouseMapper;
import com.fh.shop.api.car.param.HouseParam;
import com.fh.shop.api.car.po.House;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class HouseServiceImpl implements HouseService {
    @Autowired
    private HouseMapper houseMapper;

    @Override
    public DataTableResult findHouse(HouseParam houseParam) {
        Long count = houseMapper.houseCount(houseParam);

        List<House> houses = houseMapper.findHouseList(houseParam);
        // return new DataTableResult(houseParam.getDraw(),count,count,houses);
        return null;
    }

    @Override
    public ServerResponse addHouse(House house) {
        houseMapper.insert(house);
        return ServerResponse.success();
    }

    @Override
    public ServerResponse deleteHouse(Long id) {
        houseMapper.deleteById(id);
        return ServerResponse.success();
    }

    @Override
    public ServerResponse getHouseId(Long id) {
        House selectById = houseMapper.selectById(id);
        return ServerResponse.success(selectById);
    }

    @Override
    public ServerResponse updateHouse(House house) {
        houseMapper.updateById(house);
        return ServerResponse.success();
    }

    @Override
    public ServerResponse findHouseList(HouseParam houseParam) {
        Long aLong = houseMapper.houseCount(houseParam);
        List<House> houseList = houseMapper.findHouseList(houseParam);
        Map result = new HashMap<>();

        result.put("totalSize", aLong);
        result.put("carList", houseList);
        return ServerResponse.success(result);
    }
}
