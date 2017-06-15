package com.jjc.mailshop.controller.backend;

import com.jjc.mailshop.common.CheckUserPermissionUtil;
import com.jjc.mailshop.common.ServerResponse;
import com.jjc.mailshop.pojo.Product;
import com.jjc.mailshop.service.IProductService;
import com.sun.istack.internal.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * Created by jjc on 2017/6/7.
 * <p>产品管理控制器</p>
 */
@RequestMapping(value = "/manager/product/")
@Controller
public class ProductManagerController {

    @Autowired
    IProductService iProductService;

    /**
     * 获取产品详情
     *
     * @param productId 产品ID
     * @return
     */
    @RequestMapping(value = "getProductDetail.json", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<Product> getProductDetail(@NotNull Integer productId) {
        //获取详情
        return iProductService.getProductDetail(productId);
    }

    /**
     * 产品上下架
     *
     * @param productId 产品ID
     * @param status    0 下架 ，1 上架
     * @return
     */
    @RequestMapping(value = "setProductSaleStatus.json", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<String> setProductSaleStatus(@NotNull Integer productId, @NotNull Integer status, HttpSession session) {
        //状态不正确
        if (status != 0 && status != 1) {
            return ServerResponse.createByErrorMessage("参数status不正确");
        }
        //检查登录与权限
        ServerResponse<String> permission = CheckUserPermissionUtil.checkAdminLoginAndPermission(session);
        if (permission != null) {
            return permission;
        }
        //修改状态
        return iProductService.setSaleStatus(productId, status);
    }

    /**
     * 修改产品信息
     *
     * @param product 产品信息
     * @param session
     * @return
     */
    @RequestMapping(value = "updateProduct.json", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<String> updateProduct(Product product, HttpSession session) {
        //检查登录与权限
        ServerResponse<String> permission = CheckUserPermissionUtil.checkAdminLoginAndPermission(session);
        if (permission != null) {
            return permission;
        }

        //检查一些参数
        if (product.getId() == null) {
            return ServerResponse.createByErrorMessage("id不能为空");
        }
        //调用接口进行修改
        return iProductService.updateProduct(product);
    }

    /**
     * 增加一个商品
     *
     * @param product
     * @param session
     * @return
     */
    @RequestMapping(value = "addProduct.json", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<String> addProduct(Product product, HttpSession session) {
        //检查登录与权限
        ServerResponse<String> permission = CheckUserPermissionUtil.checkAdminLoginAndPermission(session);
        if (permission != null) {
            return permission;
        }

        //检查一些参数
        if (product.getId() != null) {
            product.setId(null);
        }
        //调用接口进行添加
        return iProductService.addProduct(product);
    }

    /**
     * 获取产品列表
     * @param session
     * @param pageSize
     * @param pageIndex
     * @return
     */
    @RequestMapping(value = "getProductList.json", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<List<Product>> getProductList(HttpSession session,
                                                        @RequestParam(value = "pageSize", defaultValue = "1") Integer pageSize,
                                                        @RequestParam(value = "pageIndex", defaultValue = "10") Integer pageIndex) {
        //检查登录与权限
        ServerResponse permission = CheckUserPermissionUtil.checkAdminLoginAndPermission(session);
        if (permission != null) {
            return permission;
        }
        //调用接口查询

        return null;
    }
}
