package admin.controller;

import admin.biz.type.TypeService;
import admin.common.DataTableResult;
import admin.common.ServerResponse;
import admin.param.TypeParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("type")
public class TypeController {
    @Autowired
    private TypeService typeService;

    @RequestMapping("toType")
    public String toType() {
        return "/type/typeList";
    }

    @RequestMapping("toAddType")
    public String toAddType() {
        return "/type/typeAdd";
    }

    @RequestMapping("toTypeEdit")
    public String toTypeEdit() {
        return "/type/typeEdit";
    }

    @ResponseBody
    @RequestMapping("findType")
    public DataTableResult findType(TypeParam typeParam) {
        return typeService.findType(typeParam);
    }

    @RequestMapping("addType")
    @ResponseBody
    public ServerResponse addType(TypeParam typeParam) {
        return typeService.addType(typeParam);
    }

    @RequestMapping("deleteType")
    @ResponseBody
    public ServerResponse deleteType(Long id) {
        return typeService.deleteType(id);
    }

    @ResponseBody
    @RequestMapping("getByTypeId")
    public ServerResponse getByTypeId(Long id) {
        return typeService.getByTypeId(id);
    }

    @RequestMapping("updateType")
    @ResponseBody
    public ServerResponse updateType(TypeParam typeParam) {
        return typeService.updateType(typeParam);
    }

    @RequestMapping("findAllType")
    @ResponseBody
    public ServerResponse findAllType() {
        return typeService.findAllType();
    }

    @RequestMapping("findTypeRelate")
    @ResponseBody
    public ServerResponse findTypeRelate(Long typeId) {
        return typeService.findTypeRelate(typeId);
    }

}
