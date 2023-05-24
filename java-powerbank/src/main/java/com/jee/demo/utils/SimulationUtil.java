package com.jee.demo.utils;

import com.jee.demo.domain.DropPoint;
import com.jee.demo.domain.dto.DropPointDto;
import net.sf.jsqlparser.statement.drop.Drop;
import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class SimulationUtil {
    private static Random r = new Random();

    public static String randomZone() {
        int index = r.nextInt(10);
        int suffix = r.nextInt(50) + 1;
        return (char) ('a' + index) + "" + suffix + "";
    }

    /**
     * 1.
     * @param points
     * @param dpZone
     * @return
     */
    public static List<DropPointDto> calDistance(List<DropPoint> points,String dpZone){
        List<DropPointDto> pointDtoList = new ArrayList<>();
        for (DropPoint point : points) {
            DropPointDto pointDto=new DropPointDto();
            BeanUtils.copyProperties(point,pointDto);
            String pZone = point.getDpZone();
            int dis1 = Math.abs(Integer.parseInt(pZone.substring(1)) - Integer.parseInt(dpZone.substring(1))) * 5;
            int dis2= Math.abs((pZone.charAt(0) - dpZone.charAt(0)))*1000;
            int dis=dis1+dis2;
            pointDto.setDistance(dis);
            pointDtoList.add(pointDto);
        }
        return pointDtoList.stream().sorted((Comparator.comparingInt(DropPointDto::getDistance))).collect(Collectors.toList());

    }
}
