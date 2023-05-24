package com.jee.demo.service;


import com.jee.demo.domain.DropPoint;
import com.jee.demo.domain.Result;

public interface DropPointService {

    /**
     * 查询充电宝投放点列表
     * @param dpName 投放点名称（可选）
     * @param dpZone 投放点区域（可选）
     * @param pageNum 当前页码
     * @param pageSize 每页大小
     * @return 投放点列表结果
     */
    Result queryDropPointList(String dpName, String dpZone, Integer pageNum, Integer pageSize);

    /**
     * 新增充电宝投放点
     * @param point 充电宝投放点对象
     * @return 操作结果
     */
    Result insertDropPoint(DropPoint point);

    /**
     * 删除充电宝投放点
     * @param id 投放点ID
     * @return 操作结果
     */
    Result deleteDropPoint(String id);

    /**
     * 更新充电宝投放点
     * @param point 充电宝投放点对象
     * @return 操作结果
     */
    Result updateDropPoint(DropPoint point);

    /**
     * 前台根据不同使用模式获得所有充电宝投放点列表，并计算距离，返回排好序的数据
     * @param status 投放点状态
     * @param dpZone 用户所处区域
     * @return 充电宝投放点列表结果
     */
    Result getAllDropPointListByMode(Integer status, String dpZone);
}
