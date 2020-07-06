package com.fh.shop.api.car.biz;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.fh.shop.api.car.mapper.AddressMapper;
import com.fh.shop.api.car.po.Address;
import com.fh.shop.api.common.ServerResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AddressServiceImpl implements AddressService{
   @Autowired
    private AddressMapper addressMapper;

    @Override
    public ServerResponse queryAddressList(Long id) {
        QueryWrapper<Address> queryWrapper=new QueryWrapper<Address>();
        queryWrapper.eq("memberId",id);
        //queryWrapper.orderByDesc("id");
        List<Address> addressList=addressMapper.selectList(queryWrapper);
        return ServerResponse.success(addressList);

    }

    @Override
    public ServerResponse saveAddress(Long id, Address address) {

        address.setMemberId(id);
        if(address.getId() == null){
            addressMapper.insert(address);
        }else{
            addressMapper.updateById(address);
        }
        return ServerResponse.success();
    }

    @Override
    public Address getAddressById(Long addressId) {
        Address address=addressMapper.selectById(addressId);
        return address;
    }


}
