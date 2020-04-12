package com.fh.shop.api.car.controller;

import com.fh.shop.api.car.biz.CarService;
import com.fh.shop.api.car.param.CarSearchParam;
import com.fh.shop.api.car.po.Car;
import com.fh.shop.api.common.ServerResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("*")
public class CarController {
    @Autowired
    private CarService carService;

    @RequestMapping("hh")
    public void hhh(){
        System.out.println("测试aa");
    }


    @PutMapping("/cars/status")
    public ServerResponse updateStatus(@RequestBody Car car){
      return   carService.updateStatus(car);
    }
    @PutMapping("cars/hot")
    public ServerResponse changeHot(@RequestBody Car car){
      return   carService.changeHot(car);
    }



    @RequestMapping(value = "cars/page", method = RequestMethod.GET)
    public ServerResponse findCarList(CarSearchParam carSearchParam) {
        return carService.findCarList(carSearchParam);
    }

    @PostMapping("cars/addBatch")
    public ServerResponse addBatchCar(@RequestBody List<Car> car) {
        return carService.addBatchCar(car);
    }

    @RequestMapping(value = "cars", method = RequestMethod.GET)
    public ServerResponse findSerchCar(CarSearchParam carSearchParam) {
        return carService.findSerchCar(carSearchParam);
    }

    @RequestMapping(value = "cars/info", method = RequestMethod.POST)
    public ServerResponse addCar(@RequestBody Car car) {
        return carService.addCar(car);
    }

    @RequestMapping(value = "cars", method = RequestMethod.POST)
    public ServerResponse addCarInfo(Car car) {
        return carService.addCar(car);
    }

    @RequestMapping(value = "cars/{id}", method = RequestMethod.DELETE)
    public ServerResponse deleteCar(@PathVariable("id") Long id) {
        return carService.deleteCar(id);
    }

    @RequestMapping(value = "cars/{id}", method = RequestMethod.GET)
    public ServerResponse findById(@PathVariable("id") Long id) {
        return carService.findById(id);
    }

    @RequestMapping(value = "cars", method = RequestMethod.PUT)
    public ServerResponse updateCar(@RequestBody Car car) {
        return carService.updateCar(car);
    }

    @RequestMapping(value = "cars", method = RequestMethod.DELETE)
    public ServerResponse deleteBatchCar(String ids) {
        return carService.deleteBatchCar(ids);
    }

}
