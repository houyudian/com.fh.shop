package admin.vo;

import admin.po.goods.Goods;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class GoodsVo implements Serializable {


    private GoodsCommonVo goodsCommon=new GoodsCommonVo();
    private List<Goods> goodsList=new ArrayList<>();

    public GoodsCommonVo getGoodsCommon() {
        return goodsCommon;
    }

    public void setGoodsCommon(GoodsCommonVo goodsCommon) {
        this.goodsCommon = goodsCommon;
    }

    public List<Goods> getGoodsList() {
        return goodsList;
    }

    public void setGoodsList(List<Goods> goodsList) {
        this.goodsList = goodsList;
    }
}
