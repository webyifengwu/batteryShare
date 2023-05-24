package com.jee.demo.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jee.demo.domain.*;
import com.jee.demo.domain.dto.OrderDto;
import com.jee.demo.mapper.ChargePalMapper;
import com.jee.demo.mapper.DropPointMapper;
import com.jee.demo.mapper.OrderMapper;
import com.jee.demo.mapper.UserMapper;
import com.jee.demo.service.ChargePalService;
import com.jee.demo.service.OrderService;
import com.jee.demo.utils.DtoVUtil;
import com.jee.demo.utils.ResultUtil;
import com.jee.demo.utils.SimulationUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import java.util.function.Function;

@Service
@Transactional
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderMapper orderMapper;
    @Autowired
    private ChargePalMapper palMapper;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private DropPointMapper pointMapper;
    private Double time;

    private Random r = new Random();

//    @Override
//    public PageInfo<Order> getOrderList(Integer pageNum, Integer pageSize) {
//        PageHelper.startPage(pageNum,pageSize);
//        List<Order> orderList=orderMapper.getOrderList();
//        PageInfo<Order> pageInfo=new PageInfo<>(orderList,5);
//        return pageInfo;
//    }
//
//    @Override
//    public PageInfo<Order> getOrderListByStatus(Integer status, Integer pageNum, Integer pageSize) {
//        PageHelper.startPage(pageNum,pageSize);
//        List<Order> orderList=orderMapper.getOrderListByStatus(status);
//        PageInfo<Order> pageInfo=new PageInfo<>(orderList,5);
//        return pageInfo;
//    }

    @Override
    public Result insertOrder(Order order) {
        //修改充电宝状态
        ChargePal pal = palMapper.getChargePalById(order.getPId());
        //
        if (pal.getStatus().compareTo(0) == 0) {
            return ResultUtil.error(503, "错误的将租借出去的充电宝再次租借");
        }
        pal.setStatus(0);
        palMapper.updateChargePal(pal);
        //修改投放点库存
        DropPoint point = pointMapper.getDropPointById(order.getDpId());
        point.setDpStock(point.getDpStock() - 1);
        pointMapper.updateDropPoint(point);
        //新增订单
        order.setOrderId(UUID.randomUUID().toString());
        order.setStatus(1);
        order.setTime(0.0);
        order.setTotal(0.0);
        orderMapper.insertOrder(order);
        return ResultUtil.success("新增订单成功");
    }

    @Override
    public Result finishOrder(String orderId, String newDpId,Double time) {
        Order order = orderMapper.getOrderById(orderId);
        ChargePal pal = palMapper.getChargePalById(order.getPId());
        User user = userMapper.getUserById(order.getUserId());
        DropPoint newPoint = pointMapper.getDropPointById(newDpId);
        //充电宝
        //判断新的投放点有无位置
        if (newPoint.getDpStock().compareTo(newPoint.getDpAll()) == 0) {
            return ResultUtil.error(501, "新的投放点已满仓");
        }
        //获取充电宝的价格
        double totalPrice = 0;
        if (time.compareTo(15.0) >= 0) {
            totalPrice=pal.getPrice() * 15;
        } else {
            totalPrice=pal.getPrice()*time;
        }
        if(user.getBalance()<totalPrice){
            return ResultUtil.error(502,"您的余额不足，请充值后再来");
        }
        pal.setStatus(1);
        pal.setDpId(newDpId);
        palMapper.updateChargePal(pal);
        //投放点行为
        newPoint.setDpStock(newPoint.getDpStock() + 1);
        pointMapper.updateDropPoint(newPoint);
        //订单行为
        order.setStatus(0);
        //模拟租借时间
        order.setTime(time);
        order.setTotal(totalPrice);
        //用户行为

        user.setBalance(user.getBalance()-totalPrice);
        userMapper.updateUser(user);
        orderMapper.updateOrder(order);
        return ResultUtil.success("您已成功返还充电宝");
    }

    @Override
    public Result queryOrderList(String username, Integer status, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<Order> orderList = orderMapper.queryOrderList(username, status);
        PageInfo<Order> pageInfo = new PageInfo<>(orderList, 5);
        PageInfo<OrderDto> res = DtoVUtil.dto2Vo(pageInfo, order -> {
            User user = userMapper.getUserById(order.getUserId());
            DropPoint point = pointMapper.getDropPointById(order.getDpId());
            OrderDto orderDto = new OrderDto();
            BeanUtils.copyProperties(order,orderDto);
            orderDto.setUsername(user.getUsername());
            orderDto.setDpName(point.getDpName());
            return orderDto;
        });

        return ResultUtil.success("获取订单列表成功",res);
    }


    @Override
    public Result getPrice(Double time, String orderId) {
        Order order = orderMapper.getOrderById(orderId);
        ChargePal pal = palMapper.getChargePalById(order.getPId());
        double price = pal.getPrice();
        Double cost=price*time;
        return ResultUtil.success(cost);
    }

    @Override
    public Result getOrderByUserAndId(Integer status, String userId) {
        List<Order> orderList=  orderMapper.getOrderByUserAndId(status,userId);
        return ResultUtil.success(orderList);
    }
}
