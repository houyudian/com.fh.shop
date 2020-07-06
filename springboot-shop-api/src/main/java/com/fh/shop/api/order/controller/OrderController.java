package com.fh.shop.api.order.controller;

import com.fh.shop.api.common.RedisUtil;
import com.fh.shop.api.common.ServerResponse;
import com.fh.shop.api.common.SystemConstant;
import com.fh.shop.api.member.vo.MemberVo;
import com.fh.shop.api.order.biz.OrderService;
import com.fh.shop.api.order.param.OrderParam;
import com.fh.shop.api.util.Check;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.UUID;

@RestController
@RequestMapping("/api/orders")
@Api("订单")
public class OrderController {
    @Autowired
    private OrderService orderService;

    public ServerResponse generateConfirmOrder(HttpServletRequest request) {
        MemberVo memberVo = (MemberVo) request.getAttribute(SystemConstant.CURRENT_MEMBER);
        Long id = memberVo.getId();
        return orderService.generateConfirmOrder(id);
    }

    @PostMapping("/generateOrder")
    @Check
    @ApiOperation("生成订单")

    @ApiImplicitParams({
            @ApiImplicitParam(name = "x-auth", value = "登录后的头信息", required = true, dataType = "String", paramType = "header"),
    })
    public ServerResponse generateOrder(HttpServletRequest request, OrderParam orderParam) {
        MemberVo memberVo = (MemberVo) request.getAttribute(SystemConstant.CURRENT_MEMBER);
        Long id = memberVo.getId();
        orderParam.setMemberId(id);
        return orderService.generateOrder(orderParam);
    }

    @GetMapping("/getResult")
    @Check
    @ApiOperation("订单结果")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "x-auth", value = "登录后的头信息", required = true, dataType = "String", paramType = "header"),
    })
    public ServerResponse getResult(HttpServletRequest request) {
        MemberVo memberVo = (MemberVo) request.getAttribute(SystemConstant.CURRENT_MEMBER);
        Long id = memberVo.getId();
        return orderService.getResult(id);
    }
@GetMapping("/createToken")
    public ServerResponse createToken() {
        String token = UUID.randomUUID().toString().replace("-", "");
        RedisUtil.setEx(token, token, 60);
        return ServerResponse.success(token);
    }

}
