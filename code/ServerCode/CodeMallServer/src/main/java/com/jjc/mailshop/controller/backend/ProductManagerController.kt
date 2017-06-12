package com.jjc.mailshop.controller.backend

import com.jjc.mailshop.common.CheckUserPermissionUtil
import com.jjc.mailshop.common.ServerResponse
import com.jjc.mailshop.pojo.Product
import com.jjc.mailshop.service.IProductService
import com.sun.istack.internal.NotNull
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.ResponseBody

import javax.servlet.http.HttpSession

/**
 * Created by jjc on 2017/6/7.
 *
 * 产品管理控制器
 */
@RequestMapping(value = "/manager/product/")
@Controller
class ProductManagerController {
    @Autowired
    internal var iProductService: IProductService? = null

    /**
     * 获取产品详情

     * @param productId 产品ID
     * *
     * @return
     */
    @RequestMapping(value = "getProductDetail.json", method = arrayOf(RequestMethod.POST))
    @ResponseBody
    fun getProductDetail(@NotNull productId: Int?): ServerResponse<Product> {
        //获取详情
        return iProductService!!.getProductDetail(productId)
    }

    /**
     * 产品上下架

     * @param productId 产品ID
     * *
     * @param status    0 下架 ，1 上架
     * *
     * @return
     */
    @RequestMapping(value = "setProductSaleStatus.json", method = arrayOf(RequestMethod.POST))
    @ResponseBody
    fun setProductSaleStatus(@NotNull productId: Int?, @NotNull status: Int, session: HttpSession): ServerResponse<String> {
        //状态不正确
        if (status !== 0 && status !== 1) {
            return ServerResponse.createByErrorMessage<String>("参数status不正确")
        }
        //检查登录与权限
        val permission = CheckUserPermissionUtil.checkLoginAndPermission(session)
        if (permission != null) {
            return permission
        }
        //修改状态
        return iProductService!!.setSaleStatus(productId, status)
    }

    /**
     * 修改产品信息

     * @param product 产品信息
     * *
     * @param session
     * *
     * @return
     */
    @RequestMapping(value = "updateProduct.json", method = arrayOf(RequestMethod.POST))
    @ResponseBody
    fun updateProduct(product: Product, session: HttpSession): ServerResponse<String> {
        //检查登录与权限
        val permission = CheckUserPermissionUtil.checkLoginAndPermission(session)
        if (permission != null) {
            return permission
        }

        //检查一些参数
        if (product.id == null) {
            return ServerResponse.createByErrorMessage<String>("id不能为空")
        }
        //调用接口进行修改
        return iProductService!!.updateProduct(product)
    }

    @RequestMapping(value = "addProduct.json", method = arrayOf(RequestMethod.POST))
    @ResponseBody
    fun addProduct(product: Product, session: HttpSession): ServerResponse<String> {
        //检查登录与权限
        val permission = CheckUserPermissionUtil.checkLoginAndPermission(session)
        if (permission != null) {
            return permission
        }

        //检查一些参数
        if (product.id != null) {
            product.id = null
        }
        //调用接口进行添加
        return iProductService!!.addProduct(product)
    }
}
