package admin.mapper;

import admin.param.TypeParam;
import admin.po.Speac;
import admin.po.TypeSpeac;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

public interface ITypeSpeacMapper extends BaseMapper<TypeSpeac> {
    Long findCount(TypeParam typeParam);

    List<Speac> findSpeac(TypeParam typeParam);

}
