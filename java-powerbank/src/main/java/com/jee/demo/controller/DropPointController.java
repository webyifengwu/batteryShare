package com.jee.demo.controller;

import com.github.pagehelper.PageInfo;
import com.jee.demo.domain.DropPoint;
import com.jee.demo.domain.Result;
import com.jee.demo.service.DropPointService;
import com.jee.demo.utils.ResultUtil;
import net.sf.jsqlparser.statement.drop.Drop;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/point")
@CrossOrigin(origins = {"http://localhost:8080","http://localhost:8081"})
public class DropPointController {

    @Autowired
    private DropPointService dropPointService;
    /**
     * 查询投放点列表
     * @param pageNum 当前页码
     * @param pageSize 每页大小
     * @param dpName 投放点名称（可选）
     * @param dpZone 投放点区域（可选）
     * @return 投放点列表结果
     */
    @GetMapping
    public Result queryDropPointList(@RequestParam(defaultValue = "1") Integer pageNum,
                                     @RequestParam(defaultValue = "10") Integer pageSize,
                                     @RequestParam(required = false) String dpName,
                                     @RequestParam(required = false) String dpZone) {
        return dropPointService.queryDropPointList(dpName, dpZone, pageNum, pageSize);
    }

    /**
     * 根据模式和区域获取所有投放点列表
     * @param status 模式（状态）
     * @param dpZone 投放点区域
     * @return 投放点列表结果
     */
    @GetMapping("/forward")
    public Result getAllDropPointList(@RequestParam Integer status, @RequestParam String dpZone) {
        return dropPointService.getAllDropPointListByMode(status, dpZone);
    }

    /**
     * 新增投放点
     * @param point 投放点对象
     * @return 操作结果
     */
    @PostMapping()
    public Result insertDropPoint(@RequestBody DropPoint point) {
        return dropPointService.insertDropPoint(point);
    }

    /**
     * 删除投放点
     * @param id 投放点ID
     * @return 操作结果
     */
    @DeleteMapping("/{id}")
    public Result deleteDropPoint(@PathVariable String id) {
        return dropPointService.deleteDropPoint(id);
    }

    /**
     * 更新投放点
     * @param point 投放点对象
     * @return 操作结果
     */
    @PutMapping
    public Result updateDropPoint(@RequestBody DropPoint point) {
        return dropPointService.updateDropPoint(point);
    }

}
