package admin.biz.category;

import admin.common.ServerResponse;
import admin.param.CategoryParam;
import admin.po.Category;

import java.util.List;

public interface CategoryService {
    ServerResponse findAllCategory();

    ServerResponse addCategory(Category category);

    ServerResponse findById(Long id);

    ServerResponse findCategory(Long id);

    ServerResponse deleteCategory(List<Long> ids);

    ServerResponse updateCategory(CategoryParam categoryParam);

}
