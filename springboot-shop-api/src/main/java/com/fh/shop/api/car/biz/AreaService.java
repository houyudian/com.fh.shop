package com.fh.shop.api.car.biz;

import com.fh.shop.api.common.ServerResponse;

public interface AreaService {
    ServerResponse findAreaList(Long id);

    ServerResponse findAreaTree();

}
