package com.sky.service.impl;

import com.sky.dto.GoodsSalesDTO;
import com.sky.entity.Orders;
import com.sky.mapper.OrderMapper;
import com.sky.service.ReportService;
import com.sky.vo.*;
import io.swagger.models.auth.In;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.alibaba.fastjson.util.JavaBeanInfo.build;

@Service
@Slf4j
public class ReportServiceImpl implements ReportService {
    @Autowired
    private OrderMapper orderMapper;

    @Override
    public TurnoverReportVO turnoverStatistics(LocalDate begin, LocalDate end) {
        List<LocalDate> dates=new ArrayList<>();
        List<Double> turnoverList=new ArrayList<>();
        dates.add(begin);
        while(!begin.equals(end)) {
            begin=begin.plusDays(1);
            dates.add(begin);
        }
        for(LocalDate date:dates) {
            LocalDateTime startTime =LocalDateTime.of(date, LocalTime.MIN);
            LocalDateTime endTime =LocalDateTime.of(date, LocalTime.MAX);
            Double turnover=orderMapper.turnoverStatistics(startTime, endTime);
            turnoverList.add(turnover);
        }

        return TurnoverReportVO.builder()
                .dateList(StringUtils.join(dates, ","))
                .turnoverList(StringUtils.join(turnoverList, ","))
                .build();
    }

    @Override
    public UserReportVO userStatistics(LocalDate begin, LocalDate end) {
        List<LocalDate> dates=new ArrayList<>();
        List<Integer> userList=new ArrayList<>();
        List<Integer> newUserList=new ArrayList<>();
        dates.add(begin);
        while(!begin.equals(end)) {
            begin=begin.plusDays(1);
            dates.add(begin);
        }

        for(LocalDate date:dates) {
            LocalDateTime startTime =LocalDateTime.of(date, LocalTime.MIN);
            LocalDateTime endTime =LocalDateTime.of(date, LocalTime.MAX);
            Map map = new HashMap<>();
            map.put("endTime", endTime);
            Integer usernum=orderMapper.userStatistics(map);
            map.put("startTime", startTime);
            Integer newUser=orderMapper.userStatistics(map);
            userList.add(usernum);
            newUserList.add(newUser);
        }
        return UserReportVO.builder()
                .dateList(StringUtils.join(dates, ","))
                .totalUserList(StringUtils.join(userList, ","))
                .newUserList(StringUtils.join(newUserList, ","))
                .build();
    }

    @Override
    public OrderReportVO orderStatistics(LocalDate begin, LocalDate end) {
        List<LocalDate> dates=new ArrayList<>();
        List<Double> userList=new ArrayList<>();
        List<Double> newUserList=new ArrayList<>();
        Integer totalOrderCount=0;

        //有效订单数
        Integer validOrderCount=0;

        //订单完成率
        Double orderCompletionRate;
        dates.add(begin);
        while(!begin.equals(end)) {
            begin=begin.plusDays(1);
            dates.add(begin);
        }
        for(LocalDate date:dates) {
            LocalDateTime startTime =LocalDateTime.of(date, LocalTime.MIN);
            LocalDateTime endTime =LocalDateTime.of(date, LocalTime.MAX);
            Map map = new HashMap<>();
            map.put("startTime", startTime);
            map.put("endTime", endTime);
            Double ordernum=orderMapper.orderStatistics(map);
            map.put("status", Orders.COMPLETED);
            Double newOrder=orderMapper.orderStatistics(map);
            userList.add(ordernum);
            newUserList.add(newOrder);
            totalOrderCount +=ordernum.intValue();
            validOrderCount = validOrderCount+newOrder.intValue();
        }
        log.info("订单统计：日期列表：{}，订单数列表：{}，有效订单数列表：{}，总订单数：{}，有效订单数：{}",
                StringUtils.join(dates, ","),
                StringUtils.join(userList, ","),
                StringUtils.join(newUserList, ","),
                totalOrderCount,
                validOrderCount);
        return OrderReportVO.builder()
                .dateList(StringUtils.join(dates, ","))
                .orderCountList(StringUtils.join(userList, ","))
                .validOrderCountList(StringUtils.join(newUserList, ","))
                .totalOrderCount(totalOrderCount)
                .validOrderCount(validOrderCount)
                .orderCompletionRate(totalOrderCount == 0 ? 0.0 : (double) validOrderCount / totalOrderCount)
                .build();
    }

    @Override
    public SalesTop10ReportVO top10(LocalDate begin, LocalDate end) {
        List<LocalDate> dates=new ArrayList<>();
        List<GoodsSalesDTO> goodsSalesList = new ArrayList<>();
        Map map = new HashMap<>();
        map.put("startTime", LocalDateTime.of(begin, LocalTime.MIN));
        map.put("endTime", LocalDateTime.of(end, LocalTime.MAX));
        map.put("status", Orders.COMPLETED);
        goodsSalesList= orderMapper.topStatistics(map);
        List<String> nameList = goodsSalesList.stream().map(GoodsSalesDTO::getName).collect(Collectors.toList());
        List<Integer> numberList = goodsSalesList.stream().map(GoodsSalesDTO::getNumber).collect(Collectors.toList());
        log.info("Top10商品销售统计：商品名称列表：{}，销量列表：{}",
                StringUtils.join(nameList, ","),
                StringUtils.join(numberList, ","));
        return SalesTop10ReportVO.builder()
        .nameList(StringUtils.join(nameList, ","))
                .numberList(StringUtils.join(numberList, ","))
                .build();
    }
}
