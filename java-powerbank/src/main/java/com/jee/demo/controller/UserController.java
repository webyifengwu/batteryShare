package com.jee.demo.controller;

import com.jee.demo.domain.Result;
import com.jee.demo.domain.User;
import com.jee.demo.domain.dto.UserDto;
import com.jee.demo.service.UserService;
import com.jee.demo.utils.JwtUtil;
import com.jee.demo.utils.ResultUtil;
import com.jee.demo.utils.SimulationUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;
import java.util.Map;

@RestController
@RequestMapping("/user")
@CrossOrigin(origins = {"http://localhost:8080", "http://localhost:8081"})
public class UserController {
    @Autowired
    private UserService userService;

    /**
     * 登录功能
     *
     * @param user
     * @param from 判断是后台还是前台
     * @return
     * @throws UnsupportedEncodingException
     */
    @PostMapping("/login")
    public Result login(@RequestBody User user, @RequestParam(required = false) String from) throws UnsupportedEncodingException {
        User u = userService.login(user.getUsername(), user.getPassword());
        //账号密码错误
        if (u == null) {
            return ResultUtil.error(302, "用户名或密码错误");
        }
        //判断是否为普通用户登录后台，若是返回error
        if ("Backend".equalsIgnoreCase(from) && u.getRole() == 0) {
            return ResultUtil.error(303, "您无权限进入后台！");
        }
        //登录成功则调用JWTUtil类的创建Token方法返回客户端
        String token = JwtUtil.createToken(u);
        //数据处理
        UserDto userDto=new UserDto();
        BeanUtils.copyProperties(u,userDto);
        userDto.setToken(token);
        userDto.setZone(SimulationUtil.randomZone());
        userDto.setPassword(null);
        if (u.getLastLogin() != null) {
            return ResultUtil.success("您上次登录时间为:" + u.getLastLogin(), userDto);
        }
        return ResultUtil.success("欢迎您初次登录", userDto);
    }

    /**
     * 使用token完成免密登录
     *
     * @param map
     * @return
     */
    @PostMapping("/login/np")
    public Result loginNoPwd(@RequestBody Map<String, String> map, @RequestParam(required = false) String from) {
        String token = map.get("token");
        if (!JwtUtil.verify(token)) {
            return ResultUtil.error(300, "token已失效");
        }
        System.out.println(token);
        String userId = JwtUtil.getUserId(token);
        User u = userService.getUserById(userId);
        //隐私处理
        UserDto userDto = new UserDto();
        BeanUtils.copyProperties(u, userDto);
        userDto.setZone(SimulationUtil.randomZone());
        userDto.setPassword(null);
        if ("Backend".equalsIgnoreCase(from) && u.getRole() == 0) {
            return ResultUtil.error(303, "您无权限进入后台！");
        }
        return ResultUtil.success("免密登录成功,您上次登录时间为:" + u.getLastLogin(), userDto);
    }
    /**
     * 查询用户列表
     * @param pageNum 当前页码
     * @param pageSize 每页大小
     * @param role 用户角色（可选）
     * @param username 用户名（可选）
     * @return 用户列表结果
     */
    @GetMapping
    public Result queryUserList(@RequestParam(defaultValue = "1") Integer pageNum, @RequestParam(defaultValue = "10") Integer pageSize,
                                @RequestParam(required = false) Integer role, @RequestParam(required = false) String username) {
        return userService.queryUserList(pageNum, pageSize, username, role);
    }

    /**
     * 新增用户
     * @param user 用户对象
     * @return 操作结果
     */
    @PostMapping
    public Result insertUser(@RequestBody User user) {
        return userService.insertUser(user);
    }

    /**
     * 前台注册
     * @param user
     * @return
     */
    @PostMapping("/forward")
    public Result registerUser(@RequestBody User user) throws UnsupportedEncodingException {
        return userService.registerUser(user);
    }

    /**
     * 检查用户名是否唯一
     * @param username 用户名
     * @param userId 用户ID（可选）
     * @return 操作结果
     */
    @GetMapping("/check/{username}")
    public Result checkUsernameIsUnique(@PathVariable String username, @RequestParam(required = false) String userId) {
        if (userService.checkUsernameIsUnique(username, userId)) {
            return ResultUtil.error(301, "该用户名已存在");
        }
        return ResultUtil.success();
    }

    /**
     * 删除用户
     * @param userId 用户ID
     * @return 操作结果
     */
    @DeleteMapping("/{userId}")
    public Result deleteUser(@PathVariable String userId) {
        return userService.deleteUser(userId);
    }

    /**
     * 更新用户
     * @param user 用户对象
     * @return 操作结果
     */
    @PutMapping
    public Result updateUser(@RequestBody User user) {
        return userService.updateUser(user);
    }

    /**
     * 前台更新密码专用接口
     * @param map
     * @return
     */
    @PutMapping("/pwd")
    public Result updatePassword(@RequestBody Map<String,String> map){
        String oldPwd = map.get("oldPwd");
        String newPwd = map.get("newPwd");
        return userService.updatePassword(oldPwd,newPwd);
    }

}
