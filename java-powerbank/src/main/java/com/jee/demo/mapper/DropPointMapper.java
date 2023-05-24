package com.jee.demo.mapper;

import com.jee.demo.domain.DropPoint;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Repository
public interface DropPointMapper {
    /**
     * 根据条件查询投放点列表
     *
     * @param dpName 投放点名称，用于模糊查询投放点列表
     * @param dpZone 投放点区域，用于模糊查询投放点列表
     * @return 投放点列表
     */
    List<DropPoint> queryDropPointList(@Param("dpName") String dpName, @Param("dpZone") String dpZone);

    /**
     * 根据投放点ID查询投放点信息
     *
     * @param dpId 投放点ID
     * @return 投放点对象
     */
    DropPoint getDropPointById(String dpId);

    /**
     * 插入投放点信息
     *
     * @param dropPoint 投放点对象
     */
    void insertDropPoint(DropPoint dropPoint);

    /**
     * 删除投放点
     *
     * @param id 投放点ID
     */
    void deleteDropPoint(String id);

    /**
     * 更新投放点信息
     *
     * @param point 投放点对象
     */
    void updateDropPoint(DropPoint point);

    /**
     * 根据模式获取不同库存的所有投放点列表
     *
     * @param mode 指定的库存模式
     * @return 投放点列表
     */
    List<DropPoint> getAllDropPointListByMode(String mode);

}
