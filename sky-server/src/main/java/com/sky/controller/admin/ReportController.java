package com.sky.controller.admin;

import com.sky.result.Result;
import com.sky.service.ReportService;
import com.sky.vo.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Select;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@RestController
@RequestMapping("/admin/report")
@Api(tags = "统计相关接口")
@Slf4j
public class ReportController {
    @Autowired
    private ReportService reportService;


    @GetMapping("/turnoverStatistics")
    @ApiOperation(value = "营业额统计")
    public Result turnoverStatistics(@DateTimeFormat(pattern="yyyy-MM-dd")  LocalDate begin,@DateTimeFormat(pattern="yyyy-MM-dd")  LocalDate end) {
        TurnoverReportVO ret= reportService.turnoverStatistics(begin, end);
        return Result.success(ret);
    }

    @GetMapping("/userStatistics")
    @ApiOperation(value = "用户统计")
    public Result UserStatistics(@DateTimeFormat(pattern="yyyy-MM-dd")  LocalDate begin,@DateTimeFormat(pattern="yyyy-MM-dd")  LocalDate end) {
        UserReportVO ret = reportService.userStatistics(begin, end);
        return Result.success(ret);
    }

    @GetMapping("/ordersStatistics")
    @ApiOperation(value = "订单统计")
    public Result orderStatistics(@DateTimeFormat(pattern="yyyy-MM-dd")  LocalDate begin,@DateTimeFormat(pattern="yyyy-MM-dd")  LocalDate end) {
        // 订单统计功能尚未实现
        log.info("订单统计功能");
        OrderReportVO ret=reportService.orderStatistics(begin,end);
        return Result.success(ret);
    }

    @GetMapping("/top10")
    @ApiOperation(value = "订单统计Top10")
    public Result top10(@DateTimeFormat(pattern="yyyy-MM-dd")  LocalDate begin,@DateTimeFormat(pattern="yyyy-MM-dd")  LocalDate end) {

        // 订单统计Top10功能尚未实现
        log.info("订单统计Top10功能");
        SalesTop10ReportVO ret = reportService.top10(begin, end);
        return Result.success(ret);
    }
}
