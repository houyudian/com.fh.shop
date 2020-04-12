package admin.biz.category;

import admin.common.ServerResponse;
import admin.common.SystemConstant;
import admin.mapper.ICategoryMapper;
import admin.mapper.goods.IGoodsCommonMapper;
import admin.param.CategoryParam;
import admin.po.Category;
import admin.util.RedisUtil;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    private ICategoryMapper categoryMapper;
    @Autowired
    private IGoodsCommonMapper goodsCommonMapper;

    @Override
    public ServerResponse findAllCategory() {

        String cateJson = RedisUtil.get(SystemConstant.CATE_KEY);
        if (StringUtils.isNotEmpty(cateJson)){
            List<Category> categories = JSONObject.parseArray(cateJson, Category.class);
            RedisUtil.expire(SystemConstant.CATE_KEY,SystemConstant.CATE_EXPIRE);
        }
        List<Category> categories = categoryMapper.selectList(null);
        String s = JSONObject.toJSONString(categories);
        RedisUtil.setEx(SystemConstant.CATE_KEY,s,SystemConstant.CATE_EXPIRE);
        return ServerResponse.success(categories);
    }

    @Override
    public ServerResponse addCategory(Category category) {
        RedisUtil.delete(SystemConstant.CATE_KEY);
        categoryMapper.insert(category);
        return ServerResponse.success();
    }

    @Override
    public ServerResponse findById(Long id) {
        Category category = categoryMapper.selectById(id);
        return ServerResponse.success(category);
    }

    @Override
    public ServerResponse findCategory(Long id) {
        String s = RedisUtil.get(SystemConstant.CATE_KEY);
       List<Category> cate = new ArrayList<>();
        if (StringUtils.isNotEmpty(s)){
            cate = categoryMapper.selectList(null);
            String s1 = JSONObject.toJSONString(cate);
            RedisUtil.setEx(SystemConstant.CATE_KEY,s1,SystemConstant.CATE_EXPIRE);
        }else {
            cate=JSONObject.parseArray(s,Category.class);
            RedisUtil.expire(SystemConstant.CATE_KEY,SystemConstant.CATE_EXPIRE);
        }
        List<Category> collect = cate.stream().filter(x -> x.getPid() == id).collect(Collectors.toList());
        return ServerResponse.success(collect);
    }

    @Override
    public ServerResponse deleteCategory(List<Long> ids) {
        RedisUtil.delete(SystemConstant.CATE_KEY);
        categoryMapper.deleteBatchIds(ids);
        return ServerResponse.success();
    }

    @Override
    public ServerResponse updateCategory(CategoryParam categoryParam) {

        Category category = categoryParam.getCategory();
        categoryMapper.updateById(category);

        String categoryName = category.getCategoryName();
        String oldCategoryName = category.getOldCategoryName();
        if (!categoryName.equals(oldCategoryName)){
            goodsCommonMapper.updateCategory(oldCategoryName, categoryName);
        }
        RedisUtil.delete(SystemConstant.CATE_KEY);

        if(categoryParam.getRelateFlag()==SystemConstant.NEED_UPDATE_CHILDS){
            String ids = categoryParam.getIds();
            if (StringUtils.isNotEmpty(ids)){
                Category cate = new Category();
                cate.setTypeId(category.getTypeId());
                cate.setTypeName(category.getTypeName());
                QueryWrapper<Category> categoryQueryWrapper = new QueryWrapper<>();
                String[] idArr = ids.split(",");
                List<Long> idList = Arrays.stream(idArr).map(x -> Long.parseLong(x)).collect(Collectors.toList());
                categoryQueryWrapper.in("id",idList);
            categoryMapper.update(cate,categoryQueryWrapper);
            }
        }

        return ServerResponse.success();
    }
}
