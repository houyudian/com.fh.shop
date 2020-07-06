package com.fh.shop.api.car.controller;

import com.fh.shop.api.car.biz.PayService;
import com.fh.shop.api.common.ServerResponse;
import com.fh.shop.api.common.SystemConstant;
import com.fh.shop.api.member.vo.MemberVo;
import com.fh.shop.api.util.Check;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/pay")
@Api(tags = "支付接口")
public class payController {


    @Autowired
    private PayService payService;

    @PostMapping("/createNative")
    @Check
    public ServerResponse createNative(HttpServletRequest request) {
        MemberVo memberId = (MemberVo) request.getAttribute(SystemConstant.CURRENT_MEMBER);
        Long id = memberId.getId();
        return payService.createNative(id);
    }

    @GetMapping
    @Check
    public ServerResponse checkPayStatus(HttpServletRequest request) {
        MemberVo memberId = (MemberVo) request.getAttribute(SystemConstant.CURRENT_MEMBER);
        Long id = memberId.getId();
        return payService.checkPayStatus(id);
    }
}
