package com.jee.demo.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jee.demo.domain.ChargePal;
import com.jee.demo.domain.DropPoint;
import com.jee.demo.domain.Result;
import com.jee.demo.domain.User;
import com.jee.demo.domain.dto.PalDto;
import com.jee.demo.mapper.ChargePalMapper;
import com.jee.demo.mapper.DropPointMapper;
import com.jee.demo.mapper.UserMapper;
import com.jee.demo.service.ChargePalService;
import com.jee.demo.service.UserService;
import com.jee.demo.utils.DtoVUtil;
import com.jee.demo.utils.ResultUtil;
import net.sf.jsqlparser.statement.drop.Drop;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@Transactional
public class ChargePalServiceImpl implements ChargePalService {
    @Autowired
    private ChargePalMapper chargePalMapper;
    @Autowired
    private DropPointMapper dropPointMapper;

    @Override
    public Result getAllChargePalListByChargePalByDpId(String dpId) {
        List<ChargePal> chargePalList=chargePalMapper.getAllChargePalListByChargePalByDpId(dpId);
        return ResultUtil.success(chargePalList);
    }

    @Override
    public Result queryChargePalList(String brand,String dpName, Integer status, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum,pageSize);
        List<ChargePal> chargePalList = chargePalMapper.queryChargePalList(dpName,status,brand);
        PageInfo<ChargePal> pageInfo=new PageInfo<>(chargePalList,5);
        PageInfo<PalDto> res = DtoVUtil.dto2Vo(pageInfo, pal -> {
            DropPoint point = dropPointMapper.getDropPointById(pal.getDpId());
            PalDto palDto = new PalDto();
            BeanUtils.copyProperties(pal,palDto);
            palDto.setDpId(point.getDpId());
            palDto.setDpName(point.getDpName());
            return palDto;
        });
        return ResultUtil.success("获取充电宝数据成功",res);
    }

    @Override
    public Result insertChargePal(ChargePal pal) {
        pal.setPId(UUID.randomUUID().toString());
        pal.setStatus(1);

        //修改投放点的Stock++
        //1.通过dp_id获取投放点
        DropPoint point = dropPointMapper.getDropPointById(pal.getDpId());
        //判断是否满仓
        if(point.getDpAll().compareTo(point.getDpStock())==0){
            return ResultUtil.error(501,"投放点已满，无法继续添加");
        }
        //2.修改获取的Point的库存值
        chargePalMapper.insertChargePal(pal);
        point.setDpStock(point.getDpStock()+1);
        //3.更新
        dropPointMapper.updateDropPoint(point);
        return ResultUtil.success("新增充电宝成功");
    }

    @Override
    public Result deleteChargePal(String pId) {
        //删除充电宝数据
        ChargePal pal = chargePalMapper.getChargePalById(pId);
        chargePalMapper.deleteChargePal(pId);
        //修改投放点的Stock++
        //1.通过dp_id获取投放点
        DropPoint point = dropPointMapper.getDropPointById(pal.getDpId());
        //2.修改获取的Point的库存值
        point.setDpStock(point.getDpStock()-1);
        //3.更新
        dropPointMapper.updateDropPoint(point);
        return ResultUtil.success("删除充电宝成功");
    }

    @Override
    public Result updateChargePal(ChargePal pal) {
        //新的point
        DropPoint point = dropPointMapper.getDropPointById(pal.getDpId());
        if(point.getDpStock().compareTo(point.getDpAll())==0){
            return ResultUtil.error(501,"投放点满仓");
        }
        ChargePal oldPal = chargePalMapper.getChargePalById(pal.getPId());
        String dpId = oldPal.getDpId();
        DropPoint oldPoint = dropPointMapper.getDropPointById(dpId);
        oldPoint.setDpStock(oldPoint.getDpStock()-1);
        point.setDpStock(point.getDpStock()+1);
        chargePalMapper.updateChargePal(pal);
        dropPointMapper.updateDropPoint(oldPoint);
        dropPointMapper.updateDropPoint(point);
        return ResultUtil.success("更新充电宝成功");
    }
}
