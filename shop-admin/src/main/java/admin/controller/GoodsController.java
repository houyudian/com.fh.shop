package admin.controller;

import admin.biz.goods.GoodsService;
import admin.common.DataTableResult;
import admin.common.ServerResponse;
import admin.param.goods.GoodsAddParam;
import admin.param.goods.GoodsSearchParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@RequestMapping("goods")
@Controller
public class GoodsController {
    @Autowired
    private GoodsService goodsService;

    @RequestMapping("toGoods")
    public String toGoods() {
        return "goods/goodsAdd";
    }

    @RequestMapping("toList")
    public String toList() {
        return "goods/goodsList";
    }
@RequestMapping("findGoodsList")
@ResponseBody
    public DataTableResult findGoodsList(GoodsSearchParam goodsSearchParam){
        return goodsService.findGoodsList(goodsSearchParam);
    }

    @ResponseBody
    @RequestMapping("addGoods")
    public ServerResponse addGoods(GoodsAddParam goodsAddParam) {
        return goodsService.addGoods(goodsAddParam);
    }
@ResponseBody
@RequestMapping(value = "updateStatus",method = RequestMethod.POST)
    public ServerResponse updateStatus(Long goodsCommonId,String status){
        return goodsService.updateStatus(goodsCommonId,status);
    }
@ResponseBody
@RequestMapping(value = "/updateHot",method = RequestMethod.POST)
    public ServerResponse updateHot(Long goodsCommonId,String hot){
        return goodsService.updateHot(goodsCommonId,hot);
    }

    @ResponseBody
    @RequestMapping(value = "deleteHotPath",method = RequestMethod.GET)
    public ServerResponse deleteHotPath(){
        return goodsService.deleteHotPath();
    }

}
