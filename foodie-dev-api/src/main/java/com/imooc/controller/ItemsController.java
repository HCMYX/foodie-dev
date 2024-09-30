package com.imooc.controller;

import com.github.pagehelper.PageHelper;
import com.imooc.enums.YesOrNo;
import com.imooc.pojo.*;
import com.imooc.pojo.vo.CommentLevelCountsVo;
import com.imooc.pojo.vo.ItemInfoVo;
import com.imooc.pojo.vo.ShopcartVO;
import com.imooc.service.CarouselService;
import com.imooc.service.CategoryService;
import com.imooc.service.ItemService;
import com.imooc.utils.IMOOCJSONResult;
import com.imooc.utils.PagedGridResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(value = "商品接口", tags = {"商品信息展示相关接口"})
@RestController
@RequestMapping("items")
public class ItemsController extends BaseController {

    @Autowired
    private ItemService itemService;

    @ApiOperation(value = "查询商品详情", notes = "查询商品详情" ,httpMethod = "GET")
    @GetMapping("/info/{itemId}")
    public IMOOCJSONResult info(@ApiParam(name = "itemId",value = "商品id",required = true)
                                  @PathVariable String itemId){
        if (StringUtils.isBlank(itemId)){
            return IMOOCJSONResult.errorMsg("请求参数不能为空");
        }
        Items items = itemService.queryItemById(itemId);
        List<ItemsImg> itemsImgs = itemService.queryItemImgList(itemId);
        ItemsParam itemsParams= itemService.queryItemParamList(itemId);
        List<ItemsSpec> itemsSpec = itemService.queryItemSpecList(itemId);
        ItemInfoVo itemInfoVo = new ItemInfoVo(items,itemsImgs,itemsParams,itemsSpec);
        return IMOOCJSONResult.ok(itemInfoVo);
    }

    @ApiOperation(value = "查询商品评价等级", notes = "查询商品评价等级" ,httpMethod = "GET")
    @GetMapping("/commentLevel")
    public IMOOCJSONResult commentLevel(@ApiParam(name = "itemId",value = "商品id",required = true)
                                  @RequestParam String itemId){
        if (StringUtils.isBlank(itemId)){
            return IMOOCJSONResult.errorMsg("请求参数不能为空");
        }
        CommentLevelCountsVo vo = itemService.queryCommentCounts(itemId);
        return IMOOCJSONResult.ok(vo);
    }
    @ApiOperation(value = "查询商品评价", notes = "查询商品评价" ,httpMethod = "GET")
    @GetMapping("/comments")
    public IMOOCJSONResult comments(@ApiParam(name = "itemId",value = "商品id",required = true)
                                  @RequestParam String itemId,
                                  @ApiParam(name = "level",value = "评价等级",required = false)
                                  @RequestParam Integer level,
                                  @ApiParam(name = "page",value = "页数",required = false)
                                  @RequestParam Integer page,
                                  @ApiParam(name = "pageSize",value = "每页条数",required = false)
                                  @RequestParam Integer pageSize){
        if (StringUtils.isBlank(itemId)){
            return IMOOCJSONResult.errorMsg("请求参数不能为空");
        }
        if (page == null){
            page = 1;
        }
        if (pageSize == null){
            pageSize = COMMON_PAGE_SIZE;
        }
        PagedGridResult gridResult = itemService.queryPagedComments(itemId,level,pageSize,page);
        return IMOOCJSONResult.ok(gridResult);
    }
    @ApiOperation(value = "搜索商品列表", notes = "搜索商品列表" ,httpMethod = "GET")
    @GetMapping("/search")
    public IMOOCJSONResult search(
            @ApiParam(name = "keywords",value = "关键字",required = true)
            @RequestParam String keywords,
            @ApiParam(name = "sort",value = "排序",required = false)
            @RequestParam String sort,
            @ApiParam(name = "page",value = "页数",required = false)
            @RequestParam Integer page,
            @ApiParam(name = "pageSize",value = "每页条数",required = false)
            @RequestParam Integer pageSize){
        if (StringUtils.isBlank(keywords)){
            return IMOOCJSONResult.errorMsg("请求参数不能为空");
        }
        if (page == null){
            page = 1;
        }
        if (pageSize == null){
            pageSize = COMMON_PAGE_SIZE;
        }
        PagedGridResult gridResult = itemService.searchItems(keywords,sort,pageSize,page);
        return IMOOCJSONResult.ok(gridResult);
    }
    @ApiOperation(value = "通过三级分类id查询商品列表", notes = "通过三级分类id查询商品列表" ,httpMethod = "GET")
    @GetMapping("/catItems")
    public IMOOCJSONResult catItems(
            @ApiParam(name = "catId",value = "三级分类id",required = true)
            @RequestParam Integer catId,
            @ApiParam(name = "sort",value = "排序",required = false)
            @RequestParam String sort,
            @ApiParam(name = "page",value = "页数",required = false)
            @RequestParam Integer page,
            @ApiParam(name = "pageSize",value = "每页条数",required = false)
            @RequestParam Integer pageSize){
        if (catId == null){
            return IMOOCJSONResult.errorMsg("请求参数不能为空");
        }
        if (page == null){
            page = 1;
        }
        if (pageSize == null){
            pageSize = COMMON_PAGE_SIZE;
        }
        PagedGridResult gridResult = itemService.searchItemsByThirdCat(catId,sort,pageSize,page);
        return IMOOCJSONResult.ok(gridResult);
    }

    @ApiOperation(value = "根据商品规格ids查找最新的商品数据", notes = "根据商品规格ids查找最新的商品数据" ,httpMethod = "GET")
    @GetMapping("/refresh")
    public IMOOCJSONResult refresh(
            @ApiParam(name = "itemSpecIds",value = "商品规格ids",required = true,example = "1001,1002")
            @RequestParam String itemSpecIds){
        if (StringUtils.isBlank(itemSpecIds)){
            return IMOOCJSONResult.errorMsg("请求参数不能为空");
        }
        List<ShopcartVO> shopcartVOS = itemService.queryItemsBySpecIds(itemSpecIds);
        return IMOOCJSONResult.ok(shopcartVOS);
    }
}
