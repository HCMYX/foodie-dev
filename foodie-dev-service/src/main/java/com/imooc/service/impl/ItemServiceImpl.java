package com.imooc.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.imooc.enums.CommentLevel;
import com.imooc.enums.YesOrNo;
import com.imooc.mapper.*;
import com.imooc.pojo.*;
import com.imooc.pojo.vo.CommentLevelCountsVo;
import com.imooc.pojo.vo.ItemCommentVo;
import com.imooc.pojo.vo.SearchItemsVo;
import com.imooc.pojo.vo.ShopcartVO;
import com.imooc.service.ItemService;
import com.imooc.utils.DesensitizationUtil;
import com.imooc.utils.PagedGridResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ItemServiceImpl implements ItemService {

    @Autowired
    private ItemsMapper itemsMapper;

    @Autowired
    private ItemsImgMapper itemsImgMapper;

    @Autowired
    private ItemsSpecMapper itemsSpecMapper;

    @Autowired
    private ItemsParamMapper itemsParamMapper;

    @Autowired
    private ItemsCommentsMapper itemsCommentsMapper;

    @Autowired
    private ItemsMapperCustom itemsMapperCustom;
    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public Items queryItemById(String itemId) {
        return itemsMapper.selectByPrimaryKey(itemId);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public List<ItemsImg> queryItemImgList(String itemId) {
        Example example = new Example(ItemsImg.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("itemId",itemId);
        return itemsImgMapper.selectByExample(example);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public List<ItemsSpec> queryItemSpecList(String itemId) {
        Example example = new Example(ItemsSpec.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("itemId",itemId);
        return itemsSpecMapper.selectByExample(example);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public ItemsParam queryItemParamList(String itemId) {
        Example example = new Example(ItemsParam.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("itemId",itemId);
        return itemsParamMapper.selectOneByExample(example);
    }

    @Override
    public CommentLevelCountsVo queryCommentCounts(String itemId) {
        CommentLevelCountsVo vo = new CommentLevelCountsVo();
        vo.setGoodCounts(getCommentCounts(itemId, CommentLevel.GOOD.type));
        vo.setNormalCounts(getCommentCounts(itemId,CommentLevel.NORMAL.type));
        vo.setBadCounts(getCommentCounts(itemId,CommentLevel.BAD.type));
        vo.setTotalCounts(vo.getGoodCounts() + vo.getNormalCounts() + vo.getBadCounts());
        return vo;
    }

    Integer getCommentCounts(String itemId,Integer level){
        ItemsComments comments = new ItemsComments();
        comments.setItemId(itemId);
        comments.setCommentLevel(level);
        return itemsCommentsMapper.selectCount(comments);
    }

    @Override
    public PagedGridResult queryPagedComments(String itemId, Integer level,Integer pageSize,Integer page) {
        Map<String,Object> map = new HashMap<>();
        map.put("itemId",itemId);
        map.put("level",level);
        PageHelper.startPage(page,pageSize);
        List<ItemCommentVo> list = itemsMapperCustom.queryItemComments(map);
        for (ItemCommentVo vo : list){
            vo.setNickname(DesensitizationUtil.commonDisplay(vo.getNickname()));
        }
        return setterPagedGrid(list,page);
    }


    @Override
    public PagedGridResult searchItems(String keywords, String sort, Integer pageSize, Integer page) {
        Map<String,Object> map = new HashMap<>();
        map.put("keywords",keywords);
        map.put("sort",sort);
        PageHelper.startPage(page,pageSize);
        List<SearchItemsVo> list = itemsMapperCustom.searchItems(map);

        return setterPagedGrid(list,page);
    }

    @Override
    public PagedGridResult searchItemsByThirdCat(Integer catId, String sort, Integer pageSize, Integer page) {
        Map<String,Object> map = new HashMap<>();
        map.put("catId",catId);
        map.put("sort",sort);
        PageHelper.startPage(page,pageSize);
        List<SearchItemsVo> list = itemsMapperCustom.searchItemsByThirdCat(map);

        return setterPagedGrid(list,page);
    }
    private PagedGridResult setterPagedGrid(List<?> list,Integer page){
        PagedGridResult grid = new PagedGridResult();
        grid.setRows(list);
        grid.setPage(page);
        PageInfo<?> pageList = new PageInfo<>(list);
        grid.setRecords(pageList.getTotal());
        grid.setTotal(pageList.getPages());
        return grid;
    }

    /**
     * 根据规格id查询购物车
     * @param specIds
     * @return
     */
    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public List<ShopcartVO> queryItemsBySpecIds(String specIds) {
        List<String> specIdsList = Arrays.asList(specIds.split(","));
        return itemsMapperCustom.queryItemsBySpecIds(specIdsList);
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public ItemsSpec queryItemSpecById(String specId) {
        return itemsSpecMapper.selectByPrimaryKey(specId);
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public String queryItemMainImgById(String itemId) {
        ItemsImg itemsImg = new ItemsImg();
        itemsImg.setItemId(itemId);
        itemsImg.setIsMain(YesOrNo.YES.type);
        ItemsImg result = itemsImgMapper.selectOne(itemsImg);
        return result != null ? result.getUrl() : "";
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void decreaseItemSpecStock(String specId, Integer buyCounts) {
        // synchronized 不推荐使用，集群下无用，性能低下
        // 锁数据库，不推荐，导致数据库性能低下
        // 分布式锁 zookeeper redis
        // lockUtil.getLock(); // 加锁
        // 1.查询库存
        // 2.判断库存，是否够
        ItemsSpec itemsSpec = itemsSpecMapper.selectByPrimaryKey(specId);
        int stock = itemsSpec.getStock() - buyCounts;
        if (stock < 0){
            throw new RuntimeException("库存不足");
        }
        itemsSpec.setStock(stock);
        itemsSpecMapper.updateByPrimaryKeySelective(itemsSpec);
        // lockUtil.unLock(); // 解锁
    }
}
