package com.jee.demo.mapper;

import com.jee.demo.domain.ChargePal;
import com.jee.demo.domain.DropPoint;
import com.jee.demo.domain.User;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChargePalMapper {
    /**
     * 根据条件查询充电宝列表
     *
     * @param dpName  投放点名称，用于模糊查询充电宝列表
     * @param status  充电宝状态，用于查询指定状态的充电宝列表
     * @param brand   充电宝品牌，用于模糊查询充电宝列表
     * @return 充电宝列表
     */
    List<ChargePal> queryChargePalList(@Param("dpName") String dpName, @Param("status") Integer status, @Param("brand") String brand);

    /**
     * 根据充电宝ID查询充电宝信息
     *
     * @param pId 充电宝ID
     * @return 充电宝对象
     */
    ChargePal getChargePalById(String pId);

    /**
     * 插入充电宝信息
     *
     * @param pal 充电宝对象
     */
    void insertChargePal(ChargePal pal);

    /**
     * 删除充电宝
     *
     * @param pId 充电宝ID
     */
    void deleteChargePal(String pId);

    /**
     * 更新充电宝信息
     *
     * @param pal 充电宝对象
     */
    void updateChargePal(ChargePal pal);

    /**
     * 根据投放点ID查询对应状态为1的所有充电宝列表
     *
     * @param dpId 投放点ID
     * @return 充电宝列表
     */
    List<ChargePal> getAllChargePalListByChargePalByDpId(String dpId);

}
