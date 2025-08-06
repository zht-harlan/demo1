package com.sky.mapper;

import com.github.pagehelper.Page;
import com.sky.dto.OrdersPageQueryDTO;
import com.sky.entity.Orders;
import com.sky.vo.OrderStatisticsVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface OrderMapper {

    void insert(Orders orders);

    Page<Orders> pagequery(OrdersPageQueryDTO ordersDTO);


    @Select("select * from orders where number = #{orderNumber}")
    Orders getByNumber(String orderNumber);

    /**
     * 修改订单信息
     * @param orders
     */
    void update(Orders orders);

    @Select("select * from orders where id = #{id}")
    Orders getById(Long id);

    /**
     * 统计各个订单的数量
     * @return
     */
    @Select("SELECT count(*) from orders where status =#{status}")
    Integer statisticsByStatus(Integer status);
}
