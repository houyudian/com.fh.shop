package admin.param;

import java.io.Serializable;

public class TypeParam extends Page implements Serializable {

    private String speacIds;
    private String brandIds;
    private String typeName;
    private String attrNames;
    private String attrValues;

    private Long typeId;

    public Long getTypeId() {
        return typeId;
    }

    public void setTypeId(Long typeId) {
        this.typeId = typeId;
    }

    public String getSpeacIds() {
        return speacIds;
    }

    public void setSpeacIds(String speacIds) {
        this.speacIds = speacIds;
    }

    public String getBrandIds() {
        return brandIds;
    }

    public void setBrandIds(String brandIds) {
        this.brandIds = brandIds;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public String getAttrNames() {
        return attrNames;
    }

    public void setAttrNames(String attrNames) {
        this.attrNames = attrNames;
    }

    public String getAttrValues() {
        return attrValues;
    }

    public void setAttrValues(String attrValues) {
        this.attrValues = attrValues;
    }
}
