package com.fh.shop.api.goods.biz;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.fh.shop.api.common.RedisUtil;
import com.fh.shop.api.common.SendMail;
import com.fh.shop.api.common.ServerResponse;
import com.fh.shop.api.common.SystemConstant;
import com.fh.shop.api.goods.mapper.GoodsMapper;
import com.fh.shop.api.goods.po.Goods;
import com.fh.shop.api.goods.vo.GoodsVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class GoodsServiceImpl implements GoodsService {
    @Autowired
    private GoodsMapper goodsMapper;
    @Value("${stock.count}")
    private int stockLess;

    @Override
    public ServerResponse findHotList() {

        String hotGoodsJson = RedisUtil.get(SystemConstant.HOT_GOODS_LIST);
        if (StringUtils.isNotEmpty(hotGoodsJson)) {
            List<GoodsVo> goodsVoList = JSONObject.parseArray(hotGoodsJson, GoodsVo.class);
            RedisUtil.expire(SystemConstant.HOT_GOODS_LIST, SystemConstant.HOT_EXPIRE);
            return ServerResponse.success(goodsVoList);
        }
        QueryWrapper<Goods> goodsQueryWrapper = new QueryWrapper<>();
        goodsQueryWrapper.select("id", "productName", "price", "mainImage");
        goodsQueryWrapper.eq("status", SystemConstant.GOODS_STATUS);
        goodsQueryWrapper.eq("isHot", SystemConstant.GOODS_ISHOT);
        List<Goods> goods = goodsMapper.selectList(goodsQueryWrapper);

        List<GoodsVo> goodsVoList = goods.stream().map(x -> {
            GoodsVo goodsVo = new GoodsVo();
            goodsVo.setId(x.getId());
            goodsVo.setProductName(x.getProductName());
            goodsVo.setPrice(x.getPrice().toString());
            goodsVo.setImg(x.getMainImage());
            return goodsVo;
        }).collect(Collectors.toList());
        hotGoodsJson = JSONObject.toJSONString(goodsVoList);
        RedisUtil.setEx(SystemConstant.HOT_GOODS_LIST, hotGoodsJson, SystemConstant.HOT_EXPIRE);
        return ServerResponse.success(goodsVoList);
    }

    public ServerResponse queryGoodsList() {

        //定时检查库存，发送邮件 ：

        //邮件内容为 表格方式

        //商品名 商品价格  现有库存量

        QueryWrapper<Goods> goodsQueryWrapper = new QueryWrapper<>();
        //商品名，商品单价，商品现有库存
        goodsQueryWrapper.select("productName", "price", "stock");
        List<Goods> goodsList = goodsMapper.selectList(goodsQueryWrapper);

        for (Goods goods : goodsList) {
            goods.setPrices(goods.getPrice().toString());
        }

        String html = "<html><body><font size='15' color='black'><table align='left' border='1' cellpadding='1' cellspacing='0'>"
                + "<tr style='font-size: 16px;' height='20px' bgcolor='red'>"
                + "<th>商品名</th><th>价格</th><th>库存</th>";
        for (Goods productVo : goodsList) {
            html += "<tr><td>" + productVo.getProductName() + "</td><td align='center'>" + productVo.getPrices() + "</td><td>" + productVo.getStock() + "</td></tr>";
        }
        html += "</table></font></body></html>";

        String user = "389168944@qq.com";
        String password = "yhwpqpuyqtiybhdd";
        String host = "imap.qq.com";
        String from = "389168944@qq.com";
        String to = "389168944@qq.com";// 收件人
        String subject = "huahua";
        //邮箱内容
        StringBuffer sb = new StringBuffer();
        String dataInfo = html;
        sb.append(html);
        try {
            String res = SendMail.sendMail(user, password, host, from, to, subject, sb.toString());
            System.out.println(res);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return ServerResponse.success(html);

    }

    @Override
    public List<Goods> stockLess() {

        QueryWrapper<Goods> queryWrapper = new QueryWrapper<>();
        queryWrapper.lt("stock", stockLess);
        List<Goods> goods = goodsMapper.selectList(queryWrapper);
        return goods;
    }
}

