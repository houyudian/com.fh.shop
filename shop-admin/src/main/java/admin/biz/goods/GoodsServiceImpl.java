package admin.biz.goods;

import admin.common.DataTableResult;
import admin.common.ResponseEnum;
import admin.common.ServerResponse;
import admin.common.SystemConstant;
import admin.mapper.goods.GoodsImgMapper;
import admin.mapper.goods.IGoodsCommonMapper;
import admin.mapper.goods.IGoodsMapper;
import admin.param.goods.GoodsAddParam;
import admin.param.goods.GoodsSearchParam;
import admin.po.goods.Goods;
import admin.po.goods.GoodsCommon;
import admin.po.goods.GoodsImage;
import admin.util.RedisUtil;
import admin.vo.GoodsCommonVo;
import admin.vo.GoodsVo;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class GoodsServiceImpl implements GoodsService {
    @Autowired
    private IGoodsMapper goodsMapper;
    @Autowired
    private IGoodsCommonMapper goodsCommonMapper;
    @Autowired
    private GoodsImgMapper goodsImgMapper;

    @Override
    public ServerResponse addGoods(GoodsAddParam goodsAddParam) {
        GoodsCommon goodsCommon = goodsAddParam.getGoodsCommon();
        goodsCommon.setIsHot("0");
        goodsCommon.setStatus("0");
        goodsCommonMapper.insert(goodsCommon);
        Long id = goodsCommon.getId();
        String name = goodsCommon.getName();
        String prices = goodsAddParam.getPrices();
        String stocks = goodsAddParam.getStocks();
        //图片
        String goodsImage = goodsAddParam.getGoodsImage();

        String specValueInfos = goodsAddParam.getSpecValueInfos();
        if (StringUtils.isEmpty(prices) || StringUtils.isEmpty(stocks) || StringUtils.isEmpty(specValueInfos)) {
            return ServerResponse.error(ResponseEnum.SKU_INFO_IS_EMPTY);
        }
        String[] priceArr = prices.split(",");
        String[] stockArr = stocks.split(",");
        String[] specValueArr = specValueInfos.split(";");

        for (int i = 0; i < priceArr.length; i++) {
            Goods goods = new Goods();
            goods.setIsHot("0");
            goods.setStatus("0");

            goods.setPrice(new BigDecimal(priceArr[i]));
            goods.setStock(Long.parseLong(stockArr[i]));
            goods.setCommonId(id);

            String specValue = specValueArr[i];
            Long colorId = Long.valueOf(specValue.split(",")[0].split(":")[0]);
            goods.setColorId(colorId);
            String img = Arrays.stream(goodsImage.split(";")).filter(x -> Long.valueOf(x.split(":")[0]) == colorId).map(y -> y.split(":")[1].split(",")[0]).collect(Collectors.toList()).get(0);

            goods.setMainImage(img);
            String[] speacValueAndNameArr = specValue.split(",");
            StringBuilder skuProductName = new StringBuilder();
            StringBuilder skuSpecValueId = new StringBuilder();
            skuProductName.append(name);
            for (String s : speacValueAndNameArr) {
                skuProductName.append(s.split(":")[1]);
                skuSpecValueId.append(",").append(s.split(":")[0]);
            }
            goods.setProductName(skuProductName.toString());
            goods.setSpecValueId(skuSpecValueId.substring(1));
            goods.setSpecValueInfos(specValue);
            goodsMapper.insert(goods);
        }
        String[] goodsImg = goodsImage.split(";");
        for (String img : goodsImg) {
            Long colorId = Long.valueOf(img.split(":")[0]);

            String[] imgArr = img.split(":")[1].split(",");

            for (String i : imgArr) {
                GoodsImage image = new GoodsImage();
                image.setImage(i);
                image.setColorId(colorId);
                image.setCommonId(id);
                goodsImgMapper.insert(image);
            }

        }
        return ServerResponse.success();
    }

    @Override
    public DataTableResult findGoodsList(GoodsSearchParam goodsSearchParam) {
        Long count = goodsCommonMapper.findCount(goodsSearchParam);
        List<GoodsVo> goodsVos = new ArrayList<>();
        if (count > 0) {
            List<GoodsCommon> goodsList = goodsCommonMapper.findGoodsList(goodsSearchParam);
            List<GoodsCommonVo> goodsCommonVoList = goodsList.stream().map(x -> {
                GoodsCommonVo g = new GoodsCommonVo();
                g.setId(x.getId());
                g.setName(x.getName());
                g.setBrandName(x.getBrandName());
                g.setCateName(x.getCateName());
                g.setIsHot(x.getIsHot());
                g.setPrice(x.getPrice().toString());
                g.setStatus(x.getStatus());
                g.setStock(x.getStock());
                g.setMainImage(x.getMainImage());
                return g;
            }).collect(Collectors.toList());

            List<Long> idList = goodsList.stream().map(x -> x.getId()).collect(Collectors.toList());
            QueryWrapper<Goods> goodsQueryWrapper = new QueryWrapper<>();
            goodsQueryWrapper.in("commonId", idList);

            List<Goods> goods = goodsMapper.selectList(goodsQueryWrapper);
            goodsCommonVoList.forEach(x -> {
                GoodsVo goodsVo = new GoodsVo();
                goodsVo.setGoodsCommon(x);
                List<Goods> goodsInfoList = goods.stream().filter(y -> y.getCommonId() == x.getId()).collect(Collectors.toList());
                goodsVo.setGoodsList(goodsInfoList);
                goodsVos.add(goodsVo);
            });
        }
        return new DataTableResult(goodsSearchParam.getDraw(), count, count, goodsVos);
    }

    @Override
    public ServerResponse updateStatus(Long goodsCommonId, String status) {

        GoodsCommon goodsCommon = new GoodsCommon();

        goodsCommon.setId(goodsCommonId);
        goodsCommon.setStatus(status);
        goodsCommonMapper.updateById(goodsCommon);

        Goods goods = new Goods();
        goods.setStatus(status);
        QueryWrapper<Goods> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("commonId", goodsCommonId);
        goodsMapper.update(goods, queryWrapper);
        return ServerResponse.success();
    }

    @Override
    public ServerResponse updateHot(Long goodsCommonId, String hot) {
        GoodsCommon goodsCommon = new GoodsCommon();
        goodsCommon.setId(goodsCommonId);
        goodsCommon.setIsHot(hot);
        goodsCommonMapper.updateById(goodsCommon);
        Goods goods = new Goods();
        goods.setIsHot(hot);
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("commonId", goodsCommonId);
        goodsMapper.update(goods, queryWrapper);
        return ServerResponse.success();
    }

    @Override
    public ServerResponse deleteHotPath() {

        RedisUtil.delete(SystemConstant.HOT_GOODS_LIST);
        return ServerResponse.success();
    }
}
