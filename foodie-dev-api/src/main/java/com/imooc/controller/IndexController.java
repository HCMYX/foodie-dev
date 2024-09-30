package com.imooc.controller;

import com.imooc.enums.YesOrNo;
import com.imooc.pojo.Carousel;
import com.imooc.pojo.Category;
import com.imooc.pojo.vo.CategoryVo;
import com.imooc.pojo.vo.NewItemsVo;
import com.imooc.service.CarouselService;
import com.imooc.service.CategoryService;
import com.imooc.utils.IMOOCJSONResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(value = "首页", tags = {"首页相关接口"})
@RestController
@RequestMapping("index")
public class IndexController {

    @Autowired
    private CarouselService carouselService;

    @Autowired
    private CategoryService categoryService;

    @ApiOperation(value = "获取首页轮播图列表", notes = "获取首页轮播图列表" ,httpMethod = "GET")
    @GetMapping("/carousel")
    public IMOOCJSONResult carousel(){
        List<Carousel> list = carouselService.queryAll(YesOrNo.YES.type);
        return IMOOCJSONResult.ok(list);
    }

    @ApiOperation(value = "获取上班分类（一级分类）", notes = "获取上班分类（一级分类）" ,httpMethod = "GET")
    @GetMapping("/cats")
    public IMOOCJSONResult cats(){
        List<Category> list = categoryService.queryAllRootLevelCar();
        return IMOOCJSONResult.ok(list);
    }
    @ApiOperation(value = "获取商品子分类", notes = "获取商品子分类" ,httpMethod = "GET")
    @GetMapping("/subCat/{rootCatId}")
    public IMOOCJSONResult subCat(
            @ApiParam(name = "rootCatId",value = "一级分类id",required = true)
            @PathVariable Integer rootCatId){
        if (null == rootCatId){
            return IMOOCJSONResult.errorMsg("请求参数不能为空");
        }
        List<CategoryVo> list = categoryService.getSubCatList(rootCatId);
        return IMOOCJSONResult.ok(list);
    }

    @ApiOperation(value = "查询每个一级分类下的最新6条商品", notes = "查询每个一级分类下的最新6条商品" ,httpMethod = "GET")
    @GetMapping("/sixNewItems/{rootCatId}")
    public IMOOCJSONResult sixNewItems(@PathVariable Integer rootCatId){

        if (null == rootCatId){
            return IMOOCJSONResult.errorMsg("请求参数不能为空");
        }
        List<NewItemsVo> list = categoryService.getSixNewItemsLazy(rootCatId);
        return IMOOCJSONResult.ok(list);
    }
}
