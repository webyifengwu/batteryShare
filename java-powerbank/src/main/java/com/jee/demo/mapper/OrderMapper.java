package com.jee.demo.mapper;

import com.jee.demo.domain.Order;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderMapper {
    /**
     * 根据订单ID获取订单信息
     *
     * @param orderId 订单ID
     * @return 订单对象
     */
    Order getOrderById(String orderId);

    /**
     * 插入订单信息
     *
     * @param order 订单对象
     */
    void insertOrder(Order order);

    /**
     * 删除订单
     *
     * @param orderId 订单ID
     */
    void deleteOrder(String orderId);

    /**
     * 更新订单信息
     *
     * @param order 订单对象
     */
    void updateOrder(Order order);

    /**
     * 根据条件查询订单列表
     *
     * @param username 用户名，用于模糊查询订单列表
     * @param status   订单状态
     * @return 订单列表
     */
    List<Order> queryOrderList(@Param("username") String username, @Param("status") Integer status);

    /**
     * 根据用户ID和订单状态查询订单列表
     *
     * @param status 订单状态
     * @param userId 用户ID
     * @return 订单列表
     */
    List<Order> getOrderByUserAndId(@Param("status") Integer status, @Param("userId") String userId);

}
