package com.fh.shop.api.goods.biz;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.fh.shop.api.common.RedisUtil;
import com.fh.shop.api.common.ServerResponse;
import com.fh.shop.api.common.SystemConstant;
import com.fh.shop.api.goods.mapper.GoodsMapper;
import com.fh.shop.api.goods.po.Goods;
import com.fh.shop.api.goods.vo.GoodsVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class GoodsServiceImpl implements GoodsService {
    @Autowired
    private GoodsMapper goodsMapper;

    @Override
    public ServerResponse findHotList() {

        String hotGoodsJson = RedisUtil.get(SystemConstant.HOT_GOODS_LIST);
        if (StringUtils.isNotEmpty(hotGoodsJson)) {
            List<GoodsVo> goodsVoList = JSONObject.parseArray(hotGoodsJson, GoodsVo.class);
            RedisUtil.expire(SystemConstant.HOT_GOODS_LIST, SystemConstant.HOT_EXPIRE);
            return ServerResponse.success(goodsVoList);
        }
        QueryWrapper<Goods> goodsQueryWrapper = new QueryWrapper<>();
        goodsQueryWrapper.select("id", "productName", "price");
        goodsQueryWrapper.eq("status", SystemConstant.GOODS_STATUS);
        goodsQueryWrapper.eq("isHot", SystemConstant.GOODS_ISHOT);
        List<Goods> goods = goodsMapper.selectList(goodsQueryWrapper);

        List<GoodsVo> goodsVoList = goods.stream().map(x -> {
            GoodsVo goodsVo = new GoodsVo();
            goodsVo.setId(x.getId());
            goodsVo.setProductName(x.getProductName());
            goodsVo.setPrice(x.getPrice().toString());
            return goodsVo;
        }).collect(Collectors.toList());
        hotGoodsJson = JSONObject.toJSONString(goodsVoList);
        RedisUtil.setEx(SystemConstant.HOT_GOODS_LIST,hotGoodsJson,SystemConstant.HOT_EXPIRE);
        return ServerResponse.success(goodsVoList);
    }
}
