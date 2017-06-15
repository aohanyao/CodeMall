package com.jjc.mailshop.controller.portal

import com.jjc.mailshop.common.CheckUserPermissionUtil
import com.jjc.mailshop.common.ServerResponse
import com.jjc.mailshop.service.IShoppingCartService
import com.jjc.mailshop.vo.CartProductVoList
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.ResponseBody
import javax.servlet.http.HttpSession

/**
 * Created by Administrator on 2017/6/14 0014.
 * <p>购物车相关的控制器
 */
@RequestMapping("/shopping/cart/")
@Controller
class ShoppingCartController {

    @Autowired
    var iShoppingCartService: IShoppingCartService? = null

    /**
     * 获取用户的购物车
     */
    @RequestMapping(value = "getShoppingCartList.json", method = arrayOf(RequestMethod.POST))
    @ResponseBody
    fun getShoppingCartList(session: HttpSession,
                            @RequestParam(value = "pageIndex", defaultValue = "1") pageIndex: Int,
                            @RequestParam(value = "pageSize", defaultValue = "10") pageSize: Int): ServerResponse<CartProductVoList>? {
        //判断用户是否已经登录了  得到用户
        val user = CheckUserPermissionUtil.checkUserLoginAndPermission(session).data
                ?: return ServerResponse.createByErrorMessage("用户未登录")

        //调用服务查询

        return iShoppingCartService!!.getShoppingCartList(user.id, pageIndex, pageSize)
    }

    /**
     * 增加购物车
     */
    @RequestMapping(value = "addShoppingCard.json", method = arrayOf(RequestMethod.POST))
    @ResponseBody
    fun addShoppingCard(session: HttpSession, productId: Int, count: Int): ServerResponse<String>? {
        //判断用户是否已经登录了  得到用户
        val user = CheckUserPermissionUtil.checkUserLoginAndPermission(session).data
                ?: return ServerResponse.createByErrorMessage("用户未登录")

        return iShoppingCartService!!.addShoppingCard(user.id, productId, count)
    }

    /**
     * 修改购物车
     */
    @RequestMapping(value = "updateShoppingCard.json", method = arrayOf(RequestMethod.POST))
    @ResponseBody
    fun updateShoppingCard(session: HttpSession, productId: Int, count: Int): ServerResponse<CartProductVoList>? {
        //判断用户是否已经登录了  得到用户
        val user = CheckUserPermissionUtil.checkUserLoginAndPermission(session).data
                ?: return ServerResponse.createByErrorMessage("用户未登录")

        return iShoppingCartService!!.updateShoppingCard(user.id, productId, count)
    }

    /**
     * 移除购物车的商品
     */
    @RequestMapping(value = "removeShoppingCard.json", method = arrayOf(RequestMethod.POST))
    @ResponseBody
    fun removeShoppingCard(session: HttpSession, cartIds: Array<Int>): ServerResponse<CartProductVoList>? {
        //判断用户是否已经登录了  得到用户
        val user = CheckUserPermissionUtil.checkUserLoginAndPermission(session).data
                ?: return ServerResponse.createByErrorMessage("用户未登录")

        //调用接口进行删除
        return iShoppingCartService!!.removeShoppingCard(user.id, cartIds)
    }
}