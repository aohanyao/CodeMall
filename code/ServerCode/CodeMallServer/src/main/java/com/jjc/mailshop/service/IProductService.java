package com.jjc.mailshop.service;

import com.github.pagehelper.PageInfo;
import com.jjc.mailshop.common.ServerResponse;
import com.jjc.mailshop.pojo.Product;
import com.sun.istack.internal.NotNull;

import javax.servlet.http.HttpSession;

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
     *
     * @param productId 产品的ID
     * @param status    产品的状态
     * @return
     */
    ServerResponse<String> setSaleStatus(Integer productId, Integer status);

    /**
     * 修改产品信息
     *
     * @param product 产品信息
     * @return
     */
    ServerResponse<String> updateProduct(Product product);

    /**
     * 增加产品
     *
     * @param product
     * @return
     */
    ServerResponse<String> addProduct(Product product);

    /**
     * 获取产品列表
     *
     * @param pageIndex 页码
     * @param pageSize  页大小
     * @return
     */
    ServerResponse<PageInfo<Product>> getProductList(int pageIndex, int pageSize);

    /**
     * 搜索
     *
     * @param productName 产品名称
     * @param productId   产品id
     * @param pageIndex   页码
     * @param pageSize    页大小
     * @return
     */
    ServerResponse<PageInfo<Product>> searchProductList(String productName,
                                                        String productId,
                                                        int pageIndex,
                                                        int pageSize);

    /**
     * <p>前台搜索产品</p>
     *
     * @param categoryId 品类ID
     * @param keyword    关键字
     * @param orderBy    排序方式
     * @param pageIndex  页码
     * @param pageSize   页大小
     * @return
     */
    ServerResponse<PageInfo<Product>> searchProductByCategoryIdOrLikeName(String categoryId,
                                                                          String keyword,
                                                                          String orderBy,
                                                                          int pageIndex,
                                                                          int pageSize);
}
