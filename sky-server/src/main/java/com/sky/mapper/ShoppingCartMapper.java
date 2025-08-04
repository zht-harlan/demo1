package com.sky.mapper;

import com.sky.entity.ShoppingCart;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface ShoppingCartMapper {

    List<ShoppingCart> list(ShoppingCart shoppingCart);

    @Update("UPDATE shopping_cart SET number = #{number} WHERE id = #{id}")
    void updateById(ShoppingCart existingCart);

    @Insert("INSERT INTO shopping_cart (user_id, dish_id, setmeal_id, name, image, amount, number, create_time) " +
            "VALUES (#{userId}, #{dishId}, #{setmealId}, #{name}, #{image}, #{amount}, #{number}, #{createTime})")
    void insert(ShoppingCart shoppingCart);

   @Delete("DELETE FROM shopping_cart WHERE user_id = #{userId}")
    void deletByUserId(Long userId);

    @Delete("DELETE FROM shopping_cart WHERE id = #{id}")
    void deletById(Long id);
}
