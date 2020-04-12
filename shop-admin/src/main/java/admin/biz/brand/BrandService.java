package admin.biz.brand;

import admin.common.DataTableResult;
import admin.common.ServerResponse;
import admin.param.BrandParam;
import admin.po.Brand;

public interface BrandService {

    ServerResponse findBrand();

    ServerResponse addBrand(Brand brand);

    DataTableResult findBrandList(BrandParam brandParam);

    ServerResponse updateReconmend(Long brandId, String isReconmend);

    ServerResponse deleteBrandCache();

}
