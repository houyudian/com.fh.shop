package admin.vo;

import admin.po.Type;
import admin.po.TypeAttr;

import java.util.ArrayList;
import java.util.List;

public class TypeVo {

    private Type type;
    private List<Long> speacList=new ArrayList<>();
    private List<Long> brandList=new ArrayList<>();
    private List<TypeAttr> attrList=new ArrayList<>();

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public List<Long> getSpeacList() {
        return speacList;
    }

    public void setSpeacList(List<Long> speacList) {
        this.speacList = speacList;
    }

    public List<Long> getBrandList() {
        return brandList;
    }

    public void setBrandList(List<Long> brandList) {
        this.brandList = brandList;
    }

    public List<TypeAttr> getAttrList() {
        return attrList;
    }

    public void setAttrList(List<TypeAttr> attrList) {
        this.attrList = attrList;
    }
}
