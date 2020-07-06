package com.fh.shop.api.car.biz;

import com.fh.shop.api.common.ServerResponse;

public interface PayService {
    ServerResponse checkPayStatus(Long id);

    ServerResponse createNative(Long memberId);
}
