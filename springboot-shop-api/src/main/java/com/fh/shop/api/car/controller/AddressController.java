package com.fh.shop.api.car.controller;

import com.fh.shop.api.car.biz.AddressService;
import com.fh.shop.api.car.po.Address;
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

@RequestMapping("/api/address")
@RestController
@Api(tags = "会员地址")
public class AddressController {
    @Autowired
    private AddressService addressService;

    @GetMapping("/queryAddressList")
    @Check
    @ApiOperation("查询地址")
    @ApiImplicitParams({
            @ApiImplicitParam(name="x-auth",value="登录后的头信息",required = true,dataType = "String",paramType = "header")})
    public ServerResponse queryAddressList(HttpServletRequest request){
        MemberVo memberVo = (MemberVo) request.getAttribute(SystemConstant.CURRENT_MEMBER);
        Long id = memberVo.getId();
        return addressService.queryAddressList(id);
    }

    @PutMapping
   @Check
    public ServerResponse saveAddress(HttpServletRequest request, Address address){

        MemberVo memberVo = (MemberVo) request.getAttribute(SystemConstant.CURRENT_MEMBER);
        Long id = memberVo.getId();
        return addressService.saveAddress(id,address);
    }

    @GetMapping("/{addressId}")
    @Check
    public ServerResponse getAddressById(@PathVariable Long addressId){
       Address address= addressService.getAddressById(addressId);
        return ServerResponse.success(address);
    }

}
