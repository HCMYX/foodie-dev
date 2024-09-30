package com.imooc.service.center;

import com.imooc.pojo.Users;

public interface CenterUserService {

    /**
     * 检索用户名和密码是否匹配，用于登录
     */
    public Users userInfo(String userId);

    public void updateUserInfo(String userId, UserBO userBO);
}
