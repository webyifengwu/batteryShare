package com.jee.demo.utils;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;

import java.util.ArrayList;
import java.util.function.Function;

public class DtoVUtil {

    public static <VO, DTO> PageInfo<VO> dto2Vo (PageInfo<DTO> source,
                                                 Function<DTO,VO> convert){
        if (source != null) {
            Page<VO> dest = new Page<>(source.getPageNum(), source.getPageSize());
            dest.setTotal(source.getTotal());
            PageInfo<VO> result = new PageInfo<>(dest);
            if (source.getList()!=null && !source.getList().isEmpty()){
                ArrayList<VO> voList = new ArrayList<>();
                for (DTO dto : source.getList()) {
                    voList.add(convert.apply(dto));
                }
                result.setList(voList);
            }
            return result;
        }
        return null;
    }


}
