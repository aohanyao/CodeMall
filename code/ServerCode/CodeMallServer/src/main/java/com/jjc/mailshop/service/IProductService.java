package com.jjc.mailshop.service;

import com.jjc.mailshop.common.ServerResponse;
import com.jjc.mailshop.pojo.Product;
import com.sun.istack.internal.NotNull;

/**
 * Created by jjc on 2017/6/7.
 * <p>产品相关的服务</p>
 */
public interface IProductService {
    /**
     * 获取产品详情
     *
     * @param productId
     * @return
     */
    ServerResponse<Product> getProductDetail(Integer productId);

    /**
     * 产品上下架
     * @param productId 产品的ID
     * @param status 产品的状态
     * @return
     */
    ServerResponse<String> setSaleStatus(Integer productId, Integer status);

    /**
     * 修改产品信息
     * @param product 产品信息
     * @return
     */
    ServerResponse<String> updateProduct(Product product);

    /**
     * 增加产品
     * @param product
     * @return
     */
    ServerResponse<String> addProduct(Product product);
}
