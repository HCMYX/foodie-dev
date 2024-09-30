package com.imooc.pojo.vo;

import com.imooc.pojo.Items;
import com.imooc.pojo.ItemsImg;
import com.imooc.pojo.ItemsParam;
import com.imooc.pojo.ItemsSpec;

import java.util.List;

/**
 * 最新商品vo
 */
public class ItemInfoVo {
    private Items item;
    private List<ItemsImg> itemImgList;
    private ItemsParam itemParams;
    private List<ItemsSpec> itemSpecList;

    public ItemInfoVo() {
    }

    public ItemInfoVo(Items item, List<ItemsImg> itemImgList, ItemsParam itemParams, List<ItemsSpec> itemSpecList) {
        this.item = item;
        this.itemImgList = itemImgList;
        this.itemParams = itemParams;
        this.itemSpecList = itemSpecList;
    }

    public Items getItem() {
        return item;
    }

    public void setItem(Items item) {
        this.item = item;
    }

    public List<ItemsImg> getItemImgList() {
        return itemImgList;
    }

    public void setItemImgList(List<ItemsImg> itemImgList) {
        this.itemImgList = itemImgList;
    }

    public ItemsParam getItemParams() {
        return itemParams;
    }

    public void setItemParams(ItemsParam itemParams) {
        this.itemParams = itemParams;
    }

    public List<ItemsSpec> getItemSpecList() {
        return itemSpecList;
    }

    public void setItemSpecList(List<ItemsSpec> itemSpecList) {
        this.itemSpecList = itemSpecList;
    }
}
