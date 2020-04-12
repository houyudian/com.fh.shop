package admin.biz.goods;

import admin.common.DataTableResult;
import admin.common.ServerResponse;
import admin.param.goods.GoodsAddParam;
import admin.param.goods.GoodsSearchParam;

public interface GoodsService {
    ServerResponse addGoods(GoodsAddParam goodsAddParam);

    DataTableResult findGoodsList(GoodsSearchParam goodsSearchParam);

    ServerResponse updateStatus(Long goodsCommonId, String status);

    ServerResponse updateHot(Long goodsCommonId, String hot);

    ServerResponse deleteHotPath();

}
