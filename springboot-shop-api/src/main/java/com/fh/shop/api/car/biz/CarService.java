package com.fh.shop.api.car.biz;

import com.fh.shop.api.car.param.CarSearchParam;
import com.fh.shop.api.car.po.Car;
import com.fh.shop.api.common.ServerResponse;

import java.util.List;
import java.util.Map;

public interface CarService {

    ServerResponse addCar(Car car);

    ServerResponse deleteCar(Long id);

    ServerResponse updateCar(Car car);

    ServerResponse findById(Long id);

    ServerResponse deleteBatchCar(String ids);

    ServerResponse findSerchCar(CarSearchParam carSearchParam);

    ServerResponse findCarList(CarSearchParam carSearchParam);

    ServerResponse addBatchCar(List<Car> car);

    ServerResponse changeHot(Car car);

    ServerResponse updateStatus(Car car);

    ServerResponse findCarCount();

    ServerResponse groupBySaleTime(String yearMonth);

    ServerResponse statisticSaleCount(Map<String, String> paramMap);

    ServerResponse addItem(Long memberId, Long goodsId, int num);

    ServerResponse find(Long memberId);

    ServerResponse addCarItem(Long id, Long goodsId, int num);

    ServerResponse deleteCart(String goodsids);

}
