package com.imooc.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.imooc.enums.CommentLevel;
import com.imooc.enums.YesOrNo;
import com.imooc.mapper.*;
import com.imooc.pojo.*;
import com.imooc.pojo.bo.AddressBO;
import com.imooc.pojo.vo.CommentLevelCountsVo;
import com.imooc.pojo.vo.ItemCommentVo;
import com.imooc.pojo.vo.SearchItemsVo;
import com.imooc.pojo.vo.ShopcartVO;
import com.imooc.service.AddressService;
import com.imooc.service.ItemService;
import com.imooc.utils.DesensitizationUtil;
import com.imooc.utils.PagedGridResult;
import org.n3r.idworker.Sid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.*;

@Service
public class AddressServiceImpl implements AddressService {
    @Autowired
    private UserAddressMapper userAddressMapper;
    @Autowired
    private Sid sid;

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public List<UserAddress> queryAll(String userId) {
        UserAddress userAddress = new UserAddress();
        userAddress.setUserId(userId);
        return userAddressMapper.select(userAddress);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void addNewUserAddress(AddressBO addressBO) {
        Integer isDefault = 0;
        List<UserAddress> addressList = this.queryAll(addressBO.getUserId());
        if (addressList == null || addressList.isEmpty() || addressList.size() == 0) {
            isDefault = 1;
        }
        String addressId = sid.nextShort();
        UserAddress newAddress = new UserAddress();
        BeanUtils.copyProperties(addressBO, newAddress);
        newAddress.setId(addressId);
        newAddress.setUserId(addressBO.getUserId());
        newAddress.setIsDefault(isDefault);
        newAddress.setReceiver(addressBO.getReceiver());
        newAddress.setCreatedTime(new Date());
        newAddress.setUpdatedTime(new Date());
        userAddressMapper.insert(newAddress);
    }
    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void updateUserAddress(AddressBO addressBO) {
        String addressId = addressBO.getAddressId();
        UserAddress pendingAddress = new UserAddress();
        BeanUtils.copyProperties(addressBO, pendingAddress);
        pendingAddress.setId(addressId);
        userAddressMapper.updateByPrimaryKeySelective(pendingAddress);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void deleteUserAddress(String userId, String addressId) {
        UserAddress address = new UserAddress();
        address.setId(addressId);
        address.setUserId(userId);
        userAddressMapper.delete(address);
    }
    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void updateUserAddressToBeDefault(String userId, String addressId) {
        UserAddress userAddress = new UserAddress();
        userAddress.setIsDefault(YesOrNo.YES.type);
        userAddress.setUserId(userId);
        List<UserAddress> addressList = userAddressMapper.select(userAddress);
        for (UserAddress address : addressList) {
            address.setIsDefault(YesOrNo.NO.type);
            userAddressMapper.updateByPrimaryKeySelective(address);
        }
        UserAddress pendingAddress = new UserAddress();
        pendingAddress.setId(addressId);
        pendingAddress.setIsDefault(YesOrNo.YES.type);
        userAddressMapper.updateByPrimaryKeySelective(pendingAddress);
    }
}
