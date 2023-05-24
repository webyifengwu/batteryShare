package com.jee.demo.service;

import com.github.pagehelper.PageInfo;
import com.jee.demo.domain.ChargePal;
import com.jee.demo.domain.Result;
import com.jee.demo.domain.User;
import com.jee.demo.domain.dto.PalDto;


public interface ChargePalService {
    /**
     * 查询充电宝列表
     * @param brand 充电宝品牌（可选）
     * @param dpName 投放点名称（可选）
     * @param status 充电宝状态（可选）
     * @param pageNum 当前页码
     * @param pageSize 每页大小
     * @return 充电宝列表结果
     */
    Result queryChargePalList(String brand, String dpName, Integer status, Integer pageNum, Integer pageSize);

    /**
     * 新增充电宝
     * @param pal 充电宝对象
     * @return 操作结果
     */
    Result insertChargePal(ChargePal pal);

    /**
     * 删除充电宝
     * @param pId 充电宝ID
     * @return 操作结果
     */
    Result deleteChargePal(String pId);

    /**
     * 更新充电宝
     * @param pal 充电宝对象
     * @return 操作结果
     */
    Result updateChargePal(ChargePal pal);

    /**
     * 根据投放点ID获取该投放点下的所有充电宝列表
     * @param dpId 投放点ID
     * @return 充电宝列表结果
     */
    Result getAllChargePalListByChargePalByDpId(String dpId);
}
