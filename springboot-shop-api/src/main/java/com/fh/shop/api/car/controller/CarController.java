package com.fh.shop.api.car.controller;

import com.fh.shop.api.car.biz.CarService;
import com.fh.shop.api.car.param.CarSearchParam;
import com.fh.shop.api.car.po.Car;
import com.fh.shop.api.common.ServerResponse;
import com.fh.shop.api.common.SystemConstant;
import com.fh.shop.api.member.vo.MemberVo;
import com.fh.shop.api.util.Check;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;
import java.util.Random;

@RestController
@RequestMapping("/api")
@Api(tags = "购物车接口")
public class CarController {

    public static void main(String[] args) {
        int leftLimit = 2;
        int rightLimit = 11;

        Random random = new Random();
        int range = rightLimit - leftLimit;

        Runnable r = () -> {
            int generatedInteger = leftLimit + (int) (random.nextFloat() * range);
            System.out.println(generatedInteger);
        };
        for (int i = 1; i < 10; i++) {
            new Thread(r).start();
        }
    }

    @Autowired
    private CarService carService;

    @PostMapping("addCar")
    @ApiOperation("增加购物车")
    public ServerResponse addCarItem(HttpServletRequest request, Long goodsId, int num) {
        MemberVo memberVo = (MemberVo) request.getAttribute(SystemConstant.CURRENT_MEMBER);
        Long id = memberVo.getId();
        return carService.addCarItem(id, goodsId, num);

    }

    @PostMapping("addItem")
    @Check
    @ApiOperation("增加购物车啊")
    @ApiImplicitParams({
            @ApiImplicitParam(name="x-auth",value="登录后的头信息",required = true,dataType = "String",paramType = "header"),
    })
    public ServerResponse addItem(HttpServletRequest request, Long goodsId, int num) {
        MemberVo memberVo = (MemberVo) request.getAttribute(SystemConstant.CURRENT_MEMBER);
        Long memberId = memberVo.getId();
        return carService.addItem(memberId, goodsId, num);
    }

    @GetMapping("find")
    @Check
    @ApiOperation("购物车查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name="x-auth",value="登录后的头信息",required = true,dataType = "String",paramType = "header")})
    public ServerResponse find(HttpServletRequest request) {
        MemberVo memberVo = (MemberVo) request.getAttribute(SystemConstant.CURRENT_MEMBER);
        Long memberId = memberVo.getId();
        return carService.find(memberId);
    }

    @PutMapping("/cars/status")
    @ApiOperation("改变状态")
    public ServerResponse updateStatus(@RequestBody Car car) {
        return carService.updateStatus(car);
    }

    @PutMapping("cars/hot")
    public ServerResponse changeHot(@RequestBody Car car) {
        return carService.changeHot(car);
    }

    @RequestMapping(value = "cars/page", method = RequestMethod.GET)
    @ApiOperation("分页展示")
    public ServerResponse findCarList(CarSearchParam carSearchParam) {
        return carService.findCarList(carSearchParam);
    }

    @PostMapping("cars/addBatch")
    @ApiOperation("批量添加车")
    public ServerResponse addBatchCar(@RequestBody List<Car> car) {
        return carService.addBatchCar(car);
    }

    @RequestMapping(value = "cars", method = RequestMethod.GET)
    @ApiOperation("条件查询")
    public ServerResponse findSerchCar(CarSearchParam carSearchParam) {
        return carService.findSerchCar(carSearchParam);
    }

    @RequestMapping(value = "cars/info", method = RequestMethod.POST)
    @ApiOperation("增加车")
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
    @ApiOperation("批量删除车")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "ids",value = "需要删除的id",required = true,dataType = "String",paramType = "path")
    })
    public ServerResponse deleteBatchCar(String ids) {
        return carService.deleteBatchCar(ids);
    }

    @GetMapping("cars/findCount")
    @ApiOperation("购物车总条数")
    public ServerResponse findCarCount() {
        return carService.findCarCount();
    }

    @GetMapping("cars/groupBySaleTime")
    public ServerResponse groupBySaleTime(String yearMonth) {
        return carService.groupBySaleTime(yearMonth);
    }

    @GetMapping("cars/saleCount")
    public ServerResponse statisticSaleCount(@RequestParam Map<String, String> paramMap) {
        return carService.statisticSaleCount(paramMap);
    }

    @PostMapping("/deleteCart")
    @Check
    @ApiOperation("删除购物车中的商品")
    @ApiImplicitParams({
            @ApiImplicitParam(name="x-auth",value="登录后的头信息",required = true,dataType = "String",paramType = "header"),
            @ApiImplicitParam(name = "goodsids",value = "需要删除的id",required = true,dataType = "String",paramType = "path")
    })
    public ServerResponse deleteCart(String goodsids) {

        return carService.deleteCart(goodsids);
    }

    @GetMapping("/queryPayList")
    @ApiOperation("获取购物车中的商品生成订单")
    @ApiImplicitParams({
            @ApiImplicitParam(name="x-auth",value="登录后的头信息",required = true,dataType = "String",paramType = "header"),
    })
   @Check
    public ServerResponse queryPayList(HttpServletRequest request){
        MemberVo memberVo = (MemberVo) request.getAttribute(SystemConstant.CURRENT_MEMBER);
        Long id = memberVo.getId();
        Map<String,Object> carKey = carService.getCarKey(id);
        return ServerResponse.success(carKey);
    }

}
