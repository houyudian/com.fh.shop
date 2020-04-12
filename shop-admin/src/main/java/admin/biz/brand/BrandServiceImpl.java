package admin.biz.brand;

import admin.common.DataTableResult;
import admin.common.ServerResponse;
import admin.common.SystemConstant;
import admin.mapper.IBrandMapper;
import admin.param.BrandParam;
import admin.po.Brand;
import admin.util.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BrandServiceImpl implements BrandService {
    @Autowired
    private IBrandMapper brandMapper;

    @Override
    public ServerResponse findBrand() {

        List<Brand> brandList = brandMapper.selectList(null);
        return ServerResponse.success(brandList);
    }

    @Override
    public ServerResponse addBrand(Brand brand) {
        brandMapper.insert(brand);
        return ServerResponse.success(brand);
    }

    @Override
    public DataTableResult findBrandList(BrandParam brandParam) {

        Long count = brandMapper.brandCount(brandParam);
        List<Brand> brands = brandMapper.findBrandList(brandParam);
        return new DataTableResult(brandParam.getDraw(), count, count, brands);
    }

    @Override
    public ServerResponse updateReconmend(Long brandId, String isReconmend) {


        Brand brand = new Brand();
        brand.setId(brandId);
        brand.setIsReconmend(isReconmend);
        brandMapper.updateById(brand);
        return ServerResponse.success();
    }

    @Override
    public ServerResponse deleteBrandCache() {

        RedisUtil.delete(SystemConstant.BRAND_KEY);
        return ServerResponse.success();
    }
}
