package admin.param.goods;

import admin.po.goods.GoodsCommon;

public class GoodsAddParam {

    private String prices;
    private GoodsCommon goodsCommon=new GoodsCommon();
    private String stocks;
    private String specValueInfos;

    private String goodsImage;

    public String getGoodsImage() {
        return goodsImage;
    }

    public void setGoodsImage(String goodsImage) {
        this.goodsImage = goodsImage;
    }

    public String getPrices() {
        return prices;
    }

    public void setPrices(String prices) {
        this.prices = prices;
    }

    public GoodsCommon getGoodsCommon() {
        return goodsCommon;
    }

    public void setGoodsCommon(GoodsCommon goodsCommon) {
        this.goodsCommon = goodsCommon;
    }

    public String getStocks() {
        return stocks;
    }

    public void setStocks(String stocks) {
        this.stocks = stocks;
    }

    public String getSpecValueInfos() {
        return specValueInfos;
    }

    public void setSpecValueInfos(String specValueInfos) {
        this.specValueInfos = specValueInfos;
    }
}
