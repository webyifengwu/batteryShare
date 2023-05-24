package com.jee.demo.mapper;

import com.jee.demo.domain.User;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserMapper {
    /**
     * 根据用户ID获取用户信息
     *
     * @param userId 用户ID
     * @return 用户对象
     */
    User getUserById(String userId);

    /**
     * 根据用户名和密码获取用户信息
     *
     * @param username 用户名
     * @param password 密码
     * @return 用户对象
     */
    User getUserByUser(@Param("username") String username, @Param("password") String password);

    /**
     * 更新用户登录时间
     *
     * @param userId 用户ID
     */
    void updateLoginTime(String userId);

    /**
     * 插入用户信息
     *
     * @param user 用户对象
     */
    void insertUser(User user);

    /**
     * 删除用户
     *
     * @param userId 用户ID
     */
    @Delete("delete from user where user_id=#{userId}")
    void deleteUser(String userId);

    /**
     * 更新用户信息
     *
     * @param user 用户对象
     */
    void updateUser(User user);

    /**
     * 检查用户名是否唯一
     *
     * @param username 用户名
     * @return 唯一性检查结果，1表示唯一，0表示不唯一
     */
    Integer checkUsernameIsUnique(String username);

    /**
     * 根据条件查询用户列表
     *
     * @param username 用户名，用于模糊查询用户列表
     * @param role     用户角色
     * @return 用户列表
     */
    List<User> queryUserList(@Param("username") String username, @Param("role") Integer role);

    /**
     * 根据用户名获取用户信息
     *
     * @param username 用户名
     * @return 用户对象
     */
    User getUserByUsername(String username);

    /**
     * 前台注册用户
     * @param user
     */
    void registerUser(User user);
}
