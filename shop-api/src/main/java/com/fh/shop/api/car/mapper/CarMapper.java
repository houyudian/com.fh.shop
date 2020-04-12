package com.fh.shop.api.car.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fh.shop.api.car.param.CarSearchParam;
import com.fh.shop.api.car.po.Car;

import java.util.List;

public interface CarMapper extends BaseMapper<Car> {

    List<Car> findSerchCar(CarSearchParam carSearchParam);

    int count(CarSearchParam carSearchParam);

    void addBatchCar(List<Car> car);

}
