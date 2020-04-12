package com.fh.shop.api.car.biz;

import com.fh.shop.api.car.mapper.CarMapper;
import com.fh.shop.api.car.param.CarSearchParam;
import com.fh.shop.api.car.po.Car;
import com.fh.shop.api.common.ResponseEnum;
import com.fh.shop.api.common.ServerResponse;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class CarServiceImpl implements CarService {
    @Autowired
    private CarMapper carMapper;

    @Override
    public ServerResponse addCar(Car car) {
        car.setStatus("0");
        car.setIsHot("0");
        carMapper.insert(car);
        return ServerResponse.success();
    }

    @Override
    public ServerResponse deleteCar(Long id) {
        carMapper.deleteById(id);
        return ServerResponse.success();
    }

    @Override
    public ServerResponse updateCar(Car car) {
        carMapper.updateById(car);
        return ServerResponse.success();
    }

    @Override
    public ServerResponse findById(Long id) {
        Car car = carMapper.selectById(id);
        return ServerResponse.success(car);
    }

    @Override
    public ServerResponse deleteBatchCar(String ids) {

        if (StringUtils.isEmpty(ids)) {
            return ServerResponse.error(ResponseEnum.CAR_IDS_IS_null);
        }
        List<Long> idList = Arrays.stream(ids.split(",")).map(x -> Long.parseLong(x)).collect(Collectors.toList());
        carMapper.deleteBatchIds(idList);
        return ServerResponse.success();

    }

    @Override
    public ServerResponse findSerchCar(CarSearchParam carSearchParam) {

        String carName = carSearchParam.getCarName();
        if (StringUtils.isNotEmpty(carName)) {
            try {
                String carNameInfo = new String(carName.getBytes("iso-8859-1"), "utf-8");
                carSearchParam.setCarName(carNameInfo);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
                throw new RuntimeException(e);
            }
        }
        List<Car> cars = carMapper.selectList(null);
        return ServerResponse.success(cars);
    }

    @Override
    public ServerResponse findCarList(CarSearchParam carSearchParam) {

       /* String carName = carSearchParam.getCarName();
        if (StringUtils.isNotEmpty(carName)) {
            try {
                String s = new String(carName.getBytes("iso-8859-1"),"utf-8");

                carSearchParam.setCarName(s);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
*/
        int totalSize = carMapper.count(carSearchParam);
        List<Car> carList = carMapper.findSerchCar(carSearchParam);
        Map result = new HashMap<>();

        result.put("totalSize", totalSize);
        result.put("carList", carList);
        return ServerResponse.success(result);
    }

    @Override
    public ServerResponse addBatchCar(List<Car> car) {
        if (car.size() > 0) {
            carMapper.addBatchCar(car);
        }
        return ServerResponse.success();
    }

    @Override

    public ServerResponse changeHot(Car car) {
        carMapper.updateById(car);
        return ServerResponse.success();
    }

    @Override
    public ServerResponse updateStatus(Car car) {
        carMapper.updateById(car);
        return ServerResponse.success();
    }

}
