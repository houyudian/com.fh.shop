package com.fh.shop.api.goods.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fh.shop.api.goods.po.Goods;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

public interface GoodsMapper extends BaseMapper<Goods> {
@Update("update t_goods set stock=stock-#{num} where id=#{goodsId} and stock >= #{num}")
    int updateStock(@Param("goodsId") Long goodsId,@Param("num") int num);
}
