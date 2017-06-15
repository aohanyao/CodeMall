package com.jjc.mailshop.service

import com.github.pagehelper.PageInfo
import com.jjc.mailshop.common.ServerResponse
import com.jjc.mailshop.pojo.Shipping

/**
 * Created by Administrator on 2017/6/15 0015.
 * <p>地址管理相关服务
 */
interface IAddressService {
    /**
     * 新增地址
     * @param shipping 地址对象
     */
    fun createAddress(shipping: Shipping): ServerResponse<Shipping>

    /**
     * 修改地址
     * @param shipping 地址对象
     */
    fun updateAddress(shipping: Shipping, userId: Int): ServerResponse<Shipping>

    /**
     * 删除地址
     * @param shippingId 地址id
     */
    fun deleteAddress(shippingId: Int, userId: Int): ServerResponse<String>

    /**
     * 获取单个地址详情
     * @param shippingId 地址id
     */
    fun getAddressDetails(shippingId: Int, userId: Int): ServerResponse<Shipping>

    /**
     * 地址列表
     * @param userId 用户id
     */
    fun getAddressList(userId: String, pageIndex: Int, pageSize: Int): ServerResponse<PageInfo<Shipping>>
}