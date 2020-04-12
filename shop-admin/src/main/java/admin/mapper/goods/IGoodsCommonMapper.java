package admin.mapper.goods;

import admin.param.goods.GoodsSearchParam;
import admin.po.goods.GoodsCommon;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface IGoodsCommonMapper extends BaseMapper<GoodsCommon> {
    Long findCount(GoodsSearchParam goodsSearchParam);

    List<GoodsCommon> findGoodsList(GoodsSearchParam goodsSearchParam);

    void updateTreeTable(@Param("oldTreeTable") String oldTreeTable, @Param("treeTable1")String treeTable1);

    void updateCategory(@Param("oldCategoryName") String oldCategoryName,@Param("categoryName") String categoryName);
}
