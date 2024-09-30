package com.imooc.service;

import com.imooc.pojo.*;
import com.imooc.pojo.bo.UserBO;
import com.imooc.pojo.vo.CommentLevelCountsVo;
import com.imooc.pojo.vo.ItemCommentVo;
import com.imooc.pojo.vo.SearchItemsVo;
import com.imooc.pojo.vo.ShopcartVO;
import com.imooc.utils.PagedGridResult;

import java.util.List;

public interface ItemService {

    /**
     * 根据商品id查询详情
     * @param itemId
     * @return
     */
   public Items queryItemById(String itemId);

    /**
     * 根据商品id查询商品图片
     * @param itemId
     * @return
     */
   public List<ItemsImg> queryItemImgList(String itemId);

    /**
     * 根据商品id查询商品规格
     * @param itemId
     * @return
     */
    public List<ItemsSpec> queryItemSpecList(String itemId);

    /**
     * 根据商品id查询商品参数
     * @param itemId
     * @return
     */
    public ItemsParam queryItemParamList(String itemId);

    /**
     * 根据商品id查询商品评价数量
     * @param itemId
     */
    public CommentLevelCountsVo queryCommentCounts(String itemId);

    /**
     * 根据商品id查询商品的评价
     * @param itemId
     * @param level
     * @return
     */
    public PagedGridResult queryPagedComments(String itemId, Integer level, Integer pageSize, Integer page);

    public PagedGridResult searchItems(String keywords, String sort, Integer pageSize, Integer page);

    public PagedGridResult searchItemsByThirdCat(Integer catId, String sort, Integer pageSize, Integer page);

    /**
     * 根据规格ids查询最新的购物车中商品信息
     * @param specIds
     * @return
     */
    public List<ShopcartVO> queryItemsBySpecIds(String specIds);
    public ItemsSpec queryItemSpecById(String specId);

    public String queryItemMainImgById(String itemId);

    public void decreaseItemSpecStock(String specId, Integer buyCounts);
}
