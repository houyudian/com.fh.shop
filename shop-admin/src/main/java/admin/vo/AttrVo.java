package admin.vo;

import admin.po.AttrValue;
import admin.po.TypeAttr;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Service
public class AttrVo implements Serializable {

private TypeAttr attr;

private List<AttrValue> attrValue=new ArrayList();

    public TypeAttr getAttr() {
        return attr;
    }

    public void setAttr(TypeAttr attr) {
        this.attr = attr;
    }

    public List<AttrValue> getAttrValue() {
        return attrValue;
    }

    public void setAttrValue(List<AttrValue> attrValue) {
        this.attrValue = attrValue;
    }
}
