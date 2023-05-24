package com.jee.demo.service;


import com.github.pagehelper.PageInfo;
import com.jee.demo.domain.Order;
import com.jee.demo.domain.Result;
import com.jee.demo.domain.dto.OrderDto;

public interface OrderService {

    /**
     * 1. 更新充电宝pid状态1改为0
     * 2. 更新投放点的stock-1
     * 3. 新增订单
     * 	初始化订单(id=uuid,status=1,createTime=currentTime,time=0,total=0)
     * @param order 订单
     */
    Result insertOrder(Order order);

    /**
     * 先判断新的投放点是否满仓
     * 	true:返回501
     * 	false:
     *         1. 更新充电宝(状态0改为1,dpId修改为新投放点id)
     *         2. 更新新投放点的stock+1
     *         3. 修改订单
     *         	   1.修改订单状态为0历史状态
     *             2.模拟使用时长,随机1-24
     *             3.判断时长是否大于等于15
     * 				   true:算作15
     *                 false:按实际
     * @param orderId 订单id
     * @param newDpId 新投放点id
     * @param time    使用时长
     * @return
     */
    Result finishOrder(String orderId, String newDpId,Double time) ;

    /**
     *
     * 查询订单列表
     * @param username 用户名
     * @param status 订单状态（可选）
     * @param pageNum 当前页码
     * @param pageSize 每页大小
     * @return 订单列表结果
     */
    Result queryOrderList(String username, Integer status, Integer pageNum, Integer pageSize);
    /**
     *
     * 获取订单价格
     * @param time 使用时长
     * @param orderId 订单ID
     * @return 价格结果
     */
    Result getPrice(Double time, String orderId);
    /**
     *
     * 根据用户ID和订单状态查询订单
     * @param status 订单状态
     * @param userId 用户ID
     * @return 订单列表结果
     */
    Result getOrderByUserAndId(Integer status, String userId);
}
