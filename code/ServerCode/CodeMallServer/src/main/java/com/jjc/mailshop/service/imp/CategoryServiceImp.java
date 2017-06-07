package com.jjc.mailshop.service.imp;

import com.jjc.mailshop.common.ServerResponse;
import com.jjc.mailshop.dao.CategoryMapper;
import com.jjc.mailshop.pojo.Category;
import com.jjc.mailshop.service.ICategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by jjc on 2017/6/3.
 */
@Service(value = "iCategoryService")
public class CategoryServiceImp implements ICategoryService {
    @Autowired
    CategoryMapper mCategoryMapper;

    @Override
    public ServerResponse<List<Category>> getCategoryByParentId(String parentId) {
        //根据id查询数据
        List<Category> categories = mCategoryMapper.selectCategoryByParentId(Integer.parseInt(parentId));
        //判断数据
        if (categories == null) {
            return ServerResponse.createBySuccessMessage("没有相关数据");
        }

        //返回数据
        return ServerResponse.createBySuccess("查询成功", categories);
    }

    @Override
    public ServerResponse<String> addCategory(String parentId, String categoryName) {
        //？需不需要检查是否已经存在了这个名词
        Category category = new Category();
        category.setParentId(Integer.parseInt(parentId));
        category.setName(categoryName);

        //插入数据 影响行数大于0 代表成功
        if (mCategoryMapper.insertSelective(category) > 0) {
            return ServerResponse.createBySuccessMessage("新增成功");
        }
        //返回失败结果
        return ServerResponse.createByErrorMessage("新增失败");
    }

    @Override
    public ServerResponse<String> updateCategory(String categoryId, String categoryName) {

        //查询是否存在
        if (mCategoryMapper.selectByPrimaryKey(Integer.parseInt(categoryId)) == null) {
            return ServerResponse.createByErrorMessage("修改失败，品类不存在");
        }
        //创建对象 赋值数据
        Category category = new Category();
        category.setId(Integer.parseInt(categoryId));
        category.setName(categoryName);
        //修改数据 影响行数大于0 代表成功
        if (mCategoryMapper.updateByPrimaryKeySelective(category) > 0) {
            return ServerResponse.createBySuccessMessage("修改成功");
        }
        //返回失败结果
        return ServerResponse.createByErrorMessage("修改失败");
    }
}
