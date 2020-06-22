package com.fh.shop.api.goods.biz;

import com.fh.shop.api.common.ServerResponse;
import com.fh.shop.api.goods.po.Goods;

import java.util.List;

public interface GoodsService {

    ServerResponse findHotList();

    ServerResponse queryGoodsList();

    public List<Goods> stockLess();
}
