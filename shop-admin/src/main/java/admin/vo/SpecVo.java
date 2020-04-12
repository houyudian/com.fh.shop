package admin.vo;

import admin.po.Speac;
import admin.po.SpeacValue;

import java.io.Serializable;
import java.util.List;

public class SpecVo implements Serializable {
    private Speac speac;
    private List<SpeacValue> speacValueList;

    public List<SpeacValue> getSpeacValueList() {
        return speacValueList;
    }

    public void setSpeacValueList(List<SpeacValue> speacValueList) {
        this.speacValueList = speacValueList;
    }

    public Speac getSpeac() {
        return speac;
    }

    public void setSpeac(Speac speac) {
        this.speac = speac;
    }


}
