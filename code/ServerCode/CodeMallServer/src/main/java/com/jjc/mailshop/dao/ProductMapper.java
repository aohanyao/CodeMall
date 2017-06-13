package com.jjc.mailshop.dao;

import com.jjc.mailshop.pojo.Product;
import org.apache.ibatis.annotations.Param;

import java.util.List;

import java.util.List;

public interface ProductMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Product record);

    int insertSelective(Product record);

    Product selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Product record);

    int updateByPrimaryKey(Product record);

    List<Product> selectProduct();
    List<Product> getAllProduct();

    List<Product> searchProductByNameOrId(@Param("productId") String productId,
                                          @Param("productName") String productName);

    List<Product> selectByCategoryId(String categoryId);

    List<Product> selectByLikeName(@Param("keword") String keword);
}