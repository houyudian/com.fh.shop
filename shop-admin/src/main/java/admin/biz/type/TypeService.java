package admin.biz.type;

import admin.common.DataTableResult;
import admin.common.ServerResponse;
import admin.param.TypeParam;

public interface TypeService {
    DataTableResult findType(TypeParam typeParam);

    ServerResponse deleteType(Long id);

    ServerResponse addType(TypeParam typeParam);

    ServerResponse getByTypeId(Long id);

    ServerResponse updateType(TypeParam typeParam);

    ServerResponse findAllType();

    ServerResponse findTypeRelate(Long typeId);

}
