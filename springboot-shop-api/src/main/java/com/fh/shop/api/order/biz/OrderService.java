package com.fh.shop.api.order.biz;

import com.fh.shop.api.car.po.Car;
import com.fh.shop.api.common.ServerResponse;
import com.fh.shop.api.order.param.OrderParam;
import com.fh.shop.api.order.po.Order;

public interface OrderService {
    ServerResponse generateConfirmOrder(Long id);

    ServerResponse generateOrder(OrderParam orderParam);

    ServerResponse getResult(Long id);

    void createOrder(Car car, OrderParam orderParam);

    void updateOrder(Order order);

}
