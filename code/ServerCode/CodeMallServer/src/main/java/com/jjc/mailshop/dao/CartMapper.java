package com.jjc.mailshop.dao;

import com.jjc.mailshop.pojo.Cart;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CartMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Cart record);

    int insertSelective(Cart record);

    Cart selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Cart record);

    int updateByPrimaryKey(Cart record);
    Cart selectByProductId(@Param("productId") Integer productId,
                           @Param("userId") Integer userId);

    List<Cart> selectByUserId(Integer userId);

    int updateAllChecked(@Param("check") Integer check, @Param("userId") Integer userId);
}