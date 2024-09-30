package com.imooc.service;

import com.imooc.pojo.Users;
import com.imooc.pojo.bo.UserBO;

public interface UserService {
    /**
     * 判断用户是否存在
     */
    public boolean queryUsernameIsExist(String username);

    public Users createUser(UserBO usersBO);

    /**
     * 检索用户名和密码是否匹配，用于登录
     * @param username
     * @param password
     * @return
     */
    public Users queryUserForLogin(String username,String password);
    public Users updateUserInfo(UserBO userBO);



}
