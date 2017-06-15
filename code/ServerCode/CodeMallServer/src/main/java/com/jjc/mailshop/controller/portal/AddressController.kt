package com.jjc.mailshop.controller.portal

import com.github.pagehelper.PageInfo
import com.jjc.mailshop.common.CheckUserPermissionUtil
import com.jjc.mailshop.common.ServerResponse
import com.jjc.mailshop.pojo.Shipping
import com.jjc.mailshop.service.IAddressService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.ResponseBody
import javax.servlet.http.HttpSession

/**
 * Created by Administrator on 2017/6/15 0015.
 * <p>收货地址管理的控制器
 */
@Controller
@RequestMapping("/shopping/address/")
class AddressController {
    @Autowired
    var iAddressService: IAddressService? = null


    /**
     * 新增地址
     * @param shipping 地址对象
     */
    @ResponseBody
    @RequestMapping(value = "createAddress.json", method = arrayOf(RequestMethod.POST))
    fun createAddress(session: HttpSession, shipping: Shipping): ServerResponse<Shipping>? {
        //判断用户是否已经登录了  得到用户
        val user = CheckUserPermissionUtil.checkUserLoginAndPermission(session).data
                ?: return ServerResponse.createByErrorMessage("用户未登录")

        //设置值
        shipping.userId = user.id
        //调用服务
        return iAddressService!!.createAddress(shipping)
    }

    /**
     * 修改地址
     * @param shipping 地址对象
     */
    @ResponseBody
    @RequestMapping(value = "updateAddress.json", method = arrayOf(RequestMethod.POST))
    fun updateAddress(session: HttpSession, shipping: Shipping): ServerResponse<Shipping>? {
        //判断用户是否已经登录了  得到用户
        val user = CheckUserPermissionUtil.checkUserLoginAndPermission(session).data
                ?: return ServerResponse.createByErrorMessage("用户未登录")

        shipping.id ?: return ServerResponse.createByErrorMessage("id必传")
        //调用服务
        return iAddressService!!.updateAddress(shipping, user.id)
    }

    /**
     * 删除地址
     * @param shippingId 地址id
     */
    @ResponseBody
    @RequestMapping(value = "deleteAddress.json", method = arrayOf(RequestMethod.POST))
    fun deleteAddress(session: HttpSession, shippingId: Int): ServerResponse<String>? {
        //判断用户是否已经登录了  得到用户
        val user = CheckUserPermissionUtil.checkUserLoginAndPermission(session).data
                ?: return ServerResponse.createByErrorMessage("用户未登录")

        return iAddressService!!.deleteAddress(shippingId, user.id)
    }

    /**
     * 获取单个地址详情
     * @param shippingId 地址id
     */
    @ResponseBody
    @RequestMapping(value = "getAddressDetails.json", method = arrayOf(RequestMethod.POST))
    fun getAddressDetails(session: HttpSession, shippingId: Int): ServerResponse<Shipping>? {
        //判断用户是否已经登录了  得到用户
        val user = CheckUserPermissionUtil.checkUserLoginAndPermission(session).data
                ?: return ServerResponse.createByErrorMessage("用户未登录")

        return iAddressService!!.getAddressDetails(shippingId, user.id)
    }

    /**
     * 地址列表
     * @param userId 用户id
     */
    @ResponseBody
    @RequestMapping(value = "getAddressList.json", method = arrayOf(RequestMethod.POST))
    fun getAddressList(session: HttpSession, userId: String, pageIndex: Int, pageSize: Int): ServerResponse<PageInfo<Shipping>>? {
        //判断用户是否已经登录了  得到用户
        val user = CheckUserPermissionUtil.checkUserLoginAndPermission(session).data
                ?: return ServerResponse.createByErrorMessage("用户未登录")

        //调用服务查询
        return iAddressService!!.getAddressList(user.id.toString(), pageIndex, pageSize)
    }


}