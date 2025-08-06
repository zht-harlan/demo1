package com.sky.task;

import com.sky.entity.Orders;
import com.sky.mapper.OrderMapper;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.weaver.ast.Or;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
@Slf4j
public class MyTask {
    @Autowired
    private OrderMapper orderMapper;
    @Scheduled(cron = "0 0/1 * * * ?") // 每分钟执行一次
    public void processOrder() {
        // 这里可以添加你需要执行的任务逻辑
        log.info("定时任务执行中...");

        LocalDateTime time=LocalDateTime.now().plusMinutes(-15);
        // 例如，调用某个服务的方法，或者执行一些数据处理等
        List<Orders> list=orderMapper.getstatusAndTime(Orders.PENDING_PAYMENT,time);
        if(list!=null && list.size()>0) {
            for (Orders orders : list) {
                // 处理订单逻辑
                log.info("处理订单: {}", orders.getNumber());
                // 这里可以添加更多的业务逻辑，比如更新订单状态等
                orders.setStatus(Orders.CANCELLED); // 假设将订单状态改为已取消
                orders.setCancelReason("订单超时未支付，已自动取消");
                orders.setCancelTime(LocalDateTime.now());
                orderMapper.update(orders);
            }
        }
    }

    @Scheduled(cron = "0 0 1 * * ?")
    public void processDelivery()
    {
        // 每天凌晨1点执行一次
        log.info("定时任务执行中...");

        // 这里可以添加你需要执行的任务逻辑
        // 例如，调用某个服务的方法，或者执行一些数据处理等
        List<Orders> list=orderMapper.getstatusAndTime(Orders.DELIVERY_IN_PROGRESS,LocalDateTime.now());
        if(list!=null && list.size()>0) {
            for (Orders orders : list) {
                // 处理订单逻辑
                log.info("处理订单: {}", orders.getNumber());
                // 这里可以添加更多的业务逻辑，比如更新订单状态等
                orders.setStatus(Orders.COMPLETED); // 假设将订单状态改为已完成
                orderMapper.update(orders);
            }
        }
    }
}
