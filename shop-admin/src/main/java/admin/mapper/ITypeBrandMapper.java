package admin.mapper;

import admin.param.BrandParam;
import admin.po.Brand;
import admin.po.TypeBrand;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

public interface ITypeBrandMapper extends BaseMapper<TypeBrand> {
    Long findCount(BrandParam brandParam);

    List<Brand> findBrand(BrandParam brandParam);
}
