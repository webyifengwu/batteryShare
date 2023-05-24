package com.jee.demo.controller;

import com.github.pagehelper.PageInfo;
import com.jee.demo.domain.Order;
import com.jee.demo.domain.Result;
import com.jee.demo.domain.dto.OrderDto;
import com.jee.demo.service.OrderService;
import com.jee.demo.utils.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/order")
@CrossOrigin(origins = {"http://localhost:8080","http://localhost:8081"})
public class OrderController {

    @Autowired
    private OrderService orderService;
    /**
     * 查询订单列表
     * @param pageNum 当前页码
     * @param pageSize 每页大小
     * @param username 用户名（可选）
     * @param status 订单状态（可选）
     * @return 订单列表结果
     */
    @GetMapping
    public Result queryOrderList(@RequestParam(defaultValue = "1") Integer pageNum,
                                 @RequestParam(defaultValue = "10") Integer pageSize,
                                 @RequestParam(required = false) String username,
                                 @RequestParam(required = false) Integer status) {
        return orderService.queryOrderList(username, status, pageNum, pageSize);
    }

    /**
     * 根据用户ID和订单状态查询订单
     * @param status 订单状态
     * @param userId 用户ID
     * @return 订单结果
     */
    @GetMapping("/forward")
    public Result getOrderByUserAndId(@RequestParam Integer status, @RequestParam String userId) {
        return orderService.getOrderByUserAndId(status, userId);
    }

    /**
     * 新增订单
     * @param order 订单对象
     * @return 操作结果
     */
    @PostMapping
    public Result insertOrder(@RequestBody Order order) {
        return orderService.insertOrder(order);
    }

    /**
     * 完成订单
     * @param map 请求参数（包含订单ID、新的投放点ID、使用时长）
     * @return 操作结果
     */
    @PutMapping()
    public Result finishOrder(@RequestBody Map<String, String> map) {
        String orderId = map.get("orderId");
        String newDpId = map.get("newDpId");
        Double time = Double.parseDouble(map.get("time"));
        return orderService.finishOrder(orderId, newDpId, time);
    }

    /**
     * 获取订单价格
     * @param time 使用时长
     * @param orderId 订单ID
     * @return 订单价格结果
     */
    @GetMapping("/cost")
    public Result getPrice(@RequestParam Double time, @RequestParam String orderId) {
        return orderService.getPrice(time, orderId);
    }


}
