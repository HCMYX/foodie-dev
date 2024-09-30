package com.imooc.service.impl.center;

import com.imooc.mapper.UsersMapper;
import com.imooc.pojo.Users;
import com.imooc.service.center.CenterUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}
