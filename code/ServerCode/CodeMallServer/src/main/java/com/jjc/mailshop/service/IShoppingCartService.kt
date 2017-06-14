package com.jjc.mailshop.service

import com.jjc.mailshop.common.ServerResponse
import com.jjc.mailshop.pojo.User
import com.jjc.mailshop.vo.CartProductVo
import com.jjc.mailshop.vo.CartProductVoList
import org.springframework.web.bind.annotation.RequestParam
import javax.servlet.http.HttpSession

/**
 * Created by Administrator on 2017/6/14 0014.
 * 用户购物车服务
 */
interface IShoppingCartService {
    /**
     * 去获取用户的购物车
     * @param userId 用户信息
     * @param pageIndex 页码
     * @param pageSize 页大小
     */
    fun getShoppingCartList(userId: Int, pageIndex: Int, pageSize: Int): ServerResponse<CartProductVoList>

    /**
     * 添加购物车
     *  @param userId 用户信息
     *  @param productId 产品ID
     *  @param count 数量
     */
    fun addShoppingCard(userId: Int, productId: Int, count: Int): ServerResponse<String>

    /**
     * 修改购物车数量
     *  @param userId 用户信息
     *  @param productId 产品ID
     *  @param count 数量
     */
    fun updateShoppingCard(userId: Int, productId: Int, count: Int): ServerResponse<CartProductVoList>

    /**
     * 移除购物车内的某个商品
     *  @param userId 用户信息
     *  @param cartId 购物车ID  以#号进行分割 客串
     */
    fun removeShoppingCard(userId: Int, vararg cartId: Int): ServerResponse<CartProductVoList>

    /**
     * 选中购物车内的某个商品
     *  @param userId 用户信息
     *  @param cartId 购物车ID
     */
    fun checkShoppingCard(userId: Int, cartId: Int): ServerResponse<CartProductVoList>

    /**
     * 取消选中购物车内的某个商品
     *  @param userId 用户信息
     *  @param cartId 购物车ID
     */
    fun unCheckShoppingCard(userId: Int, cartId: Int): ServerResponse<CartProductVoList>

    /**
     * 获取购物车中的商品数量
     *  @param userId 用户信息
     */
    fun getShoppingCardCount(userId: Int): ServerResponse<CartProductVoList>

    /**
     * 取消选中所有
     * @param userId 用户信息
     */
    fun unCheckAllShoppingCard(userId: Int): ServerResponse<CartProductVoList>

    /**
     * 选中所有
     * @param userId 用户信息
     */
    fun checkAllShoppingCard(userId: Int): ServerResponse<CartProductVoList>
}