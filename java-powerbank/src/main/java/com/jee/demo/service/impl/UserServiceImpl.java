package com.jee.demo.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jee.demo.domain.Result;
import com.jee.demo.domain.User;
import com.jee.demo.domain.dto.UserDto;
import com.jee.demo.mapper.UserMapper;
import com.jee.demo.service.UserService;
import com.jee.demo.utils.JwtUtil;
import com.jee.demo.utils.ResultUtil;
import com.jee.demo.utils.SimulationUtil;
import com.jee.demo.utils.ThreadLocalUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Random;
import java.util.UUID;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public User login(String username, String password) {
        User user = userMapper.getUserByUser(username, password);
        //验证成功则更新登录时间
        if (user != null)
            userMapper.updateLoginTime(user.getUserId());
        return user;
    }

    @Override
    public User getUserById(String userId) {
        User user = userMapper.getUserById(userId);
        if (user != null) userMapper.updateLoginTime(user.getUserId());
        return user;
    }

    @Override
    public Result insertUser(User user) {
        user.setUserId(UUID.randomUUID().toString());
        userMapper.insertUser(user);
        return ResultUtil.success("新增用户成功");
    }

    @Override
    public Result deleteUser(String userId) {
        userMapper.deleteUser(userId);
        return ResultUtil.success("删除用户成功");
    }

    @Override
    public Result updateUser(User user) {
        userMapper.updateUser(user);
        return ResultUtil.success("更新用户成功");
    }

    @Override
    public boolean checkUsernameIsUnique(String username, String userId) {
        //新增操作
        if (userId == null) {
            return userMapper.checkUsernameIsUnique(username) > 0;
        }
        //更新操作
        //根据用户名查询用户
        User user = userMapper.getUserByUsername(username);
        //用户不存在
        if (user == null) {
            return false;
        }
        //判断用户名重复
        if (userId.equals(user.getUserId())) {
            return false;
        }
        return userMapper.checkUsernameIsUnique(username) > 0;
    }

    @Override
    public Result queryUserList(Integer pageNum, Integer pageSize, String username, Integer role) {
        PageHelper.startPage(pageNum, pageSize);
        List<User> userList = userMapper.queryUserList(username, role);
        PageInfo<User> pageInfo = new PageInfo<>(userList, 5);
        return ResultUtil.success("查询成功", pageInfo);
    }

    @Override
    public Result registerUser(User user) throws UnsupportedEncodingException {
        user.setUserId(UUID.randomUUID().toString());
        userMapper.registerUser(user);
        //登录成功则调用JWTUtil类的创建Token方法返回客户端
        String token = JwtUtil.createToken(user);
        //数据处理
        UserDto userDto=new UserDto();
        BeanUtils.copyProperties(user,userDto);
        userDto.setToken(token);
        userDto.setZone(SimulationUtil.randomZone());
        userDto.setPassword(null);
        return ResultUtil.success("注册成功，欢迎您初次登录", userDto);
    }

    @Override
    public Result updatePassword(String oldPwd, String newPwd) {
        String userId = ThreadLocalUtil.getUser();
        User user = userMapper.getUserById(userId);
        if(!oldPwd.equals(user.getPassword())){
            return ResultUtil.error(504,"原密码有误");
        }
        user.setPassword(newPwd);
        userMapper.updateUser(user);
        return ResultUtil.success("更改密码成功");
    }


}
