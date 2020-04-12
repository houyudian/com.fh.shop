package admin.controller;

import admin.biz.category.CategoryService;
import admin.common.ServerResponse;
import admin.param.CategoryParam;
import admin.po.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("category")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;
    @RequestMapping("toCate")
    public String toCate() {
        return "category/cate";
    }

    @RequestMapping("findAllCategory")
    @ResponseBody
    public ServerResponse findAllCategory() {
        return categoryService.findAllCategory();
    }

    @RequestMapping("deleteCategory")
    @ResponseBody
    public ServerResponse deleteCategory(@RequestParam("ids[]") List<Long> ids) {
        return categoryService.deleteCategory(ids);
    }

    @RequestMapping("addCategory")
    @ResponseBody
    public ServerResponse addCategory(Category category) {
        return categoryService.addCategory(category);
    }
    @RequestMapping("findById")
    @ResponseBody
    public ServerResponse findById(Long id){
        return categoryService.findById(id);
    }
@RequestMapping("updateCategory")
@ResponseBody
    public ServerResponse updateCategory(CategoryParam categoryParam){
        return categoryService.updateCategory(categoryParam);
    }
@RequestMapping("findCategory")
@ResponseBody
    public ServerResponse findCategory(Long id){
        return categoryService.findCategory(id);
    }
}
