package com.jee.demo.controller;

import com.github.pagehelper.PageInfo;
import com.jee.demo.domain.ChargePal;
import com.jee.demo.domain.Result;
import com.jee.demo.domain.dto.PalDto;
import com.jee.demo.service.ChargePalService;
import com.jee.demo.utils.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/pal")
@CrossOrigin(origins = {"http://localhost:8080", "http://localhost:8081"})
public class ChargePalController {

    @Autowired
    private ChargePalService chargePalService;
    /**
     * 查询充电宝列表
     * @param pageNum 当前页码
     * @param pageSize 每页显示的数量
     * @param dpName 投放点名称（可选）
     * @param status 充电宝状态（可选）
     * @param brand 充电宝品牌（可选）
     * @return 查询结果
     */
    @GetMapping
    public Result queryChargePalList(@RequestParam(defaultValue = "1") Integer pageNum,
                                     @RequestParam(defaultValue = "10") Integer pageSize,
                                     @RequestParam(required = false) String dpName,
                                     @RequestParam(required = false) Integer status,
                                     @RequestParam(required = false) String brand) {
        return chargePalService.queryChargePalList(brand, dpName, status, pageNum, pageSize);
    }

    /**
     * 根据投放点ID查询充电宝列表
     * @param dpId 投放点ID
     * @return 查询结果
     */
    @GetMapping("/forward")
    public Result getAllChargePalListByChargePalByDpId(@RequestParam String dpId) {
        return chargePalService.getAllChargePalListByChargePalByDpId(dpId);
    }

    /**
     * 新增充电宝
     * @param pal 充电宝对象
     * @return 操作结果
     */
    @PostMapping
    public Result insertChargePal(@RequestBody ChargePal pal) {
        return chargePalService.insertChargePal(pal);
    }

    /**
     * 删除充电宝
     * @param pId 充电宝ID
     * @return 操作结果
     */
    @DeleteMapping("/{pId}")
    public Result deleteChargePal(@PathVariable String pId) {
        return chargePalService.deleteChargePal(pId);
    }

    /**
     * 更新充电宝信息
     * @param pal 充电宝对象
     * @return 操作结果
     */
    @PutMapping
    public Result updateChargePal(@RequestBody ChargePal pal) {
        return chargePalService.updateChargePal(pal);
    }

}
