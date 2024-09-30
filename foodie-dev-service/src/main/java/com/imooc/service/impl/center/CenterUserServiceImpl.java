package com.imooc.service.impl.center;

import com.imooc.mapper.UsersMapper;
import com.imooc.pojo.Users;
import com.imooc.pojo.bo.UserBO;
import com.imooc.pojo.bo.center.CenterUserBO;
import com.imooc.service.center.CenterUserService;
import org.apache.catalina.User;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.beans.BeanProperty;
import java.util.Date;

@Service
public class CenterUserServiceImpl implements CenterUserService {

    @Autowired
    private UsersMapper usersMapper;

    /**
     * 检索用户名和密码是否匹配，用于登录
     */
    @Override
    public Users userInfo(String userId) {
        return usersMapper.selectByPrimaryKey(userId);
    }

    @Override
    public Users updateUserInfo(String userId, CenterUserBO userBO) {
        Users updateUser = new Users();
        BeanUtils.copyProperties(userBO, updateUser);
        updateUser.setId(userId);
        updateUser.setUpdatedTime(new Date());
        usersMapper.updateByPrimaryKeySelective(updateUser);
        return userInfo(userId);
    }
}
