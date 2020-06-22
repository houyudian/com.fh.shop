package com.fh.shop.api.brand.biz;

import com.fh.shop.api.brand.po.Brand;
import com.fh.shop.api.common.ServerResponse;

public interface BrandService {
    ServerResponse findReconmendBrand();

    ServerResponse addBrand(Brand brand);

    ServerResponse findAllBrand();

}
