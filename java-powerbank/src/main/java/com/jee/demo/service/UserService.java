package com.jee.demo.service;

import com.github.pagehelper.PageInfo;
import com.jee.demo.domain.Result;
import com.jee.demo.domain.User;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;


public interface UserService {

    /**
     * 用户登录
     * @param username 用户名
     * @param password 密码
     * @return 登录成功的用户对象
     */
    User login(String username, String password);

    /**
     * 根据用户名获取用户信息
     * @param userId 用户名
     * @return 用户对象
     */
    User getUserById(String userId);

    /**
     * 插入用户
     * @param user 用户对象
     * @return 操作结果
     */
    Result insertUser(User user);

    /**
     * 删除用户
     * @param userId 用户ID
     * @return 操作结果
     */
    Result deleteUser(String userId);

    /**
     * 更新用户信息
     * @param user 用户对象
     * @return 操作结果
     */
    Result updateUser(User user);

    /**
     * 检查用户名是否唯一
     * @param username 用户名
     * @param userId 用户ID（可选，用于排除当前用户）
     * @return 是否唯一
     */
    boolean checkUsernameIsUnique(String username, String userId);

    /**
     * 查询用户列表
     * @param pageNum 当前页码
     * @param pageSize 每页大小
     * @param username 用户名（可选）
     * @param role 角色（可选）
     * @return 用户列表结果
     */
    Result queryUserList(Integer pageNum, Integer pageSize, String username, Integer role);

    /**
     * 前台用户注册
     * @param user
     * @return
     * @throws UnsupportedEncodingException
     */
    Result registerUser(User user) throws UnsupportedEncodingException;

    /**
     * 前台更新密码
     * @param oldPwd
     * @param newPwd
     * @return
     */
    Result updatePassword(String oldPwd, String newPwd);
}
