package admin.vo;

import admin.po.Brand;

import java.util.ArrayList;
import java.util.List;

public class TypeRelateVo {
    private List<Brand> brandList = new ArrayList<>();
    private List<AttrVo> attrVoList = new ArrayList<>();
    private List<SpecVo> speacVoList = new ArrayList<>();

    public List<Brand> getBrandList() {
        return brandList;
    }

    public void setBrandList(List<Brand> brandList) {
        this.brandList = brandList;
    }

    public List<AttrVo> getAttrVoList() {
        return attrVoList;
    }

    public void setAttrVoList(List<AttrVo> attrVoList) {
        this.attrVoList = attrVoList;
    }

    public List<SpecVo> getSpeacVoList() {
        return speacVoList;
    }

    public void setSpeacVoList(List<SpecVo> speacVoList) {
        this.speacVoList = speacVoList;
    }
}
