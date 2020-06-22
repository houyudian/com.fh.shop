package com.fh.shop.api.car.biz;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.fh.shop.api.car.mapper.CarMapper;
import com.fh.shop.api.car.param.CarSearchParam;
import com.fh.shop.api.car.po.Car;
import com.fh.shop.api.car.po.CarItem;
import com.fh.shop.api.common.*;
import com.fh.shop.api.goods.mapper.GoodsMapper;
import com.fh.shop.api.goods.po.Goods;
import com.fh.shop.api.member.vo.MemberVo;
import com.fh.shop.api.util.BigDecimalUtil;
import com.fh.shop.api.util.DateUtil;
import com.fh.shop.api.util.KeyUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class CarServiceImpl implements CarService {
    @Autowired
    private CarMapper carMapper;
    @Autowired
    private GoodsMapper goodsMapper;

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

    @Override
    public ServerResponse findCarCount() {

        List<Map> carCount = carMapper.findCarCount();
        return ServerResponse.success(carCount);
    }

    @Override
    public ServerResponse groupBySaleTime(String yearMonth) {
        if (StringUtils.isEmpty(yearMonth)) {
            return ServerResponse.error(ResponseEnum.QUERY_PARAM_IS_EMPTY);
        }
        List<Map> carMapList = carMapper.groupBySaleTime(yearMonth);
        return ServerResponse.success(carMapList);

    }

    @Override
    public ServerResponse statisticSaleCount(Map<String, String> paramMap) {

        String brandId = paramMap.get("brandId");
        String startDate = paramMap.get("startDate");
        String endDate = paramMap.get("endDate");
        if (StringUtils.isEmpty(brandId) || StringUtils.isEmpty(startDate) || StringUtils.isEmpty(endDate)) {
            return ServerResponse.error(ResponseEnum.PARAM_IS_EMPTY);
        }
        List<Map> maps = carMapper.statisticSaleCount(paramMap);

        List<String> dayList = DateUtil.dayList(startDate, endDate, DateUtil.Y_M_D);
        List<Long> dataList = new ArrayList<>();

        dayList.forEach(x -> {
            List<Map> itemMap = maps.stream().filter(y -> String.valueOf(y.get("day")).equals((x))).collect(Collectors.toList());
            if (itemMap.size() == 0) {
                dataList.add(0L);
            } else {
                dataList.add((Long) itemMap.get(0).get("count"));
            }
        });
        Map result = new HashMap<>();
        result.put("dayList", dayList);
        result.put("dataList", dataList);
        return ServerResponse.success(result);
    }

    @Override
    public ServerResponse addItem(Long memberId, Long goodsId, int num) {
        Goods goods = goodsMapper.selectById(goodsId);
        if (goods == null) {
            return ServerResponse.error(ResponseEnum.CAR_GOODS_IS_NULL);
        }
        String status = goods.getStatus();
        if (SystemConstant.GOODS_IS_DOWN.equals(status)) {
            return ServerResponse.error(ResponseEnum.CAR_GOODS_IS_DOWN);
        }
        String key = KeyUtil.buildCarKey(memberId);
        String carJson = RedisUtil.get(key);
        if (StringUtils.isNotEmpty(carJson)) {
            //商品在购物车已存在/
            Car car = JSONObject.parseObject(carJson, Car.class);
            List<CarItem> carItems = car.getCarItems();
            Optional<CarItem> carItem = carItems.stream().filter(x -> x.getGoodsId().longValue() == goodsId.longValue()).findFirst();
            if (carItem.isPresent()) {//购物车中存在这个商品
                CarItem item = carItem.get();
                int num1 = item.getNum() + num;
                if (num1 < 0) {
                    return ServerResponse.error(ResponseEnum.CAR_GOODS_NUM_IS_ERROR);
                }
                if (num1 == 0) {
                    carItems.remove(item);
                    if (carItems.size() == 0) {
                        RedisUtil.delete(key);
                        return ServerResponse.success();
                    }
                }
                item.setNum(num1);
                String price = item.getPrice();
                BigDecimal subPrice = BigDecimalUtil.mul(num1 + "", price);
                item.setSubPrice(subPrice.toString());
                updateCar(memberId, car);
            } else {//购物车中不存在这个商品
                if (num < 0) {
                    return ServerResponse.error(ResponseEnum.CAR_GOODS_NUM_IS_ERROR);
                }
                CarItem item = buildCarItem(num, goods);
                carItems.add(item);
                updateCar(memberId, car);
            }
        } else {//购物车不存在
            if (num < 0) {
                return ServerResponse.error(ResponseEnum.CAR_GOODS_NUM_IS_ERROR);
            }
            Car car = new Car();
            List<CarItem> carItems = car.getCarItems();
            CarItem item = buildCarItem(num, goods);
            carItems.add(item);
            updateCar(memberId, car);
        }
        return ServerResponse.success();
    }

    @Override
    public ServerResponse find(Long memberId) {
        String key = KeyUtil.buildCarKey(memberId);
        String s = RedisUtil.get(key);
        Car car = JSONObject.parseObject(s, Car.class);
        return ServerResponse.success(car);
    }

    @Override
    public ServerResponse addCarItem(Long id, Long goodsId, int num) {

        Goods goods = goodsMapper.selectById(goodsId);
        if (goods == null) {
            return ServerResponse.error(ResponseEnum.CAR_GOODS_IS_NULL);
        }
        String status = goods.getStatus();
        if (SystemConstant.GOODS_IS_DOWN.equals(status)) {
            return ServerResponse.error(ResponseEnum.CAR_GOODS_IS_DOWN);
        }

        String key = KeyUtil.buildCarKey(id);
        String carJson = RedisUtil.get(key);
        if (StringUtils.isNotEmpty(carJson)) {
            Car car = JSONObject.parseObject(carJson, Car.class);
            List<CarItem> carItems = car.getCarItems();
            Optional<CarItem> carItem = carItems.stream().filter((x -> x.getGoodsId() == goodsId.longValue())).findFirst();
            if (carItem.isPresent()) {
                CarItem item = carItem.get();
                int num1 = item.getNum() + num;
                if (num1 < 0) {
                    return ServerResponse.error(ResponseEnum.CAR_GOODS_NUM_IS_ERROR);
                }
                if (num1 == 0) {
                    carItems.remove(item);
                    if (carItems.size() == 0) {
                        RedisUtil.delete(key);
                        return ServerResponse.success();
                    }
                } else {
                    item.setNum(num1);
                    String price = item.getPrice();
                    BigDecimal mul = BigDecimalUtil.mul(num1 + "", price);
                    item.setSubPrice(mul.toString());
                }
                updateCar(id, car);
            } else {
                if (num <= 0) {
                    return ServerResponse.error(ResponseEnum.CAR_GOODS_NUM_IS_ERROR);
                }
                CarItem item = buildCarItem(num, goods);
                carItems.add(item);
                updateCar(id, car);
            }
        } else {
            if (num <= 0) {
                return ServerResponse.error(ResponseEnum.CAR_GOODS_NUM_IS_ERROR);
            }
            Car car = new Car();
            CarItem item = buildCarItem(num, goods);
            List<CarItem> carItems = car.getCarItems();
            carItems.add(item);
            updateCar(id, car);
        }
        return ServerResponse.success();
    }

    private CarItem buildCarItem(int num, Goods goods) {
        CarItem item = new CarItem();
        item.setGoodsId(goods.getId());
        item.setNum(num);
        item.setPrice(goods.getPrice().toString());
        item.setName(goods.getProductName());
        item.setImg(goods.getMainImage());
        item.setSubPrice(BigDecimalUtil.mul(num + "", goods.getPrice().toString()).toString());
        return item;
    }

    private void updateCar(Long memberId, Car car) {
        List<CarItem> carItems = car.getCarItems();
        int sum = carItems.stream().mapToInt(CarItem::getNum).sum();
        car.setTotalNum(sum);
        BigDecimal totalPrice = new BigDecimal(0);
        for (CarItem c : carItems) {
            totalPrice = BigDecimalUtil.add(totalPrice.toString(), c.getSubPrice());
        }
        car.setTotalPrice(totalPrice.toString());
        String s = JSONObject.toJSONString(car);
        RedisUtil.set(KeyUtil.buildCarKey(memberId), s);

    }

    @Override
    public ServerResponse deleteCart(String goodsArr) {
        String[] goodsids = goodsArr.split(",");
        //取出用户信息
        HttpServletRequest request = WebContext.get();
        MemberVo memberVo = (MemberVo) request.getAttribute(SystemConstant.CURRENT_MEMBER);
        //获取用户id
        Long id = memberVo.getId();
        //制作redis的key
        String cartKey = KeyUtil.buildCarKey(id);
        //通过key从redis中取出购物车
        String cartStr = RedisUtil.get(cartKey);
        //将购物车字符串转换为实体bean
        Car cart = JSONObject.parseObject(cartStr, Car.class);
        //获取商品集合
        List<CarItem> cartItems = cart.getCarItems();
        //判断购物车中每个商品的id是否与传过来的id一样
        for (int i = 0; i < cartItems.size(); i++) {
            for (int j = 0; j < goodsids.length; j++) {
                Long goodsid = Long.valueOf(goodsids[j]);
                if (goodsid == cartItems.get(i).getGoodsId()) {
                    //购物车中的商品id与传过来的商品id一样，删除
                    cartItems.remove(cartItems.get(i));
                }
            }
        }
        //将删除后的商品集合放入购物车中
        cart.setCarItems(cartItems);
        //将购物车转化为字符串
        String cartString = JSON.toJSONString(cart);
        //将购物车字符串作为value存入redis中
        RedisUtil.set(cartKey, cartString);
        return ServerResponse.success();
    }
}
