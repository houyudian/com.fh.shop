package admin.mapper;

import admin.param.TypeParam;
import admin.po.Type;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

public interface ITypeMapper extends BaseMapper<Type> {
    Long findCount(TypeParam typeParam);

    List<Type> findType(TypeParam typeParam);
}
