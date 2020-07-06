package com.fh.shop.api.car.biz;

import com.fh.shop.api.car.po.Address;
import com.fh.shop.api.common.ServerResponse;

public interface AddressService {
    ServerResponse queryAddressList(Long id);

    ServerResponse saveAddress(Long id, Address address);

    Address getAddressById(Long addressId);



}
