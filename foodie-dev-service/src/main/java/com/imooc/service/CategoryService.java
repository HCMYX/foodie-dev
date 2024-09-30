package com.imooc.service;

import com.imooc.pojo.Category;
import com.imooc.pojo.vo.CategoryVo;
import com.imooc.pojo.vo.NewItemsVo;

import java.util.List;

public interface CategoryService {

    /**
     * 查询所有一级分类
     * @return
     */
    public List<Category> queryAllRootLevelCar ();

    public List<CategoryVo> getSubCatList(Integer rootCatId);

    public List<NewItemsVo> getSixNewItemsLazy(Integer rootCatId);
}
