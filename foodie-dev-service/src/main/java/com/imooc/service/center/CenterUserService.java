package com.imooc.service.center;

import com.imooc.pojo.Users;
import com.imooc.pojo.bo.UserBO;
import com.imooc.pojo.bo.center.CenterUserBO;

public interface CenterUserService {

    /**
     * 检索用户名和密码是否匹配，用于登录
     */
    public Users userInfo(String userId);

    public Users updateUserInfo(String userId, CenterUserBO userBO);
}