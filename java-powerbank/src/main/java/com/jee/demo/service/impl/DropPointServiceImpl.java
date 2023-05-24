package com.jee.demo.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jee.demo.domain.DropPoint;
import com.jee.demo.domain.Result;
import com.jee.demo.domain.dto.DropPointDto;
import com.jee.demo.mapper.DropPointMapper;
import com.jee.demo.service.DropPointService;
import com.jee.demo.utils.ResultUtil;
import com.jee.demo.utils.SimulationUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;
import java.util.UUID;

@Service
@Transactional
public class DropPointServiceImpl implements DropPointService {

    @Autowired
    private DropPointMapper dropPointMapper;

    @Override
    public Result queryDropPointList(String dpName, String dpZone, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum,pageSize);
        List<DropPoint> dropPointList= dropPointMapper.queryDropPointList(dpName,dpZone);
        PageInfo<DropPoint> pageInfo=new PageInfo<>(dropPointList,5);
        return ResultUtil.success("获取投放点数据成功",pageInfo);
    }

    @Override
    public Result insertDropPoint(DropPoint point) {
        //初始化时赋予id,和满库存（可用充电宝数等于所有充电宝）
        point.setDpId(UUID.randomUUID().toString());
        point.setDpStock(0);
        dropPointMapper.insertDropPoint(point);
        return ResultUtil.success("新增投放点成功");
    }

    @Override
    public Result deleteDropPoint(String id) {
        dropPointMapper.deleteDropPoint(id);
        return ResultUtil.success("删除投放点成功");
    }

    @Override
    public Result updateDropPoint(DropPoint point) {
        dropPointMapper.updateDropPoint(point);
        return ResultUtil.success("更新投放点成功");
    }

    @Override
    public Result getAllDropPointListByMode(Integer status, String dpZone) {
        String mode;
        if(status==0){
            mode="0";
        }else {
            mode="dp_all";
        }
        List<DropPoint> all = dropPointMapper.getAllDropPointListByMode(mode);
        //对距离进行处理
        List<DropPointDto> res = SimulationUtil.calDistance(all,dpZone);
        return ResultUtil.success("成功获取投放点数据",res);
    }
}
