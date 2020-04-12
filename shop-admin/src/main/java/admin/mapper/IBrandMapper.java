package admin.mapper;

import admin.param.BrandParam;
import admin.po.Brand;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

public interface IBrandMapper extends BaseMapper<Brand> {
    Long brandCount(BrandParam brandParam);

    List<Brand> findBrandList(BrandParam brandParam);

}
